package ncu.cc.digger.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ZoneUserEntityPK implements Serializable {
    private String zoneId;
    private Integer userId;

    @Column(name = "zone_id", nullable = false, length = 255)
    @Id
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneUserEntityPK that = (ZoneUserEntityPK) o;
        return Objects.equals(zoneId, that.zoneId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, userId);
    }
}
