package ncu.cc.digger.models.statistics;

public class NumberOfProblemsStatistics {
    private final Integer numberOfProblems;
    private final Long cnt;

    public NumberOfProblemsStatistics(Integer numberOfProblems, Long cnt) {
        this.numberOfProblems = numberOfProblems;
        this.cnt = cnt;
    }

    public Integer getNumberOfProblems() {
        return numberOfProblems;
    }

    public Long getCnt() {
        return cnt;
    }
}
