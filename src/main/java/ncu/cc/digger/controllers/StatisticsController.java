package ncu.cc.digger.controllers;

import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.models.statistics.*;
import ncu.cc.digger.repositories.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(Routes.STATISTICS)
public class StatisticsController {
    private final ReportRepository reportRepository;

    public StatisticsController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping
    @MenuItem(value = MenuPaths.STATISTICS, order = MenuOrders.STATISTICS)
    public String index() {
        return Views.STATISTICS;
    }

    @GetMapping(Routes.STATISTICS_SEVERITY)
    @MenuItem(value = MenuPaths.STATISTICS_SEVERITY, order = 1)
    public String severity() {
        return Views.STATISTICS;
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_SEVERITY)
    @ResponseBody
    public List<SeverityLevelStatistics> findSeverityLevelStatistics() {
        return reportRepository.findSeverityLevelStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_EDNS)
    @ResponseBody
    public List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics() {
        return reportRepository.findNonCompliantEdnsStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_IPV6)
    @ResponseBody
    public List<IPv6AvailableStatistics> findIPv6AvailableStatistics() {
        return reportRepository.findIPv6AvailableStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_DNSSEC)
    @ResponseBody
    public List<DnssecEnabledStatistics> findDnssecEnabledStatistics() {
        return reportRepository.findDnssecEnabledStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_PROBLEMS)
    @ResponseBody
    public List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics() {
        return reportRepository.findNumberOfProblemsStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_SERVERS)
    @ResponseBody
    public List<NumberOfServersStatistics> findNumberOfServersStatistics() {
        return reportRepository.findNumberOfServersStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_AXFR)
    @ResponseBody
    public List<OpenAxfrStatistics> findOpenAxfrStatistics() {
        return reportRepository.findOpenAxfrStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_RECURSIVE)
    @ResponseBody
    public List<OpenRecursiveStatistics> findOpenRecursiveStatistics() {
        return reportRepository.findOpenRecursiveStatistics();
    }

    @GetMapping(Routes.STATISTICS_BACKEND + Routes.STATISTICS_BACKEND_RRSET)
    @ResponseBody
    public List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics() {
        return reportRepository.findRrsetInconsistencyStatistics();
    }
}
