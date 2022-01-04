package ncu.cc.digger.models.statistics;

public class RrsetInconsistencyStatistics implements GroupByByteBoolean {
    private final Byte rrsetInconsistency;
    private final Long cnt;

    public RrsetInconsistencyStatistics(Byte rrsetInconsistency, Long cnt) {
        this.rrsetInconsistency = rrsetInconsistency;
        this.cnt = cnt;
    }

    public Byte getRrsetInconsistency() {
        return rrsetInconsistency;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return rrsetInconsistency;
    }
}
