package ncu.cc.digger.controllers_api;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.models.APICommonResponse;
import ncu.cc.digger.services.ReactiveCommandExecutor;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.API_PATH + Routes.API_STATUS)
public class APIQueryStatusController {
    private final ReactiveCommandExecutor reactiveCommandExecutor;
    private final DiggerBackendWorker diggerBackendWorker;

    public APIQueryStatusController(ReactiveCommandExecutor reactiveCommandExecutor, DiggerBackendWorker diggerBackendWorker) {
        this.reactiveCommandExecutor = reactiveCommandExecutor;
        this.diggerBackendWorker = diggerBackendWorker;
    }

    @GetMapping(Routes.API_STATUS_THREADS)
    public APICommonResponse threads() {
        APICommonResponse apiCommonResponse = new APICommonResponse();

        apiCommonResponse.setStatus(APICommonResponse.Status.success);
        apiCommonResponse.setData(this.reactiveCommandExecutor.dumpProcesses().values());

        return apiCommonResponse;
    }

    @GetMapping(Routes.API_STATUS_EVENTS)
    public APICommonResponse queue() {
        APICommonResponse apiCommonResponse = new APICommonResponse();

        apiCommonResponse.setStatus(APICommonResponse.Status.success);
        apiCommonResponse.setData(this.diggerBackendWorker.getNumberOfEvents());

        return apiCommonResponse;
    }
}
