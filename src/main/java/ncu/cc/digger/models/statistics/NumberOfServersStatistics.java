package ncu.cc.digger.models.statistics;

public class NumberOfServersStatistics {
    private final Integer numberOfServers;
    private final Long cnt;

    public NumberOfServersStatistics(Integer numberOfServers, Long cnt) {
        this.numberOfServers = numberOfServers;
        this.cnt = cnt;
    }

    public Integer getNumberOfServers() {
        return numberOfServers;
    }

    public Long getCnt() {
        return cnt;
    }
}
