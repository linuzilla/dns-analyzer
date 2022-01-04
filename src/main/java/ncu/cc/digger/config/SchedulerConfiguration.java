package ncu.cc.digger.config;

import ncu.cc.digger.constants.BeanIds;
import ncu.cc.digger.constants.ValueConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SchedulerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfiguration.class);

    private final Integer numberOfScheduler;

    public SchedulerConfiguration(@Value(ValueConstants.SCHEDULER_SIZE) Integer numberOfScheduler) {
        logger.info("Number of Scheduler: {}", numberOfScheduler);
        this.numberOfScheduler = numberOfScheduler;
    }

    @Bean(name = BeanIds.SCHEDULER)
    public Scheduler reactiveScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(numberOfScheduler));
    }
}
