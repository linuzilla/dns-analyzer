package ncu.cc.digger.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "scene_report_view", schema = "digger_db", catalog = "")
@IdClass(SceneReportViewEntityPK.class)
public class SceneReportViewEntity {
    private Integer sceneId;
    private String zoneId;
    private String zoneName;
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
    private Timestamp updatedAt;

    @Id
    @Basic
    @Column(name = "scene_id", nullable = false)
    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    @Id
    @Basic
    @Column(name = "zone_id", nullable = false, length = 255)
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "zone_name", nullable = false, length = 24)
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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
    @Column(name = "ipv6_available", nullable = false)
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
        SceneReportViewEntity that = (SceneReportViewEntity) o;
        return Objects.equals(sceneId, that.sceneId) &&
                Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(zoneName, that.zoneName) &&
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
                Objects.equals(soaInconsistency, that.soaInconsistency) &&
                Objects.equals(openRecursive, that.openRecursive) &&
                Objects.equals(openAxfr, that.openAxfr) &&
                Objects.equals(nonCompliantEdns, that.nonCompliantEdns) &&
                Objects.equals(serverNotWorking, that.serverNotWorking) &&
                Objects.equals(rrsetInconsistency, that.rrsetInconsistency) &&
                Objects.equals(numberOfServers, that.numberOfServers) &&
                Objects.equals(numberOfProblems, that.numberOfProblems) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, zoneId, zoneName, score, soaEmail, soaSerialno, severityLevel, severityUrgent, severityHigh, severityMedium, severityLow, severityInfo, dnssecEnabled, soaInconsistency, openRecursive, openAxfr, nonCompliantEdns, serverNotWorking, rrsetInconsistency, numberOfServers, numberOfProblems, updatedAt);
    }
}
