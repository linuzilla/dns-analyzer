package ncu.cc.digger.models.statistics;

public class NonCompliantEdnsStatistics implements GroupByByteBoolean {
    private final Byte nonCompliantEdns;
    private final Long cnt;

    public NonCompliantEdnsStatistics(Byte nonCompliantEdns, Long cnt) {
        this.nonCompliantEdns = nonCompliantEdns;
        this.cnt = cnt;
    }

    public Byte getNonCompliantEdns() {
        return nonCompliantEdns;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return nonCompliantEdns;
    }
}
