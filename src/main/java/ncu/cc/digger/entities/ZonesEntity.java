package ncu.cc.digger.entities;

import ncu.cc.digger.constants.Constants;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "zones", schema = Constants.DB_SCHEMA, catalog = "")
public class ZonesEntity {
    private String id;
    private String parentZone;
    private String masterName;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "master_name", nullable = true, length = 255)
    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        ZonesEntity that = (ZonesEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parentZone, that.parentZone) &&
                Objects.equals(masterName, that.masterName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentZone, masterName, email, createdAt, updatedAt);
    }
}
