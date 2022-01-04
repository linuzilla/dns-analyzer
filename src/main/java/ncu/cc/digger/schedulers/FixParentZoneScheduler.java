package ncu.cc.digger.schedulers;

import ncu.cc.digger.constants.CronValues;
import ncu.cc.digger.repositories.ReportRepository;
import ncu.cc.digger.utils.DNSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class FixParentZoneScheduler {
    private final Logger logger = LoggerFactory.getLogger(FixParentZoneScheduler.class);
    private final ReportRepository reportRepository;

    public FixParentZoneScheduler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

//        @Scheduled(cron = CronValues.EVERY_MINUTES)
    public void service() {
        this.reportRepository.findByParentZoneIsNull()
                .forEach(reportEntity -> {
                    reportEntity.setParentZone(DNSUtil.parentZone(reportEntity.getZoneId()));
                    reportRepository.save(reportEntity);

                    logger.info("{} -> {}", reportEntity.getZoneId(), reportEntity.getParentZone());
                });
    }
}
