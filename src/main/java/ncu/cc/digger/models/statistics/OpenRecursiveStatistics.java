package ncu.cc.digger.models.statistics;

public class OpenRecursiveStatistics implements GroupByByteBoolean {
    private final Byte  openRecursive;
    private final Long  cnt;

    public OpenRecursiveStatistics(Byte openRecursive, Long cnt) {
        this.openRecursive = openRecursive;
        this.cnt = cnt;
    }

    public Byte getOpenRecursive() {
        return openRecursive;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return openRecursive;
    }
}
