package ncu.cc.digger.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "country_ranking", schema = "digger_db", catalog = "")
@IdClass(CountryRankingEntityPK.class)
public class CountryRankingEntity {
    private Integer eventId;
    private String countryCode;
    private Integer rank;
    private Integer score;
    private Integer zones;
    private Integer dnssec;
    private Integer ipv6;
    private Integer edns;
    private Integer rrset;
    private Integer axfr;
    private Integer recursion;
    private Integer normal;
    private Integer info;
    private Integer low;
    private Integer medium;
    private Integer high;
    private Integer urgent;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Transient
    private String countryName;

    @Id
    @Column(name = "event_id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Id
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "rank")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "zones")
    public Integer getZones() {
        return zones;
    }

    public void setZones(Integer zones) {
        this.zones = zones;
    }

    @Basic
    @Column(name = "dnssec")
    public Integer getDnssec() {
        return dnssec;
    }

    public void setDnssec(Integer dnssec) {
        this.dnssec = dnssec;
    }

    @Basic
    @Column(name = "ipv6")
    public Integer getIpv6() {
        return ipv6;
    }

    public void setIpv6(Integer ipv6) {
        this.ipv6 = ipv6;
    }

    @Basic
    @Column(name = "edns")
    public Integer getEdns() {
        return edns;
    }

    public void setEdns(Integer edns) {
        this.edns = edns;
    }

    @Basic
    @Column(name = "rrset")
    public Integer getRrset() {
        return rrset;
    }

    public void setRrset(Integer rrset) {
        this.rrset = rrset;
    }

    @Basic
    @Column(name = "axfr")
    public Integer getAxfr() {
        return axfr;
    }

    public void setAxfr(Integer axfr) {
        this.axfr = axfr;
    }

    @Basic
    @Column(name = "recursion")
    public Integer getRecursion() {
        return recursion;
    }

    public void setRecursion(Integer recursion) {
        this.recursion = recursion;
    }

    @Basic
    @Column(name = "normal")
    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }

    @Basic
    @Column(name = "info")
    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    @Basic
    @Column(name = "low")
    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    @Basic
    @Column(name = "medium")
    public Integer getMedium() {
        return medium;
    }

    public void setMedium(Integer medium) {
        this.medium = medium;
    }

    @Basic
    @Column(name = "high")
    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    @Basic
    @Column(name = "urgent")
    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Transient
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryRankingEntity that = (CountryRankingEntity) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(score, that.score) &&
                Objects.equals(zones, that.zones) &&
                Objects.equals(dnssec, that.dnssec) &&
                Objects.equals(ipv6, that.ipv6) &&
                Objects.equals(edns, that.edns) &&
                Objects.equals(rrset, that.rrset) &&
                Objects.equals(axfr, that.axfr) &&
                Objects.equals(recursion, that.recursion) &&
                Objects.equals(normal, that.normal) &&
                Objects.equals(info, that.info) &&
                Objects.equals(low, that.low) &&
                Objects.equals(medium, that.medium) &&
                Objects.equals(high, that.high) &&
                Objects.equals(urgent, that.urgent) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, countryCode, rank, score, zones, dnssec, ipv6, edns, rrset, axfr, recursion, normal, info, low, medium, high, urgent, createdAt, updatedAt);
    }
}
