package ncu.cc.digger.services;

import ncu.cc.commons.utils.Pair;
import ncu.cc.digger.models.DNSHealthCheckResult;
import ncu.cc.digger.models.DNSProblem;
import ncu.cc.digger.models.DNSProblemsEnum;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DNSHealthScoringImpl implements DNSHealthScoring {
    private static final int DNSSEC_SCORE = 1;
    private static final int OPEN_AXFR_SCORE = 10;
    private static final int OPEN_RECURSIVE_SCORE = 10;
    private static final int RRSET_INCONSISTENCY_SCORE = 10;
    private static final int SOA_INCONSISTENCY_SCORE = 10;

    // FIXME:
    private int calculate(DNSHealthCheckResult healthResult) {

        if (healthResult.getProblems().size() == 0) {
            return 0;
        } else {
            AtomicInteger score = new AtomicInteger(0);

            Map<DNSProblemsEnum, AtomicInteger> problemMap = Stream.of(DNSProblemsEnum.values())
                    .map(dnsProblemsEnum ->
                            new AbstractMap.SimpleEntry<>(dnsProblemsEnum, new AtomicInteger(0)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            healthResult.getProblems().stream()
                    .map(DNSProblem::getProblem)
                    .forEach(dnsProblemsEnum -> problemMap.get(dnsProblemsEnum).incrementAndGet());

            problemMap.forEach((dnsProblemsEnum, atomicInteger) -> {
                if (atomicInteger.get() > 0) {
                    score.addAndGet(atomicInteger.get());
                }
            });

            return score.get();
        }
    }
}
