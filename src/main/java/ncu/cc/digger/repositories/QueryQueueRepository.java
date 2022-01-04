package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.QueryQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueryQueueRepository extends JpaRepository<QueryQueueEntity,String> {
    Optional<QueryQueueEntity> findFirstByServiceAtIsNullOrderByCreatedAt();
}
