package ncu.cc.digger.schedulers;

import ncu.cc.commons.utils.ByteUtil;
import ncu.cc.digger.converters.HealthResultToEntity;
import ncu.cc.digger.models.DNSHealthCheckResult;
import ncu.cc.digger.models.DNSProblem;
import ncu.cc.digger.models.DNSProblemsEnum;
import ncu.cc.digger.models.dns.FlatResourceRecord;
import ncu.cc.digger.repositories.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

//@Component
public class FixIpv6InfoScheduler {
    private final Logger logger = LoggerFactory.getLogger(FixIpv6InfoScheduler.class);
    private final ReportRepository reportRepository;

    public FixIpv6InfoScheduler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    //    @Scheduled(cron = CronValues.EVERY_MINUTES)
    public void fixIPv6Problem() {
        this.reportRepository.findByIpv6AvailableIsNull()
                .forEach(reportEntity -> {
                    DNSHealthCheckResult healthCheckResult = HealthResultToEntity.reportToHealthResult(reportEntity);

                    boolean haveIPv6 = false;

                    Stream<FlatResourceRecord> recordStream = Stream.concat(
                            Stream.concat(healthCheckResult.getParents().stream(),
                                    healthCheckResult.getCommons().stream()),
                            healthCheckResult.getDaughters().stream());

                    if (recordStream.anyMatch(FlatResourceRecord::isIs_v6)) {
                        haveIPv6 = true;

                        if (healthCheckResult.getProblems() != null) {
                            haveIPv6 = healthCheckResult.getProblems().stream()
                                    .map(DNSProblem::getProblem)
                                    .noneMatch(dnsProblemsEnum -> dnsProblemsEnum.equals(DNSProblemsEnum.NO_WORKING_IPV6_NAME_SERVER));
                        }
                    }

                    logger.info("fix ipv6 status: {} -> {}", reportEntity.getZoneId(), haveIPv6);

                    reportEntity.setIpv6Available(ByteUtil.byteBoolean(haveIPv6));
                    reportRepository.save(reportEntity);
                });
    }
}
