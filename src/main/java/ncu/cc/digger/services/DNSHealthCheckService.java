package ncu.cc.digger.services;

import ncu.cc.digger.models.DNSHealthCheckResult;
import reactor.core.publisher.Mono;

public interface DNSHealthCheckService {
    Mono<DNSHealthCheckResult> check(String zone);
}
