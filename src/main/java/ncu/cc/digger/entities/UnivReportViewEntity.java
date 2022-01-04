package ncu.cc.digger.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "univ_report_view", schema = "digger_db", catalog = "")
public class UnivReportViewEntity {
    private String zoneId;
    private String zoneName;
    private Integer rank;
    private String countryCode;
    private String parentZone;
    private Integer score;
    private String soaEmail;
    private Long soaSerialno;
    private Byte severityLevel;
    private Integer severityUrgent;
    private Integer severityHigh;
    private Integer severityMedium;
    private Integer severityLow;
    private Integer severityInfo;
    private Byte dnssecEnabled;
    private Byte ipv6Available;
    private Byte soaInconsistency;
    private Byte openRecursive;
    private Byte openAxfr;
    private Byte nonCompliantEdns;
    private Byte serverNotWorking;
    private Byte rrsetInconsistency;
    private Integer numberOfServers;
    private Integer numberOfProblems;
    private String jsonReport;
    private String remoteAddress;
    private Timestamp updatedAt;

    @Id
    @Column(name = "zone_id", nullable = false, length = 255)
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "zone_name", nullable = false, length = 255)
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Basic
    @Column(name = "rank", nullable = false)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "country_code", nullable = false, length = 24)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "parent_zone", nullable = true, length = 255)
    public String getParentZone() {
        return parentZone;
    }

    public void setParentZone(String parentZone) {
        this.parentZone = parentZone;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "soa_email", nullable = true, length = 72)
    public String getSoaEmail() {
        return soaEmail;
    }

    public void setSoaEmail(String soaEmail) {
        this.soaEmail = soaEmail;
    }

    @Basic
    @Column(name = "soa_serialno", nullable = true)
    public Long getSoaSerialno() {
        return soaSerialno;
    }

    public void setSoaSerialno(Long soaSerialno) {
        this.soaSerialno = soaSerialno;
    }

    @Basic
    @Column(name = "severity_level", nullable = true)
    public Byte getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Byte severityLevel) {
        this.severityLevel = severityLevel;
    }

    @Basic
    @Column(name = "severity_urgent", nullable = true)
    public Integer getSeverityUrgent() {
        return severityUrgent;
    }

    public void setSeverityUrgent(Integer severityUrgent) {
        this.severityUrgent = severityUrgent;
    }

    @Basic
    @Column(name = "severity_high", nullable = true)
    public Integer getSeverityHigh() {
        return severityHigh;
    }

    public void setSeverityHigh(Integer severityHigh) {
        this.severityHigh = severityHigh;
    }

    @Basic
    @Column(name = "severity_medium", nullable = true)
    public Integer getSeverityMedium() {
        return severityMedium;
    }

    public void setSeverityMedium(Integer severityMedium) {
        this.severityMedium = severityMedium;
    }

    @Basic
    @Column(name = "severity_low", nullable = true)
    public Integer getSeverityLow() {
        return severityLow;
    }

    public void setSeverityLow(Integer severityLow) {
        this.severityLow = severityLow;
    }

    @Basic
    @Column(name = "severity_info", nullable = true)
    public Integer getSeverityInfo() {
        return severityInfo;
    }

    public void setSeverityInfo(Integer severityInfo) {
        this.severityInfo = severityInfo;
    }

    @Basic
    @Column(name = "dnssec_enabled", nullable = true)
    public Byte getDnssecEnabled() {
        return dnssecEnabled;
    }

    public void setDnssecEnabled(Byte dnssecEnabled) {
        this.dnssecEnabled = dnssecEnabled;
    }

    @Basic
    @Column(name = "ipv6_available", nullable = true)
    public Byte getIpv6Available() {
        return ipv6Available;
    }

    public void setIpv6Available(Byte ipv6Available) {
        this.ipv6Available = ipv6Available;
    }

    @Basic
    @Column(name = "soa_inconsistency", nullable = true)
    public Byte getSoaInconsistency() {
        return soaInconsistency;
    }

    public void setSoaInconsistency(Byte soaInconsistency) {
        this.soaInconsistency = soaInconsistency;
    }

    @Basic
    @Column(name = "open_recursive", nullable = true)
    public Byte getOpenRecursive() {
        return openRecursive;
    }

    public void setOpenRecursive(Byte openRecursive) {
        this.openRecursive = openRecursive;
    }

    @Basic
    @Column(name = "open_axfr", nullable = true)
    public Byte getOpenAxfr() {
        return openAxfr;
    }

    public void setOpenAxfr(Byte openAxfr) {
        this.openAxfr = openAxfr;
    }

    @Basic
    @Column(name = "non_compliant_edns", nullable = true)
    public Byte getNonCompliantEdns() {
        return nonCompliantEdns;
    }

    public void setNonCompliantEdns(Byte nonCompliantEdns) {
        this.nonCompliantEdns = nonCompliantEdns;
    }

    @Basic
    @Column(name = "server_not_working", nullable = true)
    public Byte getServerNotWorking() {
        return serverNotWorking;
    }

    public void setServerNotWorking(Byte serverNotWorking) {
        this.serverNotWorking = serverNotWorking;
    }

    @Basic
    @Column(name = "rrset_inconsistency", nullable = true)
    public Byte getRrsetInconsistency() {
        return rrsetInconsistency;
    }

    public void setRrsetInconsistency(Byte rrsetInconsistency) {
        this.rrsetInconsistency = rrsetInconsistency;
    }

    @Basic
    @Column(name = "number_of_servers", nullable = true)
    public Integer getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(Integer numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    @Basic
    @Column(name = "number_of_problems", nullable = true)
    public Integer getNumberOfProblems() {
        return numberOfProblems;
    }

    public void setNumberOfProblems(Integer numberOfProblems) {
        this.numberOfProblems = numberOfProblems;
    }

    @Basic
    @Column(name = "json_report", nullable = true, length = -1)
    public String getJsonReport() {
        return jsonReport;
    }

    public void setJsonReport(String jsonReport) {
        this.jsonReport = jsonReport;
    }

    @Basic
    @Column(name = "remote_address", nullable = true, length = 128)
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnivReportViewEntity that = (UnivReportViewEntity) o;
        return Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(zoneName, that.zoneName) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(parentZone, that.parentZone) &&
                Objects.equals(score, that.score) &&
                Objects.equals(soaEmail, that.soaEmail) &&
                Objects.equals(soaSerialno, that.soaSerialno) &&
                Objects.equals(severityLevel, that.severityLevel) &&
                Objects.equals(severityUrgent, that.severityUrgent) &&
                Objects.equals(severityHigh, that.severityHigh) &&
                Objects.equals(severityMedium, that.severityMedium) &&
                Objects.equals(severityLow, that.severityLow) &&
                Objects.equals(severityInfo, that.severityInfo) &&
                Objects.equals(dnssecEnabled, that.dnssecEnabled) &&
                Objects.equals(ipv6Available, that.ipv6Available) &&
                Objects.equals(soaInconsistency, that.soaInconsistency) &&
                Objects.equals(openRecursive, that.openRecursive) &&
                Objects.equals(openAxfr, that.openAxfr) &&
                Objects.equals(nonCompliantEdns, that.nonCompliantEdns) &&
                Objects.equals(serverNotWorking, that.serverNotWorking) &&
                Objects.equals(rrsetInconsistency, that.rrsetInconsistency) &&
                Objects.equals(numberOfServers, that.numberOfServers) &&
                Objects.equals(numberOfProblems, that.numberOfProblems) &&
                Objects.equals(jsonReport, that.jsonReport) &&
                Objects.equals(remoteAddress, that.remoteAddress) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, zoneName, rank, countryCode, parentZone, score, soaEmail, soaSerialno, severityLevel, severityUrgent, severityHigh, severityMedium, severityLow, severityInfo, dnssecEnabled, ipv6Available, soaInconsistency, openRecursive, openAxfr, nonCompliantEdns, serverNotWorking, rrsetInconsistency, numberOfServers, numberOfProblems, jsonReport, remoteAddress, updatedAt);
    }
}
