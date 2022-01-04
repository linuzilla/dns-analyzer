package ncu.cc.digger.services;

import ncu.cc.digger.models.DNSHealthCheckResult;
import reactor.core.publisher.Mono;

public interface DNSQueryAndStoreService {
    Mono<DNSHealthCheckResult> digAndStore(String zone, String queryFrom);
    Mono<DNSHealthCheckResult> fetchOrDig(String zone, String queryFrom);
}
