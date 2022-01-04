package ncu.cc.digger.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SceneZoneEntityPK implements Serializable {
    private Integer sceneId;
    private String zoneId;

    @Column(name = "scene_id", nullable = false)
    @Id
    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    @Column(name = "zone_id", nullable = false, length = 255)
    @Id
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SceneZoneEntityPK that = (SceneZoneEntityPK) o;
        return Objects.equals(sceneId, that.sceneId) &&
                Objects.equals(zoneId, that.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, zoneId);
    }
}
