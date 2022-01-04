package ncu.cc.digger.services;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.converters.HealthResultToEntity;
import ncu.cc.digger.models.DNSHealthCheckResult;
import ncu.cc.digger.models.SeverityLevel;
import ncu.cc.digger.repositories.ReportHistoryRepository;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DNSQueryAndStoreServiceImpl implements DNSQueryAndStoreService {
    private static final Logger logger = LoggerFactory.getLogger(DNSQueryAndStoreServiceImpl.class);

    private final DNSHealthCheckService dnsHealthCheckService;
    private final DNSHealthAnalyzeService dnsHealthAnalyzeService;
    private final ReportRepository reportRepository;
    private final ReportHistoryRepository reportHistoryRepository;

    public DNSQueryAndStoreServiceImpl(
            DNSHealthCheckService dnsHealthCheckService,
            DNSHealthAnalyzeService dnsHealthAnalyzeService,
            ReportRepository reportRepository,
            ReportHistoryRepository reportHistoryRepository) {
        this.dnsHealthCheckService = dnsHealthCheckService;
        this.dnsHealthAnalyzeService = dnsHealthAnalyzeService;
        this.reportRepository = reportRepository;
        this.reportHistoryRepository = reportHistoryRepository;
    }

    @Override
    public Mono<DNSHealthCheckResult> digAndStore(String queryZone, String queryFrom) {
        String zone = DNSUtil.zoneNameNormalize(queryZone);

        logger.info("digAndStore({}, {})", queryZone, queryFrom);

        return dnsHealthCheckService.check(zone)
                .doOnNext(dnsHealthAnalyzeService::analyze)
                .doOnNext(healthCheckResult -> {
                    if (healthCheckResult.getSeverityLevel().getLevel() < SeverityLevel.FATAL.getLevel()) {
                        try {
                            logger.info("persistent: {}", zone);
                            reportRepository.save(HealthResultToEntity.healthResultToReport(healthCheckResult, queryFrom));
                            reportHistoryRepository.save(HealthResultToEntity.healthResultToReportHistory(healthCheckResult, queryFrom));
                        } catch (Exception e) {
                            logger.error("exception: {}: {}", e.getClass().getName(), e.getMessage());
                        }
                    } else {
                        logger.warn("Fatal error query zone: {}", queryZone);
                    }
                });
    }

    @Override
    public Mono<DNSHealthCheckResult> fetchOrDig(String queryZone, String queryFrom) {
        String zone = DNSUtil.zoneNameNormalize(queryZone);

        return reportRepository.findById(DNSUtil.stripTrailingDot(zone))
                .map(HealthResultToEntity::reportToHealthResult)
                .map(Mono::just)
                .orElse(digAndStore(zone, queryFrom));
    }
}
