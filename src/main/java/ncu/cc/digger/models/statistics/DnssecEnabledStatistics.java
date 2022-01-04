package ncu.cc.digger.models.statistics;

public class DnssecEnabledStatistics implements GroupByByteBoolean {
    private final Byte dnssecEnabled;
    private final Long cnt;

    public DnssecEnabledStatistics(Byte dnssecEnabled, Long cnt) {
        this.dnssecEnabled = dnssecEnabled;
        this.cnt = cnt;
    }

    public Byte getDnssecEnabled() {
        return dnssecEnabled;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return dnssecEnabled;
    }
}
