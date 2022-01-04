package ncu.cc.digger.models;

public class DNSProblemReport {
    private final int severity;
    private final String description;
    private final String recommendation;
    private final String additional;

    public DNSProblemReport(int severity, String description, String recommendation, String additional) {
        this.severity = severity;
        this.description = description;
        this.recommendation = recommendation;
        this.additional = additional;
    }

    public int getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public String getAdditional() {
        return additional;
    }
}
