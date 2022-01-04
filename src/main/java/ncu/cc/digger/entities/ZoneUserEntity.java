package ncu.cc.digger.entities;

import ncu.cc.digger.constants.Constants;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "zone_users", schema = Constants.DB_SCHEMA, catalog = "")
@IdClass(ZoneUserEntityPK.class)
public class ZoneUserEntity {
    private String zoneId;
    private Integer userId;
    private Byte isAdmin;
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

    @Id
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
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
        ZoneUserEntity that = (ZoneUserEntity) o;
        return Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(isAdmin, that.isAdmin) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, userId, isAdmin, createdAt, updatedAt);
    }
}
