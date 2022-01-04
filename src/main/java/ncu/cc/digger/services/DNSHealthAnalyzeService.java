package ncu.cc.digger.services;

import ncu.cc.digger.models.DNSHealthCheckResult;
import ncu.cc.digger.models.DNSProblemReport;

import java.util.List;

public interface DNSHealthAnalyzeService {
    void analyze(DNSHealthCheckResult healthCheckResult);

    List<DNSProblemReport> problemsAndSuggestions(DNSHealthCheckResult healthCheckResult);
}
