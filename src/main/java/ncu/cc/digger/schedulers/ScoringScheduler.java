package ncu.cc.digger.schedulers;

import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.CronValues;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScoringScheduler {
    private final Logger logger = LoggerFactory.getLogger(ScheduleScanner.class);
    private final DiggerBackendWorker diggerBackendWorker;
    private final boolean enableMonthlyScoring;

    public ScoringScheduler(
            DiggerBackendWorker diggerBackendWorker,
            @Value(ValueConstants.APPLICATION_MONTHLY_SCORING) boolean enableMonthlyScoring) {
        this.diggerBackendWorker = diggerBackendWorker;
        this.enableMonthlyScoring = enableMonthlyScoring;
    }

    @Scheduled(cron = CronValues.EVERY_MONTH)
    public void startScoring() {
        if (enableMonthlyScoring) {
            diggerBackendWorker.triggerEvent(DiggerBackendWorker.OperationEventEnum.RANK_CREATION, null);
        } else {
            logger.info("Current time: {}, monthly scoring not enabled", Constants.DATETIME_FORMAT.format(new Date()));
        }
    }
}
