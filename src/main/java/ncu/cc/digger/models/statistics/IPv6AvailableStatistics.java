package ncu.cc.digger.models.statistics;

public class IPv6AvailableStatistics implements GroupByByteBoolean {
    private final Byte ipv6Available;
    private final Long cnt;

    public IPv6AvailableStatistics(Byte ipv6Available, Long cnt) {
        this.ipv6Available = ipv6Available;
        this.cnt = cnt;
    }

    public Byte getIpv6Available() {
        return ipv6Available;
    }

    public Long getCnt() {
        return cnt;
    }

    @Override
    public Byte getBoolean() {
        return ipv6Available;
    }
}
