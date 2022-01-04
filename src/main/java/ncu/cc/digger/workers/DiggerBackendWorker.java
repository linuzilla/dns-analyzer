package ncu.cc.digger.workers;

import ncu.cc.digger.tasks.DiggerTaskResult;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface DiggerBackendWorker {
    enum OperationEventEnum {
        END_OF_THE_RUN,
        DUMMY_EVENT,
        DNS_CHECK_ZONE,
        RANK_CREATION;

        public static final List<String> ALL_EVENTS;

        static {
            List<String> list = new ArrayList<>();
            for (OperationEventEnum event: values()) {
                if (event != END_OF_THE_RUN) {
                    list.add(event.name());
                }
            }
            ALL_EVENTS = Collections.unmodifiableList(list);
        }

        public static OperationEventEnum byValue(String value) {
            for (OperationEventEnum eventEnum: values()) {
                if (value.equalsIgnoreCase(eventEnum.name())) {
                    return eventEnum;
                }
            }
            return null;
        }
    }

    enum WorkingExceptionEnum {
        DUPLICATE, FATAL_EXCEPTION
    }

    class WorkingException extends Exception {
        private final WorkingExceptionEnum reason;

        WorkingException(WorkingExceptionEnum reason, String message) {
            super(message);
            this.reason = reason;
        }

        public WorkingExceptionEnum getReason() {
            return reason;
        }
    }

    enum  WorkerStatusEnum {
        SLEEP, WORKING
    }

    class WorkerStatusResult {
        private final OperationEventEnum event;
        private final WorkerStatusEnum status;

        WorkerStatusResult(OperationEventEnum event, WorkerStatusEnum status) {
            this.event = event;
            this.status = status;
        }

        public OperationEventEnum getEvent() {
            return event;
        }

        public WorkerStatusEnum getStatus() {
            return status;
        }
    }

    void triggerEvent(OperationEventEnum event, Object padLoad, BiConsumer<DiggerTaskResult,Exception> collector);
    void triggerEvent(OperationEventEnum event, Object padLoad);

    Mono<DiggerTaskResult> reactiveTriggerEvent(OperationEventEnum event, Object padLoad);
    WorkerStatusResult getWorkerStatus();
    int getNumberOfEvents();
}
