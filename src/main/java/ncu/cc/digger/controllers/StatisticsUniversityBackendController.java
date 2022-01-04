package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.models.statistics.*;
import ncu.cc.digger.repositories.UnivReportViewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.STATISTICS + Routes.STATISTICS_UNIVERSITIES + Routes.STATISTICS_UNIV_BACKEND)
public class StatisticsUniversityBackendController {
    private final UnivReportViewRepository univReportViewRepository;

    public StatisticsUniversityBackendController(UnivReportViewRepository univReportViewRepository) {
        this.univReportViewRepository = univReportViewRepository;
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_SEVERITY)
    public List<SeverityLevelStatistics> severity() {
        return this.univReportViewRepository.findSeverityLevelStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_SEVERITY + "/{code}")
    public List<SeverityLevelStatistics> severityCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findSeverityLevelStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_EDNS)
    public List<NonCompliantEdnsStatistics> edns() {
        return this.univReportViewRepository.findNonCompliantEdnsStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_EDNS + "/{code}")
    public List<NonCompliantEdnsStatistics> ednsCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findNonCompliantEdnsStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_IPV6)
    public List<IPv6AvailableStatistics> ipv6() {
        return this.univReportViewRepository.findIPv6AvailableStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_IPV6 + "/{code}")
    public List<IPv6AvailableStatistics> ipv6Country(@PathVariable("code") String code) {
        return this.univReportViewRepository.findIPv6AvailableStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_DNSSEC)
    public List<DnssecEnabledStatistics> dnssec() {
        return this.univReportViewRepository.findDnssecEnabledStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_DNSSEC + "/{code}")
    public List<DnssecEnabledStatistics> dnssecCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findDnssecEnabledStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_PROBLEMS)
    public List<NumberOfProblemsStatistics> problems() {
        return this.univReportViewRepository.findNumberOfProblemsStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_PROBLEMS + "/{code}")
    public List<NumberOfProblemsStatistics> problemsCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findNumberOfProblemsStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_RRSET)
    public List<RrsetInconsistencyStatistics> rrset() {
        return this.univReportViewRepository.findRrsetInconsistencyStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_RRSET + "/{code}")
    public List<RrsetInconsistencyStatistics> rrset(@PathVariable("code") String code) {
        return this.univReportViewRepository.findRrsetInconsistencyStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_SERVERS)
    public List<NumberOfServersStatistics> servers() {
        return this.univReportViewRepository.findNumberOfServersStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_SERVERS + "/{code}")
    public List<NumberOfServersStatistics> serversCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findNumberOfServersStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_AXFR)
    public List<OpenAxfrStatistics> axfr() {
        return this.univReportViewRepository.findOpenAxfrStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_AXFR + "/{code}")
    public List<OpenAxfrStatistics> axfrCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findOpenAxfrStatistics(code);
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_RECURSIVE)
    public List<OpenRecursiveStatistics> recursive() {
        return this.univReportViewRepository.findOpenRecursiveStatistics();
    }

    @GetMapping(Routes.STATISTICS_UNIVERSITIES_RECURSIVE + "/{code}")
    public List<OpenRecursiveStatistics> recursiveCountry(@PathVariable("code") String code) {
        return this.univReportViewRepository.findOpenRecursiveStatistics(code);
    }
}
