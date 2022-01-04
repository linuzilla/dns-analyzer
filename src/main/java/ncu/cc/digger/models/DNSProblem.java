package ncu.cc.digger.models;

import ncu.cc.digger.models.dns.FlatResourceRecord;

public class DNSProblem {
    private final DNSProblemsEnum problem;
    private final String zone;
    private final FlatResourceRecord nameServer;
    private SeverityLevel severityLevel;
    private String additionalInfo;
    private String error;

    public DNSProblem(DNSProblemsEnum problem, String zone, FlatResourceRecord nameServer) {
        this.problem = problem;
        this.zone = zone;
        this.nameServer = nameServer;
    }

    public DNSProblem(DNSProblemsEnum problem, String zone, FlatResourceRecord nameServer, String additionalInfo, String error) {
        this.problem = problem;
        this.zone = zone;
        this.nameServer = nameServer;
        this.additionalInfo = additionalInfo;
        this.error = error;
    }

    public DNSProblem(DNSProblemsEnum problem, String zone) {
        this(problem, zone, null);
    }

    public DNSProblemsEnum getProblem() {
        return problem;
    }

    public String getZone() {
        return zone;
    }

    public FlatResourceRecord getNameServer() {
        return nameServer;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getError() {
        return error;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
