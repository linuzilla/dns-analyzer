package ncu.cc.digger.models;

import java.util.Map;

public class LocalizeProblem {
    public static class ProblemSpec {
        private SeverityLevel severityLevel;
        private String severity;
        private String problem;
        private String recommendation;

        public SeverityLevel getSeverityLevel() {
            return severityLevel;
        }

        public void setSeverityLevel(SeverityLevel severityLevel) {
            this.severityLevel = severityLevel;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getRecommendation() {
            return recommendation;
        }

        public void setRecommendation(String recommendation) {
            this.recommendation = recommendation;
        }
    }
    private String name;
    private Map<String, ProblemSpec> problems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ProblemSpec> getProblems() {
        return problems;
    }

    public void setProblems(Map<String, ProblemSpec> problems) {
        this.problems = problems;
    }
}
