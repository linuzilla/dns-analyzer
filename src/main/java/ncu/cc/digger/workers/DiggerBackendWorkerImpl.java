package ncu.cc.digger.workers;

import ncu.cc.digger.tasks.DiggerTask;
import ncu.cc.digger.tasks.DiggerTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class DiggerBackendWorkerImpl implements DiggerBackendWorker {
    private static final Logger logger = LoggerFactory.getLogger(DiggerBackendWorkerImpl.class);
    private static final String WORKER_NAME = "Digger Worker";
    private static final boolean ALLOW_DUPLICATE_EVENT = true;

    private final ApplicationContext applicationContext;

    private static class EventBundle {
        private final OperationEventEnum event;
        private final Object padLoad;
        private final BiConsumer<DiggerTaskResult, Exception> collector;

        private EventBundle(OperationEventEnum event, Object padLoad, BiConsumer<DiggerTaskResult, Exception> collector) {
            this.event = event;
            this.padLoad = padLoad;
            this.collector = collector;
        }

        private OperationEventEnum getEvent() {
            return event;
        }

        private Object getPadLoad() {
            return padLoad;
        }

        private void accept(DiggerTaskResult diggerTaskResult, Exception e) {
            this.collector.accept(diggerTaskResult, e);
        }
    }

    public DiggerBackendWorkerImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private final Worker worker = new Worker();
    private final Deque<EventBundle> eventQueue = new ConcurrentLinkedDeque<>();
    private final Map<OperationEventEnum, DiggerTask> operationTaskMap = new HashMap<>();

    class Worker implements Runnable {
        private final Object mutex = new Object();
        private volatile boolean done = false;
        private WorkerStatusEnum workerStatus = WorkerStatusEnum.SLEEP;
        private OperationEventEnum currentWorkingEvent = OperationEventEnum.END_OF_THE_RUN;

        private void serviceEvent() {
            EventBundle bundle;
            Set<OperationEventEnum> eventSet = new HashSet<>();
            int counter = 0;

            logger.info("[{}] waked up and servicing events", WORKER_NAME);

            while ((bundle = eventQueue.poll()) != null) {
                currentWorkingEvent = bundle.getEvent();

                if (bundle.getEvent() == OperationEventEnum.END_OF_THE_RUN) {
                    break;
                } else if (!ALLOW_DUPLICATE_EVENT && eventSet.contains(bundle.getEvent())) {
                    logger.info("[{}] duplicated event {} ... let it go this moment", WORKER_NAME, bundle.getEvent().name());
                    bundle.accept(null,
                            new WorkingException(WorkingExceptionEnum.DUPLICATE, "duplicated event"));
                } else {
                    eventSet.add(bundle.getEvent());
                    DiggerTask diggerTask = operationTaskMap.get(bundle.getEvent());

                    if (diggerTask != null) {
                        try {
                            counter++;
                            logger.info("[{}] perform {} event", WORKER_NAME, bundle.getEvent().name());
                            DiggerTaskResult result = diggerTask.runTask(bundle.getPadLoad());
                            bundle.accept(result, null);
                            logger.info("[{}] {} event done, find next event", WORKER_NAME, bundle.getEvent().name());
                        } catch (Exception e) {
                            logger.error(e.getClass().getName());
                            bundle.accept(null, e);
                            e.printStackTrace();
                        }
                    } else {
                        bundle.accept(
                                null,
                                new WorkingException(WorkingExceptionEnum.FATAL_EXCEPTION, "Event without a handling function")
                        );
                    }
                }
            }
            logger.info("[{}] {} event(s) had been serviced", WORKER_NAME, counter);
        }

        @Override
        public void run() {
            logger.info("[{}] worker is ready to service", WORKER_NAME);

            while (!done) {
                try {
                    synchronized (mutex) {
                        logger.info("[{}] entering wait state", WORKER_NAME);
                        workerStatus = WorkerStatusEnum.SLEEP;
                        mutex.wait();
                        logger.info("[{}] leaving wait state", WORKER_NAME);
                    }

                    while (eventQueue.size() > 0) {
                        eventQueue.add(new EventBundle(OperationEventEnum.END_OF_THE_RUN, null, null));
                        workerStatus = WorkerStatusEnum.WORKING;
                        serviceEvent();
                    }
                } catch (Exception e) {
                    logger.warn("[{}] Exception: {}", WORKER_NAME, e.getMessage());
                }
            }
        }

        private void wakeup() {
            synchronized (mutex) {
                mutex.notify();
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        applicationContext.getBeansOfType(DiggerTask.class).forEach((s, accountOperationTask) -> {
            operationTaskMap.put(accountOperationTask.workOnEvent(), accountOperationTask);
        });

        new Thread(worker).start();
    }

    @PreDestroy
    public void destroy() {
        worker.done = true;
    }

    private void defaultCollector(DiggerTaskResult diggerTaskResult, Exception e) {
        if (diggerTaskResult != null) {
            logger.info("collector: event success = {}", diggerTaskResult.isSuccess());
        }
        if (e != null) {
            logger.warn("collector: event exception: {}", e.getMessage());
        }
    }

    @Override
    public void triggerEvent(OperationEventEnum event, Object padLoad) {
        triggerEvent(event, padLoad, this::defaultCollector);
    }

    @Override
    public void triggerEvent(OperationEventEnum event, Object padLoad, BiConsumer<DiggerTaskResult, Exception> collector) {
        if (event != OperationEventEnum.END_OF_THE_RUN && operationTaskMap.containsKey(event)) {
            eventQueue.add(new EventBundle(event, padLoad, collector));
            worker.wakeup();
        }
    }

    @Override
    public Mono<DiggerTaskResult> reactiveTriggerEvent(OperationEventEnum event, Object padLoad) {
        MonoProcessor<DiggerTaskResult> stream = MonoProcessor.create();

        this.triggerEvent(event, padLoad, (diggerTaskResult, e) -> {
            if (diggerTaskResult != null) {
                logger.info("reactiveTriggerEvent: event success = {}", diggerTaskResult.isSuccess());
                stream.onNext(diggerTaskResult);
                logger.info("reactiveTriggerEvent: onNext");
            } else {
                logger.warn("reactiveTriggerEvent: event exception: {}", e.getMessage());
            }
            stream.onComplete();
        });
        return stream;
    }

    @Override
    public WorkerStatusResult getWorkerStatus() {
        return new WorkerStatusResult(worker.currentWorkingEvent, worker.workerStatus);
    }

    @Override
    public int getNumberOfEvents() {
        return this.eventQueue.size();
    }
}
