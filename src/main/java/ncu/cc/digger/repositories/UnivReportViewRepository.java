package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.SceneReportViewEntity;
import ncu.cc.digger.entities.UnivReportViewEntity;
import ncu.cc.digger.models.statistics.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnivReportViewRepository extends JpaRepository<UnivReportViewEntity,String> {
    Page<UnivReportViewEntity> findAllByOrderByRank(Pageable pageable);
    Page<UnivReportViewEntity> findAllByCountryCodeOrderByRank(Pageable pageable);
    Page<UnivReportViewEntity> findAllByOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(Pageable pageable);
    Page<UnivReportViewEntity> findByCountryCodeOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(String countryCode, Pageable pageable);


    @Query("SELECT new ncu.cc.digger.models.statistics.SeverityLevelStatistics(e.severityLevel, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.severityLevel")
    List<SeverityLevelStatistics> findSeverityLevelStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NonCompliantEdnsStatistics(e.nonCompliantEdns, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.nonCompliantEdns")
    List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.IPv6AvailableStatistics(e.ipv6Available, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.ipv6Available")
    List<IPv6AvailableStatistics> findIPv6AvailableStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.DnssecEnabledStatistics(e.dnssecEnabled, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.dnssecEnabled")
    List<DnssecEnabledStatistics> findDnssecEnabledStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfProblemsStatistics(e.numberOfProblems, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfProblems")
    List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfServersStatistics(e.numberOfServers, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfServers")
    List<NumberOfServersStatistics> findNumberOfServersStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenAxfrStatistics(e.openAxfr, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.openAxfr")
    List<OpenAxfrStatistics> findOpenAxfrStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenRecursiveStatistics(e.openRecursive, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.openRecursive")
    List<OpenRecursiveStatistics> findOpenRecursiveStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.RrsetInconsistencyStatistics(e.rrsetInconsistency, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.updatedAt IS NOT NULL " +
            "GROUP BY e.rrsetInconsistency")
    List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics();

    ////////////// With Country Code

    @Query("SELECT new ncu.cc.digger.models.statistics.SeverityLevelStatistics(e.severityLevel, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.severityLevel")
    List<SeverityLevelStatistics> findSeverityLevelStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.NonCompliantEdnsStatistics(e.nonCompliantEdns, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.nonCompliantEdns")
    List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.IPv6AvailableStatistics(e.ipv6Available, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.ipv6Available")
    List<IPv6AvailableStatistics> findIPv6AvailableStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.DnssecEnabledStatistics(e.dnssecEnabled, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.dnssecEnabled")
    List<DnssecEnabledStatistics> findDnssecEnabledStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfProblemsStatistics(e.numberOfProblems, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfProblems")
    List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfServersStatistics(e.numberOfServers, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.numberOfServers")
    List<NumberOfServersStatistics> findNumberOfServersStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenAxfrStatistics(e.openAxfr, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.openAxfr")
    List<OpenAxfrStatistics> findOpenAxfrStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenRecursiveStatistics(e.openRecursive, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.openRecursive")
    List<OpenRecursiveStatistics> findOpenRecursiveStatistics(String countryCode);

    @Query("SELECT new ncu.cc.digger.models.statistics.RrsetInconsistencyStatistics(e.rrsetInconsistency, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "WHERE e.countryCode = ?1 AND e.updatedAt IS NOT NULL " +
            "GROUP BY e.rrsetInconsistency")
    List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics(String countryCode);
}
