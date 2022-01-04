package ncu.cc.digger.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class SceneUserViewEntityPK implements Serializable {
    private Integer sceneId;
    private Integer userId;

    @Column(name = "scene_id", nullable = false)
    @Id
    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
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
        SceneUserViewEntityPK that = (SceneUserViewEntityPK) o;
        return Objects.equals(sceneId, that.sceneId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, userId);
    }
}
