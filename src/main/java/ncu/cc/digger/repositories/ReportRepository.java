package ncu.cc.digger.repositories;

import ncu.cc.digger.entities.ReportEntity;
import ncu.cc.digger.models.statistics.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity,String>, PagingAndSortingRepository<ReportEntity,String> {
    Page<ReportEntity> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    List<ReportEntity> findByIpv6AvailableIsNull();
    List<ReportEntity> findByParentZoneIsNull();

    @Query("SELECT new ncu.cc.digger.models.statistics.SeverityLevelStatistics(e.severityLevel, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.severityLevel")
    List<SeverityLevelStatistics> findSeverityLevelStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NonCompliantEdnsStatistics(e.nonCompliantEdns, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.nonCompliantEdns")
    List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.IPv6AvailableStatistics(e.ipv6Available, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.ipv6Available")
    List<IPv6AvailableStatistics> findIPv6AvailableStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.DnssecEnabledStatistics(e.dnssecEnabled, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.dnssecEnabled")
    List<DnssecEnabledStatistics> findDnssecEnabledStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfProblemsStatistics(e.numberOfProblems, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.numberOfProblems")
    List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfServersStatistics(e.numberOfServers, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.numberOfServers")
    List<NumberOfServersStatistics> findNumberOfServersStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenAxfrStatistics(e.openAxfr, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.openAxfr")
    List<OpenAxfrStatistics> findOpenAxfrStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.OpenRecursiveStatistics(e.openRecursive, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.openRecursive")
    List<OpenRecursiveStatistics> findOpenRecursiveStatistics();

    @Query("SELECT new ncu.cc.digger.models.statistics.RrsetInconsistencyStatistics(e.rrsetInconsistency, COUNT(e)) " +
            "FROM #{#entityName} e " +
            "GROUP BY e.rrsetInconsistency")
    List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics();

    //

//    @Query("SELECT new ncu.cc.digger.models.statistics.SeverityLevelStatistics(e.severityLevel, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.severityLevel")
//    List<SeverityLevelStatistics> findSeverityLevelStatisticsByParentZone(String parentZone);
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.NonCompliantEdnsStatistics(e.nonCompliantEdns, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.nonCompliantEdns")
//    List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatisticsByParentZone(String parentZone);
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.IPv6AvailableStatistics(e.ipv6Available, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.ipv6Available")
//    List<IPv6AvailableStatistics> findIPv6AvailableStatisticsByParentZone(String parentZone);
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfProblemsStatistics(e.numberOfProblems, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.numberOfProblems")
//    List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics();
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.NumberOfServersStatistics(e.numberOfServers, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.numberOfServers")
//    List<NumberOfServersStatistics> findNumberOfServersStatistics();
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.OpenAxfrStatistics(e.openAxfr, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.openAxfr")
//    List<OpenAxfrStatistics> findOpenAxfrStatistics();
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.OpenRecursiveStatistics(e.openRecursive, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.openRecursive")
//    List<OpenRecursiveStatistics> findOpenRecursiveStatistics();
//
//    @Query("SELECT new ncu.cc.digger.models.statistics.RrsetInconsistencyStatistics(e.rrsetInconsistency, COUNT(e)) " +
//            "FROM #{#entityName} e " +
//            "GROUP BY e.rrsetInconsistency")
//    List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics();
}
