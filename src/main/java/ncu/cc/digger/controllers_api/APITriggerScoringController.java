package ncu.cc.digger.controllers_api;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.controllers_api.utils.APIControllerUtil;
import ncu.cc.digger.models.APICommonResponse;
import ncu.cc.digger.schedulers.ScheduleScanner;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.API_PATH)
public class APITriggerScoringController {
    private final Logger logger = LoggerFactory.getLogger(ScheduleScanner.class);
    private final DiggerBackendWorker diggerBackendWorker;

    public APITriggerScoringController(DiggerBackendWorker diggerBackendWorker) {
        this.diggerBackendWorker = diggerBackendWorker;
    }

    @PostMapping(Routes.API_SCORING)
    public APICommonResponse triggerScoring() {
        return APIControllerUtil.success(() -> {
            diggerBackendWorker.triggerEvent(DiggerBackendWorker.OperationEventEnum.RANK_CREATION, null);
            return null;
        });
    }
}
