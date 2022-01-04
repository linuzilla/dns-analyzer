package ncu.cc.digger.services;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.DigCommands;
import ncu.cc.digger.constants.DigConstants;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.models.*;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DigWithCacheServiceImpl implements DigWithCacheService {
    private static final Logger logger = LoggerFactory.getLogger(DigWithCacheServiceImpl.class);

    private final DigService digService;
    private final RedisService redisService;
    private final int digResultCacheTimeToLive;

    public DigWithCacheServiceImpl(DigService digService, RedisService redisService,
                                   @Value(ValueConstants.DIG_CACHE_TIME_TO_LIVE) int digCacheTimeToLive) {
        this.digService = digService;
        this.redisService = redisService;
        this.digResultCacheTimeToLive = digCacheTimeToLive > 0 ? digCacheTimeToLive : Constants.DIG_CACHE_TTL;
    }

    private Mono<DigResult> digWithCacheTTL(String command, int ttl) {
        return redisService.asyncRetrieve(command)
                .flatMap(jsonOptional -> {
                    if (jsonOptional.isPresent()) {
                        DigResult digResult = DigResult.fromJSON(jsonOptional.get());
                        logger.trace("Retrieve from cache: [ {} ]", command);
                        return Mono.just(digResult);
                    } else {
                        logger.trace("Execute and cache: [ {} ]", command);

                        return digService.digWithShell(command)
                                .flatMap(digResult ->
                                        redisService
                                                .asyncStore(command, digResult.toJSON(), ttl)
                                                .map(aBoolean -> digResult));
                    }
                });
    }

    @Override
    public Mono<DigResult> digWithCache(String command) {
        return digWithCacheTTL(command, digResultCacheTimeToLive);
    }

    @Override
    public Mono<DigResult> digWithoutCache(String command) {
        return digWithCacheTTL(command, Constants.DIG_CACHE_MIN_TTL);
    }

    private Mono<List<ResourceRecord>> mapToResourceRecordList(Mono<DigResult> digResultMono) {
        return digResultMono.map(digResult -> digResult.getAuthority() > 0
                ? digResult.getAuthorityRecords()
                : digResult.getAnswerRecords())
                .map(answers -> answers.stream()
                        .filter(resourceRecord -> RecordTypes.NS.name().equals(resourceRecord.getRecordType()))
                        .collect(Collectors.toList()));

    }

    private Mono<List<ResourceRecord>> queryZoneWithCache(String command) {
        return mapToResourceRecordList(this.digWithCache(command));
    }

    private Mono<List<ResourceRecord>> queryZoneWithoutCache(String command) {
        return mapToResourceRecordList(this.digWithoutCache(command));
    }

//    private Mono<List<ResourceRecord>> queryZone(String command) {
//        return this.digWithoutCache(command)
//                .map(digResult -> digResult.getAuthority() > 0
//                        ? digResult.getAuthorityRecords()
//                        : digResult.getAnswerRecords())
//                .map(answers -> answers.stream()
//                        .filter(resourceRecord -> RecordTypes.NS.name().equals(resourceRecord.getRecordType()))
//                        .collect(Collectors.toList()));
//    }

    private Mono<List<ResourceRecord>> query(String command, RecordTypes recordType) {
        return this.digWithoutCache(command)
                .map(DigResult::getAnswerRecords)
                .map(answers -> answers.stream()
                        .filter(resourceRecord -> recordType.name().equals(resourceRecord.getRecordType()))
                        .collect(Collectors.toList()));
    }

    private class QueryNameServer {
        private final String zone;
        private final String nameServer;

        private QueryNameServer(String zone, String nameServer) {
            this.zone = zone;
            this.nameServer = nameServer;
        }

        private List<NameServerRecord> toNameServerRecord(DigResult digResult) {

            if (!DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                return Collections.emptyList();
            }

            boolean authority = digResult.getAuthority() > 0;
            List<ResourceRecord> rrset = authority ? digResult.getAuthorityRecords() : digResult.getAnswerRecords();

            var records = rrset.stream()
                    .filter(resourceRecord -> zone.equalsIgnoreCase(resourceRecord.getDomainName()))
                    .filter(resourceRecord -> RecordTypes.NS.name().equals(resourceRecord.getRecordType()))
                    .map(resourceRecord -> new NameServerRecord(
                            nameServer,
                            zone,
                            resourceRecord.getData().toLowerCase(),
                            authority))
                    .collect(Collectors.toList());


            digResult.getAdditionalRecords().forEach(resourceRecord -> records.stream()
                    .filter(nameServerRecord -> resourceRecord.getDomainName().equalsIgnoreCase(nameServerRecord.getDomainName()))
                    .forEach(nameServerRecord -> {
                        if (RecordTypes.A.name().equals(resourceRecord.getRecordType())) {
                            nameServerRecord.setIpv4Address(resourceRecord.getData());
                        } else if (RecordTypes.AAAA.name().equals(resourceRecord.getRecordType())) {
                            nameServerRecord.setIpv6Address(resourceRecord.getData());
                        }
                    })
            );

            return records;
        }

        private Mono<List<NameServerRecord>> queryAddress(List<NameServerRecord> nameServerRecords) {
            return Flux.fromIterable(nameServerRecords)
                    .flatMap(nameServerRecord -> {
                        if (!nameServerRecord.haveAddress()) {
                            return Mono.zip(
                                    findIPv4Address(nameServerRecord.getDomainName())
                                            .map(resourceRecords -> {
                                                resourceRecords
                                                        .stream()
                                                        .findFirst()
                                                        .map(resourceRecord -> {
                                                            nameServerRecord.setIpv4Address(resourceRecord.getData());
                                                            return resourceRecord;
                                                        });
                                                return nameServerRecord;
                                            }),
                                    findIPv6Address(nameServerRecord.getDomainName())
                                            .map(resourceRecords -> {
                                                resourceRecords
                                                        .stream()
                                                        .findFirst()
                                                        .map(resourceRecord -> {
                                                            nameServerRecord.setIpv6Address(resourceRecord.getData());
                                                            return resourceRecord;
                                                        });
                                                return nameServerRecord;
                                            })
                            ).map(Tuple2::getT1);
                        } else {
                            return Mono.just(nameServerRecord);
                        }
                    })
                    .collectList();
        }

        private Mono<List<NameServerRecord>> query() {
            return digWithoutCache(DigCommands.FIND_NS_FROM.toCommand(zone, nameServer))
                    .map(this::toNameServerRecord)
                    .flatMap(this::queryAddress);
        }
    }

    private Mono<List<ResourceRecord>> findIPv4Address(String domainName) {
        String domain = DNSUtil.zoneNameNormalize(domainName);

        logger.trace("find ipv4 address for {}", domain);

        return query(DigCommands.FIND_A.toCommand(domain), RecordTypes.A);
    }

    private Mono<List<ResourceRecord>> findIPv6Address(String domainName) {
        String domain = DNSUtil.zoneNameNormalize(domainName);

        logger.trace("find ipv6 address for {}", domain);

        return query(DigCommands.FIND_AAAA.toCommand(domain), RecordTypes.AAAA);
    }

    @Override
    public Mono<List<ResourceRecord>> findNameServers(String zone) {
        logger.info("find name servers for {}", zone);

        return queryZoneWithCache(DigCommands.FIND_NS.toCommand(zone))
                .doOnNext(resourceRecords -> logger.info("find {} record(s)", resourceRecords.size()));
    }

    @Override
    public Mono<List<ResourceRecord>> findNameServersFrom(String zone, String from) {
        logger.info("find name servers for {}", zone);

        return queryZoneWithoutCache(DigCommands.FIND_NS_FROM.toCommand(zone, from))
                .doOnNext(resourceRecords -> logger.info("find {} record(s)", resourceRecords.size()));
    }

    private String nameServerAndBelongsTo(ResourceRecord resourceRecord, String normalizedZone) {
        if (RecordTypes.NS.name().equalsIgnoreCase(resourceRecord.getRecordType())) {
            logger.info("found ns record {}", resourceRecord.getDomainName());
            return resourceRecord.getDomainName();
        } else if (RecordTypes.SOA.name().equalsIgnoreCase(resourceRecord.getRecordType())) {
            var soaDomain = resourceRecord.getDomainName().toLowerCase();

            if (normalizedZone.endsWith(soaDomain)) {
                return soaDomain;
            }
        }
        return null;
    }

    private Optional<String> findNSRecord(List<ResourceRecord> resourceRecordList, String normalizedZone) {
        return resourceRecordList
                .stream()
                .map(resourceRecord -> nameServerAndBelongsTo(resourceRecord, normalizedZone))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private String findDomain(DigResult digResult, String normalizedZone) {
        if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
            if (digResult.getAnswer() > 0) {
                Optional<String> nsRecord = findNSRecord(digResult.getAnswerRecords(), normalizedZone);

                if (nsRecord.isPresent()) {
                    logger.info("Find a domain (Answer) {}", nsRecord.get());
                    return nsRecord.get();
                }
            }

            if (digResult.getAuthority() > 0) {
                Optional<String> nsRecord = findNSRecord(digResult.getAuthorityRecords(), normalizedZone);

                if (nsRecord.isPresent()) {
                    logger.info("Find a domain (Authority) {}", nsRecord.get());
                    return nsRecord.get();
                }
            }
        }
        return "";
    }

    private Mono<String> tryToFindBelongingZoneFromParent(String zone, String parentZone) {
        return findNameServers(parentZone)
                .map(resourceRecordList -> resourceRecordList.stream().map(ResourceRecord::getData).collect(Collectors.toList()))
                .flatMap(parentNSs -> {
                    Optional<String> anyParent = parentNSs.stream().findAny();

                    if (anyParent.isPresent()) {
                        return findNameServersFrom(zone, anyParent.get())
                                .map(resourceRecordList -> resourceRecordList.size() == 0 ? "" : zone)
                                .doOnNext(s -> {
                                    if (StringUtil.isNotEmpty(s)) {
                                        logger.info("find zone {} from parent {}", zone, anyParent.get());
                                    } else {
                                        logger.info("zone {} not found from parent {}", zone, anyParent.get());
                                    }
                                });
                    } else {
                        logger.info("zone {} no parent", zone);
                        return Mono.just("");
                    }
                });
    }

    private Mono<String> tryToFindBelongingZone(String queryDomain, String zone, int level) {
        return digWithoutCache(DigCommands.FIND_NS.toCommand(zone))
                .flatMap(digResult -> {
                    logger.info("zone: {}, aueryZone: {}", zone, queryDomain);
                    if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                        var foundNS = findDomain(digResult, zone);

                        if (StringUtil.isNotEmpty(foundNS)) {
                            logger.info("found {} for {}/{}", foundNS, queryDomain, zone);
                            return Mono.just(foundNS.toLowerCase());
                        } else if (zone.chars().filter(ch -> ch == '.').count() > 1L) {
                            logger.info("not found, ... try parent zone: {}/{}", queryDomain, zone);

                            String parentZone = DNSUtil.parentZone(zone);

                            return tryToFindBelongingZoneFromParent(zone, parentZone)
                                    .flatMap(result -> {
                                        if (StringUtil.isNotEmpty(result)) {
                                            return Mono.just(result);
                                        } else {
                                            return tryToFindBelongingZone(queryDomain, parentZone, level + 1);
                                        }
                                    });
                        }
                    } else if (DigResponseStatus.SERVFAIL.name().equals(digResult.getStatus())) {
                        logger.info("findBelongingZone: {} {}", queryDomain, digResult.getTransferRecords());
                        return Mono.just(queryDomain);
                    }
                    return Mono.just("");
                });
    }

    @Override
    public Mono<String> findBelongingZone(String queryDomain) {
        var zone = DNSUtil.zoneNameNormalize(queryDomain);

        return tryToFindBelongingZone(zone, zone, 0);

//        return digWithoutCache(DigCommands.FIND_NS.toCommand(zone))
//                .flatMap(digResult -> {
//                    if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
//                        var foundNS = findDomain(digResult, zone);
//
//                        if (StringUtil.isNotEmpty(foundNS)) {
//                            return Mono.just(foundNS.toLowerCase());
//                        } else if (zone.chars().filter(ch -> ch == '.').count() > 1L) {
//                            return findBelongingZone(DNSUtil.parentZone(zone));
//                        }
//                    } else if (DigResponseStatus.SERVFAIL.name().equals(digResult.getStatus())) {
//                        logger.info("findBelongingZone: {} {}", queryDomain, digResult.getTransferRecords());
//                        return Mono.just(queryDomain);
//                    }
//                    return Mono.just("");
//                });
    }

//    @Override
//    public Mono<String> checkDomainExists(String zone) {
//        return digWithCache(DigCommands.FIND_NS.toCommand(zone))
//                .map(digResult -> {
//                    if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
//                        if (digResult.getAnswer() > 0) {
//                            logger.info("Find a domain: {}", zone);
//                            return digResult.getStatus();
//                        } else if (digResult.getAuthority() > 0 && digResult.getAuthorityRecords().stream()
//                                    .map(ResourceRecord::getRecordType)
//                                    .anyMatch(recordType -> RecordTypes.NS.name().equals(recordType))) {
//                            logger.info("Find a domain (Authority) {}", zone);
//                            return digResult.getStatus();
//                        }
//                        logger.info("Not a domain: {}", zone);
//                        return DigResponseStatus.NXDOMAIN.name();
//                    } else {
//                        return digResult.getStatus();
//                    }
//                });
//    }

    @Override
    public Mono<List<NameServerRecord>> findNameServersDetails(String zone, String nameServer) {
        return new QueryNameServer(DNSUtil.zoneNameNormalize(zone), nameServer).query();
    }

    @Override
    public Mono<DigResult> plainDNSCheck(String zoneName, String nameServer) {
        String zone = DNSUtil.zoneNameNormalize(zoneName);
        logger.trace("plain dns check on {} against {}", zone, nameServer);

        return digWithoutCache(DigCommands.QUERY_SOA.toCommand(zone, nameServer));
    }

    @Override
    public Mono<RecursiveCheckResultEnum> recursiveCheck(String nameServer) {
        return digWithoutCache(DigCommands.FIND_A_FROM.toCommand("non-exists.domain.please.", nameServer))
                .map(digResult -> {
                    if (DigResponseStatus.NXDOMAIN.name().equals(digResult.getStatus())) {
                        return RecursiveCheckResultEnum.RECURSIVE;
                    } else if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                        return RecursiveCheckResultEnum.RECURSIVE;
                    } else if (DigResponseStatus.REFUSED.name().equals(digResult.getStatus())) {
                        return RecursiveCheckResultEnum.NOT_ALLOW;
                    }
                    return RecursiveCheckResultEnum.HAVE_ERROR;
                });
    }

    @Override
    public Mono<Boolean> zoneTransferCheck(String zone, String nameServer) {
        return digWithoutCache(DigCommands.ZONE_TRANSFER.toCommand(zone, nameServer))
                .map(digResult -> {
//                    StackTraceUtil.print1(digResult);
                    return digResult.getAxfrRecords().size() > 0 && digResult.getTransferRecords() > 0;
                });
    }

    @Override
    public Mono<Boolean> dnssecCheck(String zone, String nameServer) {
        return digWithoutCache(DigCommands.DNSSEC_QUERY.toCommand(zone, nameServer))
                .map(digResult -> digResult.getFlags().contains(DigConstants.AUTHENTICATED_DATA));
    }
}
