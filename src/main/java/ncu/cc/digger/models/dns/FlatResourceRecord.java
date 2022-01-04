package ncu.cc.digger.models.dns;

import ncu.cc.digger.services.EDNSComplianceTestService;

public class FlatResourceRecord {
    private SOARecord soa;
    private String name;
    private String address;
    private Boolean responding;
    private boolean is_v6;
    private String reported_by;
    private String error;
    private EDNSComplianceTestService.EDNSComplianceTestResult edns;
    private boolean allowTransfer;
    private boolean allowRecursive;

    public boolean sameAs(FlatResourceRecord another) {
        if (this.address != null && another.address != null) {
            return this.address.equals(another.address);
        } else if (this.address == null && another.address == null) {
            return this.name.equals(another.name);
        } else {
            return false;
        }
    }

    public SOARecord getSoa() {
        return soa;
    }

    public void setSoa(SOARecord soa) {
        this.soa = soa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isResponding() {
        return responding;
    }

    public void setResponding(Boolean responding) {
        this.responding = responding;
    }

    public boolean isIs_v6() {
        return is_v6;
    }

    public boolean isIPv4() {
        return address != null && ! is_v6;
    }

    public void setIs_v6(boolean is_v6) {
        this.is_v6 = is_v6;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public EDNSComplianceTestService.EDNSComplianceTestResult getEdns() {
        return edns;
    }

    public void setEdns(EDNSComplianceTestService.EDNSComplianceTestResult edns) {
        this.edns = edns;
    }

    public boolean isAllowTransfer() {
        return allowTransfer;
    }

    public void setAllowTransfer(boolean allowTransfer) {
        this.allowTransfer = allowTransfer;
    }

    public boolean isAllowRecursive() {
        return allowRecursive;
    }

    public void setAllowRecursive(boolean allowRecursive) {
        this.allowRecursive = allowRecursive;
    }
}
