package ncu.cc.digger.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "universities", schema = "digger_db", catalog = "")
public class UniversitiesEntity {
    private String zoneId;
    private String zoneName;
    private Integer rank;
    private String countryCode;
    private Timestamp createdAt;
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
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @UpdateTimestamp
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
        UniversitiesEntity that = (UniversitiesEntity) o;
        return Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(zoneName, that.zoneName) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, zoneName, rank, countryCode, createdAt, updatedAt);
    }
}
