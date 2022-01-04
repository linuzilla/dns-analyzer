package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.ReportHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportHistoryRepository extends JpaRepository<ReportHistoryEntity,Integer> {
}
