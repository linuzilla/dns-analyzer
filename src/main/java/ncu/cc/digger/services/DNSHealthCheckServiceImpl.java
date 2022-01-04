package ncu.cc.digger.services;

import ncu.cc.commons.utils.RandomUtil;
import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.models.*;
import ncu.cc.digger.models.dns.FlatResourceRecord;
import ncu.cc.digger.models.dns.SOARecord;
import ncu.cc.digger.properties.DNSSECServerProperty;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class DNSHealthCheckServiceImpl implements DNSHealthCheckService {
    private static final Logger logger = LoggerFactory.getLogger(DNSHealthCheckServiceImpl.class);

    private final DigWithCacheService digWithCacheService;
    private final EDNSComplianceTestService ednsComplianceTestService;
    private final boolean enableIPv6support;
    private final List<String> dnssecVerficationServers;

    public DNSHealthCheckServiceImpl(
            DigWithCacheService digWithCacheService,
            EDNSComplianceTestService ednsComplianceTestService,
            DNSSECServerProperty dnssecServerProperty,
            @Value(ValueConstants.ENABLE_IPV6_NAME_SERVER) boolean enableIPv6support) {

        this.digWithCacheService = digWithCacheService;
        this.ednsComplianceTestService = ednsComplianceTestService;
        this.enableIPv6support = enableIPv6support;
        this.dnssecVerficationServers = dnssecServerProperty.getServers();
    }

    private class HealthChecker {
        private final String zone;
        private final DNSHealthCheckResult dnsHealthCheckResult;
        private String parentZone;

        private HealthChecker(String zone) {
            this.zone = DNSUtil.zoneNameNormalize(zone);
            this.parentZone = DNSUtil.parentZone(this.zone);

            dnsHealthCheckResult = new DNSHealthCheckResult();
            dnsHealthCheckResult.setZone(this.zone);
            dnsHealthCheckResult.setParentZone(this.parentZone);

//            digWithCacheService.findNameServers(zone)
//                    .map(resourceRecords ->
//                            resourceRecords.stream().map(FlatResourceRecord::getData).collect(Collectors.toList()));
        }

        private Mono<ParentNSRRSet> fromParentNS(String parentNS) {
            logger.trace("loading from parent NS: {}", parentNS);
            return digWithCacheService.findNameServersDetails(zone, parentNS)
                    .map(rrset -> new ParentNSRRSet(parentNS, rrset));
        }

        private Mono<ParentNSRRSet> preferAuthorityParent(List<String> nameServers) {
            if (nameServers == null || nameServers.size() == 0) {
                logger.trace("no name server found");
                return Mono.just(new ParentNSRRSet(null, Collections.emptyList()));
            }

            final AtomicBoolean found = new AtomicBoolean(false);
            final AtomicReference<ParentNSRRSet> bestAnswer = new AtomicReference<>();
            final AtomicReference<ParentNSRRSet> fallback = new AtomicReference<>();

            return Flux.fromIterable(nameServers)
                    .flatMap(nameServer -> fromParentNS(nameServer)
                            .doOnNext(parentNSRRSet -> {
                                synchronized (found) {
                                    if (!found.get()) {
                                        if (parentNSRRSet != null && parentNSRRSet.haveRecord()) {
                                            if (parentNSRRSet.isAuthority()) {
                                                logger.info("choose authority: {}", nameServer);
                                                found.set(true);
                                                bestAnswer.set(parentNSRRSet);
                                            } else {
                                                logger.info("get a non-authority: {}", nameServer);
                                                fallback.set(parentNSRRSet);
                                            }
                                        } else if (fallback.get() == null && parentNSRRSet != null) {
                                            fallback.set(parentNSRRSet);
                                        }
                                    }
                                }
                            })
                    )
                    .collectList()
                    .map(parentNSRRSets -> {
                        if (found.get()) {
                            return bestAnswer.get();
                        } else {
                            logger.info("choose fallback: {}", fallback.get().getNameServer());
                            return fallback.get();
                        }
                    });
        }

        private Mono<ParentNSRRSet> findAndPickOneParentNameServer() {
            return digWithCacheService.findNameServers(this.parentZone)
                    .flatMap(resourceRecords -> {
                        if (resourceRecords.size() == 0) {
                            var parent = DNSUtil.parentZone(this.parentZone);

                            if (StringUtil.isNotEmpty(parent)) {
                                return digWithCacheService.findNameServers(parent)
                                        .doOnNext(rrs -> {
                                            if (rrs.size() > 0) {
                                                this.parentZone = parent;
                                                this.dnsHealthCheckResult.setParentZone(parent);
                                            }
                                        });
                            }
                        }
                        return Mono.just(resourceRecords);
                    })
                    .map(resourceRecords -> resourceRecords.stream().map(ResourceRecord::getData).collect(Collectors.toList()))
                    .doOnNext(strings -> logger.trace("findAndPickOneParentNameServer: {} record(s)", strings.size()))
                    .doOnNext(this.dnsHealthCheckResult::setParentNameServers)
                    .flatMap(this::preferAuthorityParent)
                    .doOnNext(this.dnsHealthCheckResult::setParentRRSet);
        }

        private Mono<DaughterRRSet> findNameServersByRecord(NameServerRecord nameServerRecord) {
            if (nameServerRecord.haveAddress()) {
                logger.trace("address for {} is {}", nameServerRecord.getDomainName(), nameServerRecord.getAddress());
                return digWithCacheService.findNameServersDetails(zone, nameServerRecord.getAddress())
                        .map(nameServerRecords -> new DaughterRRSet(nameServerRecord, nameServerRecords));
            } else {
                logger.trace("no address info for {}", nameServerRecord.getDomainName());
                return Mono.empty();
            }
        }

        private Mono<Boolean> dnssecCheck(List<FlatResourceRecord> flatResourceRecords) {
            if (flatResourceRecords.size() == 0) {
                return Mono.just(false);
            } else {
                return digWithCacheService.dnssecCheck(zone, RandomUtil.randomFromList(dnssecVerficationServers))
                        .doOnNext(this.dnsHealthCheckResult::setDnssecEnabled);
            }
        }

        private Optional<SOARecord> digResultToSoa(DigResult digResult) {
            if (DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                return digResult.getAnswerRecords()
                        .stream()
                        .filter(resourceRecord -> RecordTypes.SOA.name().equals(resourceRecord.getRecordType()))
                        .filter(resourceRecord -> zone.equalsIgnoreCase(resourceRecord.getDomainName()))
                        .findFirst()
                        .map(ResourceRecord::getData)
                        .map(DNSUtil::toSoaRecord);
            } else {
                return Optional.empty();
            }
        }

        private Mono<FlatResourceRecord> plainDNSCheck(FlatResourceRecord flatResourceRecord) {
            AtomicBoolean haveSOA = new AtomicBoolean(false);

            if (flatResourceRecord.getAddress() == null) {
                this.dnsHealthCheckResult.setServerNotWorking(true);
                flatResourceRecord.setResponding(false);
                flatResourceRecord.setError("Unknown host");
                return Mono.just(flatResourceRecord);
            } else if (! flatResourceRecord.isIs_v6() || enableIPv6support) {
                return digWithCacheService.plainDNSCheck(zone, flatResourceRecord.getAddress())
                        .doOnNext(digResult -> {
                            flatResourceRecord.setResponding(! digResult.getFailedType().isFailed());

                            if (! DigResponseStatus.NOERROR.name().equals(digResult.getStatus())) {
                                if (StringUtil.isNotEmpty(digResult.getStatus())) {
                                    flatResourceRecord.setError(digResult.getStatus());
                                } else {
                                    flatResourceRecord.setError(digResult.getFailedMessage());
                                }
                            }
                        })
                        .map(this::digResultToSoa)
                        .map(optional -> {
                            flatResourceRecord.setSoa(optional.orElse(null));
                            if (! flatResourceRecord.isResponding()) {
                                this.dnsHealthCheckResult.setServerNotWorking(true);
                            }

                            if (flatResourceRecord.getSoa() == null && flatResourceRecord.getError() == null) {
//                                logger.warn("force setError to refused");
                                flatResourceRecord.setError("No SOA");
                            } else if (flatResourceRecord.isIs_v6()) {
                                this.dnsHealthCheckResult.setIpv6Available(true);
                            }

                            synchronized (haveSOA) {
                                if (! haveSOA.get() && flatResourceRecord.getSoa() != null) {
                                    this.dnsHealthCheckResult.setSoaEmail(flatResourceRecord.getSoa().getEmail());
                                    this.dnsHealthCheckResult.setSoaSerialNo(flatResourceRecord.getSoa().getSerial());
                                    haveSOA.set(true);
                                }
                            }
                            return flatResourceRecord;
                        });
            } else {
                return Mono.just(flatResourceRecord);
            }
        }

        private Mono<FlatResourceRecord> checkRecursive(FlatResourceRecord flatResourceRecord) {
            if (flatResourceRecord.getAddress() == null || flatResourceRecord.getSoa() == null) {
                return Mono.just(flatResourceRecord);
            } else {
                return digWithCacheService.recursiveCheck(flatResourceRecord.getAddress())
                        .map(recursiveCheckResultEnum -> {
                            switch (recursiveCheckResultEnum) {
                                case RECURSIVE:
                                    flatResourceRecord.setAllowRecursive(true);
                                    break;
                                case NOT_ALLOW:
                                case HAVE_ERROR:
                                    flatResourceRecord.setAllowRecursive(false);
                                    break;
                            }
                            return flatResourceRecord;
                        });
            }
        }

        private Mono<FlatResourceRecord> checkZoneTransferable(FlatResourceRecord flatResourceRecord) {
            if (flatResourceRecord.getAddress() == null || flatResourceRecord.getSoa() == null) {
                return Mono.just(flatResourceRecord);
            } else {
                return digWithCacheService.zoneTransferCheck(zone, flatResourceRecord.getAddress())
                        .map(transferable -> {
                            flatResourceRecord.setAllowTransfer(transferable);
                            return flatResourceRecord;
                        });
            }
        }

        private Mono<FlatResourceRecord> checkEDNSCompliance(FlatResourceRecord flatResourceRecord) {
            if (flatResourceRecord.getAddress() == null ||
                    flatResourceRecord.isIs_v6() && ! enableIPv6support) {
                return Mono.just(flatResourceRecord);
            } else if (! flatResourceRecord.isResponding()) {
                return Mono.just(flatResourceRecord);
            } else {
                return ednsComplianceTestService.performEDNSTests(zone, flatResourceRecord.getAddress())
                        .doOnNext(flatResourceRecord::setEdns)
                        .map(ednsComplianceTestResult -> flatResourceRecord);
            }
        }

        private Mono<DNSHealthCheckResult> check() {
            return findAndPickOneParentNameServer()
                    .map(ParentNSRRSet::getRrset)
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(this::findNameServersByRecord)
                    .collectList()
                    .doOnNext(daughterRRSets -> logger.trace("Daughter: {} records", daughterRRSets.size()))
                    .map(this.dnsHealthCheckResult::setDaughterRRSets)
                    .map(DNSHealthCheckResult::getParentRRSet)
                    .map(ParentNSRRSet::getRrset)
                    .map(DNSUtil::nameServerRecordsToFlatResourceRecords)
                    .map(this.dnsHealthCheckResult::setParents)
                    .doOnNext(DNSHealthCheckResult::nsDifferents)
                    .map(DNSHealthCheckResult::getAllNameServers)
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(this::plainDNSCheck)
                    .flatMap(this::checkRecursive)
                    .flatMap(this::checkZoneTransferable)
                    .flatMap(this::checkEDNSCompliance)
                    .collectList()
                    .doOnNext(flatResourceRecords -> logger.trace("flat RR: {}", flatResourceRecords.size()))
                    .flatMap(this::dnssecCheck)
                    .doOnNext(dnssecEnabled -> logger.info("DNSSEC Enabled: {}", dnssecEnabled))
                    .map(dnssecEnabled -> this.dnsHealthCheckResult);
        }
    }

    @Override
    public Mono<DNSHealthCheckResult> check(String zone) {
        return new HealthChecker(zone).check();
//
//        digWithCacheService.findAndPickOneParentNameServer(zone)
//                .map(resourceRecords -> resourceRecords.stream().map(FlatResourceRecord::getData).collect(Collectors.toList()))
//                .map(nameServers -> nameServers.stream()
//                    .map(ch))
//        return null;
    }
}
