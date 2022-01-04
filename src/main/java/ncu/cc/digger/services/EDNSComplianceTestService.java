package ncu.cc.digger.services;

import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Set;

public interface EDNSComplianceTestService {
    Mono<EDNSComplianceTestResult> performEDNSTests(String zone, String server);

    enum EDNSStatus {
        OK,
        HAVE_SOA("soa"),
        NO_AA("noaa"),
        NO_SOA("nosoa"),
        ECHOED("echoed"),
        VERSION_NOT_ZERO("version-not-zero"),
        NO_OPT("noopt"),
        DO_REQUIRED("nodo"),
        Z_BIT_TO_BE_CLEAR("z"),
        OVER512("over-512-bytes"),
        RD_FLAG("rd"),
        COOKIE,
        SUBNET;

        private final String value;


        EDNSStatus(String value) {
            this.value = value;
        }
        EDNSStatus() {
            this.value = name().toLowerCase();
        }


        public String getValue() {
            return value;
        }
    }

    class EDNSComplianceTestResult {
        private boolean success;
        private String report;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }
    }

    class EDNSComplianceSingleTestResult {
        private String command;
        private boolean success = false;
        private Set<String> errors = new LinkedHashSet<>();
        private Set<String> extra = new LinkedHashSet<>();
        private String tag;
        private String result;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public Set<String> getErrors() {
            return errors;
        }

        public void setErrors(Set<String> errors) {
            this.errors = errors;
        }

        public Set<String> getExtra() {
            return extra;
        }

        public void setExtra(Set<String> extra) {
            this.extra = extra;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
