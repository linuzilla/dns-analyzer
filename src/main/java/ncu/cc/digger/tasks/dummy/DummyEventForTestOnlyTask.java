package ncu.cc.digger.tasks.dummy;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.tasks.DiggerTask;
import ncu.cc.digger.tasks.DiggerTaskResult;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.springframework.stereotype.Component;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Component
public class DummyEventForTestOnlyTask implements DiggerTask {
    @Override
    public DiggerTaskResult runTask(Object padLoad) {
        StackTraceUtil.print1("This is only a test event ... sleep for a while");
        try {
            Thread.sleep(20_000);
        } catch (InterruptedException e) {
        }
        StackTraceUtil.print1("Testing dummy event had been done.");

        DiggerTaskResult result = new DiggerTaskResult(true, null);

        return result;
    }

    @Override
    public DiggerBackendWorker.OperationEventEnum workOnEvent() {
        return DiggerBackendWorker.OperationEventEnum.DUMMY_EVENT;
    }
}
