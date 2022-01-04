package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.SceneReportViewEntity;
import ncu.cc.digger.entities.SceneReportViewEntityPK;
import ncu.cc.digger.models.statistics.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SceneReportViewRepository extends JpaRepository<SceneReportViewEntity, SceneReportViewEntityPK>,
        PagingAndSortingRepository<SceneReportViewEntity, SceneReportViewEntityPK> {
    Page<SceneReportViewEntity> findBySceneIdOrderBySeverityLevel(int sceneId, Pageable pageable);
    Page<SceneReportViewEntity> findBySceneIdOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(int sceneId, Pageable pageable);

    @Query("SELECT new ncu.cc.digger.models.statistics.SeverityLevelStatistics(e.severityLevel, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.severityLevel")
    List<SeverityLevelStatistics> findSeverityLevelStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.NonCompliantEdnsStatistics(e.nonCompliantEdns, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.nonCompliantEdns")
    List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.IPv6AvailableStatistics(e.ipv6Available, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.ipv6Available")
    List<IPv6AvailableStatistics> findIPv6AvailableStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.DnssecEnabledStatistics(e.dnssecEnabled, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.dnssecEnabled")
    List<DnssecEnabledStatistics> findDnssecEnabledStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfProblemsStatistics(e.numberOfProblems, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfProblems")
    List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfServersStatistics(e.numberOfServers, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfServers")
    List<NumberOfServersStatistics> findNumberOfServersStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenAxfrStatistics(e.openAxfr, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.openAxfr")
    List<OpenAxfrStatistics> findOpenAxfrStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenRecursiveStatistics(e.openRecursive, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.openRecursive")
    List<OpenRecursiveStatistics> findOpenRecursiveStatistics(int sceneId);

    @Query("SELECT new ncu.cc.digger.models.statistics.RrsetInconsistencyStatistics(e.rrsetInconsistency, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.sceneId = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.rrsetInconsistency")
    List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics(int sceneId);
}
