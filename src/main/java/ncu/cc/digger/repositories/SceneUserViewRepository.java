package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.SceneUserViewEntity;
import ncu.cc.digger.entities.SceneUserViewEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SceneUserViewRepository extends JpaRepository<SceneUserViewEntity, SceneUserViewEntityPK> {
    List<SceneUserViewEntity> findByUserId(int userId);
}
