package ncu.cc.digger.schedulers;

import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.CronValues;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleScanner {
    private final Logger logger = LoggerFactory.getLogger(ScheduleScanner.class);
    private final DiggerBackendWorker diggerBackendWorker;

    public ScheduleScanner(DiggerBackendWorker diggerBackendWorker) {
        this.diggerBackendWorker = diggerBackendWorker;
    }

    @Scheduled(cron = CronValues.EVERY_DAY)
    public void scan() {
        logger.info("The time is now {}", Constants.TIME_FORMAT.format(new Date()));
    }
}
