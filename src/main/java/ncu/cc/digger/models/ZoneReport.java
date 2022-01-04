package ncu.cc.digger.models;

import ncu.cc.digger.models.dns.FlatResourceRecord;
import ncu.cc.digger.models.dns.Problem;
import ncu.cc.digger.models.dns.SOARecord;

import java.util.List;

public class ZoneReport {
    private String zone;
    private SOARecord soa;
    private String severity;
    private int severity_level;
    private String error;
    private List<FlatResourceRecord> common_ns_rrset;
    private List<FlatResourceRecord> parent_only_ns_rrset;
    private List<FlatResourceRecord> children_only_ns_rrset;
    private List<Problem> problems;
    private int number_of_problems;
    private int number_of_v4_servers;
    private int number_of_v6_servers;
    private int open_recursive;
    private int open_zone_transfer;
    private String timestamp;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public SOARecord getSoa() {
        return soa;
    }

    public void setSoa(SOARecord soa) {
        this.soa = soa;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public int getSeverity_level() {
        return severity_level;
    }

    public void setSeverity_level(int severity_level) {
        this.severity_level = severity_level;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<FlatResourceRecord> getCommon_ns_rrset() {
        return common_ns_rrset;
    }

    public void setCommon_ns_rrset(List<FlatResourceRecord> common_ns_rrset) {
        this.common_ns_rrset = common_ns_rrset;
    }

    public List<FlatResourceRecord> getParent_only_ns_rrset() {
        return parent_only_ns_rrset;
    }

    public void setParent_only_ns_rrset(List<FlatResourceRecord> parent_only_ns_rrset) {
        this.parent_only_ns_rrset = parent_only_ns_rrset;
    }

    public List<FlatResourceRecord> getChildren_only_ns_rrset() {
        return children_only_ns_rrset;
    }

    public void setChildren_only_ns_rrset(List<FlatResourceRecord> children_only_ns_rrset) {
        this.children_only_ns_rrset = children_only_ns_rrset;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public int getNumber_of_problems() {
        return number_of_problems;
    }

    public void setNumber_of_problems(int number_of_problems) {
        this.number_of_problems = number_of_problems;
    }

    public int getNumber_of_v4_servers() {
        return number_of_v4_servers;
    }

    public void setNumber_of_v4_servers(int number_of_v4_servers) {
        this.number_of_v4_servers = number_of_v4_servers;
    }

    public int getNumber_of_v6_servers() {
        return number_of_v6_servers;
    }

    public void setNumber_of_v6_servers(int number_of_v6_servers) {
        this.number_of_v6_servers = number_of_v6_servers;
    }

    public int getOpen_recursive() {
        return open_recursive;
    }

    public void setOpen_recursive(int open_recursive) {
        this.open_recursive = open_recursive;
    }

    public int getOpen_zone_transfer() {
        return open_zone_transfer;
    }

    public void setOpen_zone_transfer(int open_zone_transfer) {
        this.open_zone_transfer = open_zone_transfer;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
