package ncu.cc.digger.services;

import ncu.cc.digger.models.DigResult;
import ncu.cc.digger.models.NameServerRecord;
import ncu.cc.digger.models.ResourceRecord;
import ncu.cc.digger.models.dns.SOARecord;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface DigWithCacheService {

    enum RecursiveCheckResultEnum {
        RECURSIVE, NOT_ALLOW, HAVE_ERROR
    }

    Mono<DigResult> digWithCache(String command);

    Mono<DigResult> digWithoutCache(String command);

    Mono<List<ResourceRecord>> findNameServers(String zone);

    Mono<List<ResourceRecord>> findNameServersFrom(String zone, String from);

    Mono<String> findBelongingZone(String queryDomain);

//    Mono<String> checkDomainExists(String zone);

    Mono<List<NameServerRecord>> findNameServersDetails(String zone, String nameServer);

    Mono<DigResult> plainDNSCheck(String zone, String nameServer);

    Mono<RecursiveCheckResultEnum> recursiveCheck(String nameServer);

    Mono<Boolean> zoneTransferCheck(String zone, String nameServer);

    Mono<Boolean> dnssecCheck(String zone, String nameServer);
}
