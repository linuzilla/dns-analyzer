package ncu.cc.digger.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "query_queue", schema = "digger_db", catalog = "")
public class QueryQueueEntity {
    private String zoneId;
    private Timestamp serviceAt;
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
    @Column(name = "service_at", nullable = true)
    public Timestamp getServiceAt() {
        return serviceAt;
    }

    public void setServiceAt(Timestamp serviceAt) {
        this.serviceAt = serviceAt;
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
        QueryQueueEntity that = (QueryQueueEntity) o;
        return Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(serviceAt, that.serviceAt) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, serviceAt, createdAt, updatedAt);
    }
}
