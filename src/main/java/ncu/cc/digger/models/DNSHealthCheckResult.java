package ncu.cc.digger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ncu.cc.commons.utils.JsonUtil;
import ncu.cc.digger.models.dns.FlatResourceRecord;
import ncu.cc.digger.utils.DNSUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DNSHealthCheckResult {
    public static class Severity {
        public int urgent;
        public int high;
        public int medium;
        public int low;
        public int info;
    }
    private String zone;
    private String parentZone;
    private SeverityLevel severityLevel;
    private Severity severity;
    private String soaEmail;
    private Long soaSerialNo;
    private boolean dnssecEnabled;
    private boolean soaInconsistency;
    private boolean openRecursive;
    private boolean openAxfr;
    private boolean nonCompliantEdns;
    private boolean serverNotWorking;
    private boolean rrsetInconsistency;
    private boolean ipv6Available;
    private int numberOfServers;
    private int numberOfProblems;
    private List<String> parentNameServers;
    @JsonIgnore
    private ParentNSRRSet parentRRSet;
    @JsonIgnore
    private List<DaughterRRSet> daughterRRSets;
    private List<FlatResourceRecord> parents;
    private List<FlatResourceRecord> daughters;
    private List<FlatResourceRecord> commons;
    @JsonIgnore
    private List<FlatResourceRecord> allNameServers;
    private List<DNSProblem> problems = new ArrayList<>();


    public static DNSHealthCheckResult fromJSON(String jsonString) {
        return new Gson().fromJson(jsonString, DNSHealthCheckResult.class);
    }

    public String toJSON() {
        return JsonUtil.gsonExcludeJsonIgnore().toJson(this);
    }

    public DNSHealthCheckResult setDaughterRRSets(List<DaughterRRSet> daughterRRSets) {
        this.daughterRRSets = daughterRRSets;

        if (this.daughterRRSets.size() > 0) {
            this.daughterRRSets.sort((o1, o2) -> o2.getNameServers().size() - o1.getNameServers().size());
            this.daughters = DNSUtil.nameServerRecordsToFlatResourceRecords(this.daughterRRSets.get(0).getNameServers());
        } else {
            this.daughters = Collections.emptyList();
        }

        return this;
    }

    public DNSHealthCheckResult setParents(List<FlatResourceRecord> parents) {
        this.parents = parents;
        return this;
    }

    public DNSHealthCheckResult setDaughters(List<FlatResourceRecord> daughters) {
        this.daughters = daughters;
        return this;
    }

    public void nsDifferents() {
        this.commons = new ArrayList<>();
        List<FlatResourceRecord> parent_only = new ArrayList<>();
        List<FlatResourceRecord> copyOfDaughter = new ArrayList<>(daughters);
        List<FlatResourceRecord> left = new ArrayList<>();

        for (FlatResourceRecord parent : parents) {
            boolean found = false;
            left = new ArrayList<>();

            for (FlatResourceRecord daughter: copyOfDaughter) {
                if (! found && parent.sameAs(daughter)) {
                    this.commons.add(parent);
                    found = true;
                } else {
                    left.add(daughter);
                }
            }

            if (found) {
                copyOfDaughter = left;
            } else {
                parent_only.add(parent);
            }
        }

        this.setParents(parent_only);
        this.setDaughters(left);

        this.allNameServers = new ArrayList<>(this.commons);
        this.allNameServers.addAll(this.parents);
        this.allNameServers.addAll(this.daughters);

        this.setRrsetInconsistency(this.getParents().size() + this.getDaughters().size() != 0);
        this.setNumberOfServers(this.allNameServers.size());
    }

    //  Getter and Setters

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getParentZone() {
        return parentZone;
    }

    public void setParentZone(String parentZone) {
        this.parentZone = parentZone;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getSoaEmail() {
        return soaEmail;
    }

    public void setSoaEmail(String soaEmail) {
        this.soaEmail = soaEmail;
    }

    public Long getSoaSerialNo() {
        return soaSerialNo;
    }

    public void setSoaSerialNo(Long soaSerialNo) {
        this.soaSerialNo = soaSerialNo;
    }

    public boolean isDnssecEnabled() {
        return dnssecEnabled;
    }

    public void setDnssecEnabled(boolean dnssecEnabled) {
        this.dnssecEnabled = dnssecEnabled;
    }

    public boolean isSoaInconsistency() {
        return soaInconsistency;
    }

    public void setSoaInconsistency(boolean soaInconsistency) {
        this.soaInconsistency = soaInconsistency;
    }

    public boolean isOpenRecursive() {
        return openRecursive;
    }

    public void setOpenRecursive(boolean openRecursive) {
        this.openRecursive = openRecursive;
    }

    public boolean isOpenAxfr() {
        return openAxfr;
    }

    public void setOpenAxfr(boolean openAxfr) {
        this.openAxfr = openAxfr;
    }

    public boolean isNonCompliantEdns() {
        return nonCompliantEdns;
    }

    public void setNonCompliantEdns(boolean nonCompliantEdns) {
        this.nonCompliantEdns = nonCompliantEdns;
    }

    public boolean isServerNotWorking() {
        return serverNotWorking;
    }

    public void setServerNotWorking(boolean serverNotWorking) {
        this.serverNotWorking = serverNotWorking;
    }

    public boolean isRrsetInconsistency() {
        return rrsetInconsistency;
    }

    public boolean isIpv6Available() {
        return ipv6Available;
    }

    public void setIpv6Available(boolean ipv6Available) {
        this.ipv6Available = ipv6Available;
    }

    public void setRrsetInconsistency(boolean rrsetInconsistency) {
        this.rrsetInconsistency = rrsetInconsistency;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public int getNumberOfProblems() {
        return numberOfProblems;
    }

    public void setNumberOfProblems(int numberOfProblems) {
        this.numberOfProblems = numberOfProblems;
    }

    public List<String> getParentNameServers() {
        return parentNameServers;
    }

    public void setParentNameServers(List<String> parentNameServers) {
        this.parentNameServers = parentNameServers;
    }

    public ParentNSRRSet getParentRRSet() {
        return parentRRSet;
    }

    public void setParentRRSet(ParentNSRRSet parentRRSet) {
        this.parentRRSet = parentRRSet;
    }

    public List<DaughterRRSet> getDaughterRRSets() {
        return daughterRRSets;
    }

    public List<FlatResourceRecord> getParents() {
        return parents;
    }

    public List<FlatResourceRecord> getDaughters() {
        return daughters;
    }

    public List<FlatResourceRecord> getCommons() {
        return commons;
    }

    public void setCommons(List<FlatResourceRecord> commons) {
        this.commons = commons;
    }

    public List<FlatResourceRecord> getAllNameServers() {
        return allNameServers;
    }

    public void setAllNameServers(List<FlatResourceRecord> allNameServers) {
        this.allNameServers = allNameServers;
    }

    public List<DNSProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<DNSProblem> problems) {
        this.problems = problems;
    }
}
