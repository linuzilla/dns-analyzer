package ncu.cc.digger.entities;

import ncu.cc.digger.constants.Constants;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "scene_users", schema = Constants.DB_SCHEMA, catalog = "")
@IdClass(SceneUserEntityPK.class)
public class SceneUserEntity {
    private Integer sceneId;
    private Integer userId;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Id
    @Column(name = "scene_id", nullable = false)
    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
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
        SceneUserEntity that = (SceneUserEntity) o;
        return Objects.equals(sceneId, that.sceneId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(role, that.role) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, userId, role, createdAt, updatedAt);
    }
}
