package ncu.cc.digger.models.statistics;

public class SeverityLevelStatistics {
    private final Byte    severityLevel;
    private final Long    cnt;

    public SeverityLevelStatistics(Byte severityLevel, Long cnt) {
        this.severityLevel = severityLevel;
        this.cnt = cnt;
    }

    public Byte getSeverityLevel() {
        return severityLevel;
    }

    public Long getCnt() {
        return cnt;
    }
}
