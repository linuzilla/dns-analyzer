package ncu.cc.digger.models.statistics;

public class OpenAxfrStatistics implements GroupByByteBoolean {
    private final Byte openAxfr;
    private final Long cnt;

    public OpenAxfrStatistics(Byte openAxfr, Long cnt) {
        this.openAxfr = openAxfr;
        this.cnt = cnt;
    }

    public Byte getOpenAxfr() {
        return openAxfr;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return openAxfr;
    }
}
