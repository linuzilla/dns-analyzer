package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.RankingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingEventRepository extends JpaRepository<RankingEventEntity,Integer> {
    List<RankingEventEntity> findAllByOrderByIdDesc();
    List<RankingEventEntity> findTop10ByOrderByIdDesc();
    Optional<RankingEventEntity> findFirstByOrderByIdDesc();
    Optional<RankingEventEntity> findByTag(String tag);
}
