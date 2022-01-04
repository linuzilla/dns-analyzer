package ncu.cc.digger.tasks;

import ncu.cc.digger.workers.DiggerBackendWorker;

public interface DiggerTask {
    DiggerTaskResult runTask(Object padLoad);
    DiggerBackendWorker.OperationEventEnum workOnEvent();
}
