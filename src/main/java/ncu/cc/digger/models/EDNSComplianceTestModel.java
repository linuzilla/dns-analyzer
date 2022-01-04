package ncu.cc.digger.models;

import java.util.List;
import java.util.regex.Pattern;

public class EDNSComplianceTestModel {
    private final static Pattern TAG_IN_NAME_PATTERN = Pattern.compile(".*\\((.+)\\).*");

    public static class TestTemplate {
        private String tag;
        private String name;
        private String description;
        private String command;
        private List<String> expect;
        private String see;
        private List<EDNSExpects> ednsExpectsList;

        public void retrieveTagFromName() {
            var matcher = TAG_IN_NAME_PATTERN.matcher(this.name);

            if (matcher.matches()) {
                this.tag = matcher.group(1);
            }
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public List<String> getExpect() {
            return expect;
        }

        public void setExpect(List<String> expect) {
            this.expect = expect;
        }

        public String getSee() {
            return see;
        }

        public void setSee(String see) {
            this.see = see;
        }

        public List<EDNSExpects> getEdnsExpectsList() {
            return ednsExpectsList;
        }

        public void setEdnsExpectsList(List<EDNSExpects> ednsExpectsList) {
            this.ednsExpectsList = ednsExpectsList;
        }
    }

    private String name;
    private List<TestTemplate> tests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestTemplate> getTests() {
        return tests;
    }

    public void setTests(List<TestTemplate> tests) {
        this.tests = tests;
    }
}
