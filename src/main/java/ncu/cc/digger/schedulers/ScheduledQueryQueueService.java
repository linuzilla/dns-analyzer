package ncu.cc.digger.schedulers;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.CronValues;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.services.QueryQueueService;
import ncu.cc.digger.services.ZoneQueryWithCacheService;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScheduledQueryQueueService {
    private static final Pattern PATTERN = Pattern.compile("^\\*/(\\d+)$");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final String DISABLE = "disable";

    private static final Logger logger = LoggerFactory.getLogger(ScheduledQueryQueueService.class);
    private final QueryQueueService queryQueueService;
    private final ZoneQueryWithCacheService zoneQueryWithCacheService;
    private final DiggerBackendWorker diggerBackendWorker;
    private final Lock lock = new ReentrantLock();
    private final AtomicLong counter = new AtomicLong(0L);
    private long ratio;

    public ScheduledQueryQueueService(
            QueryQueueService queryQueueService,
            ZoneQueryWithCacheService zoneQueryWithCacheService,
            DiggerBackendWorker diggerBackendWorker,
            @Value(ValueConstants.QUEYE_WORKER) String queueWorker) {
        this.queryQueueService = queryQueueService;
        this.zoneQueryWithCacheService = zoneQueryWithCacheService;
        this.diggerBackendWorker = diggerBackendWorker;

        if (DISABLE.equals(queueWorker)) {
            this.ratio = 0L;
        } else if ("*".equals(queueWorker)) {
            this.ratio = 1L;
        } else {
            Matcher matcher = PATTERN.matcher(queueWorker);
            if (matcher.matches()) {
                this.ratio = Long.parseLong(matcher.group(1));

                logger.info("Ratio: {}", this.ratio);
            } else {
                throw new RuntimeException(
                        String.format("%s: format error (should be: disable or *, */2, */3 ...", queueWorker));
            }
        }
    }

    @Scheduled(cron = CronValues.EVERY_MINUTES)
//    @Scheduled(cron = CronValues.EVERY_10_SECONDS)
    public void service() {
        if (this.ratio == 0L || counter.incrementAndGet() % this.ratio != 0L) {
            return;
        }

        if (diggerBackendWorker.getNumberOfEvents() == 0) {
            if (lock.tryLock()) {
                try {
                    var result = new AtomicReference<>(QueryQueueService.QueueServiceResult.UNKNOWN);

                    this.queryQueueService.retrieveAndServe(zone ->
                            this.zoneQueryWithCacheService.findZone(zone, (s, zoneRetrieveState) -> true)
                                    .map(zoneInDatabase -> zoneInDatabase
                                            .ifAlreadyInDatabase(reportEntity ->
                                                    result.set(QueryQueueService.QueueServiceResult.SERVED_BEFORE))
                                            .ifNotInDatabase(zoneBeforeAnalysis -> {
                                                String belongingZone = zoneBeforeAnalysis.belongingZoneName();

                                                if (StringUtil.isNotEmpty(belongingZone)) {
                                                    logger.info("trigger zone analyze: {}", belongingZone);
                                                    zoneBeforeAnalysis.triggerAnalyzer(this.getClass().getName(), false);
                                                    result.set(QueryQueueService.QueueServiceResult.SERVED);
                                                } else {
                                                    result.set(QueryQueueService.QueueServiceResult.NOT_FOUND);
                                                }
                                            }).map(z -> result.get())
                                    ).orElse(QueryQueueService.QueueServiceResult.FORMAT_ERROR)
                    );
                } finally {
                    lock.unlock();
                }
            } else {
                logger.info("The time is now {}", DATE_FORMAT.format(new Date()));
                logger.warn("another process on going ... skip this time");
            }
        }
    }
}
