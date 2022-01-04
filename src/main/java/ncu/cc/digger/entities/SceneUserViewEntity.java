package ncu.cc.digger.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "scene_user_view", schema = "digger_db", catalog = "")
@IdClass(SceneUserViewEntityPK.class)
public class SceneUserViewEntity {
    private Integer sceneId;
    @JsonIgnore
    private Integer userId;
    private String sceneName;
    @JsonIgnore
    private Integer creatorId;
    @JsonIgnore
    private String role;
    private Timestamp createdAt;
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
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "scene_name", nullable = true, length = 24)
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    @Basic
    @Column(name = "creator_id", nullable = false)
    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        SceneUserViewEntity that = (SceneUserViewEntity) o;
        return Objects.equals(sceneId, that.sceneId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(sceneName, that.sceneName) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(role, that.role) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, userId, sceneName, creatorId, role, createdAt, updatedAt);
    }
}
