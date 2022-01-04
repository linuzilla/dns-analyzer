package ncu.cc.digger.controllers_api;

import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.controllers_api.utils.APIControllerUtil;
import ncu.cc.digger.models.APICommonResponse;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.QueryQueueService;
import ncu.cc.digger.services.ZoneQueryWithCacheService;
import ncu.cc.digger.utils.DNSUtil;
import ncu.cc.digger.workers.DiggerBackendWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.API_PATH)
public class APIZoneQueryController {
    private static final Logger logger = LoggerFactory.getLogger(APIZoneQueryController.class);

    private final DiggerBackendWorker diggerBackendWorker;
    private final ZoneQueryWithCacheService zoneQueryWithCacheService;
    private final AuthenticationService authenticationService;
    private final QueryQueueService queryQueueService;

    public APIZoneQueryController(DiggerBackendWorker diggerBackendWorker, ZoneQueryWithCacheService zoneQueryWithCacheService, AuthenticationService authenticationService, QueryQueueService queryQueueService) {
        this.diggerBackendWorker = diggerBackendWorker;
        this.zoneQueryWithCacheService = zoneQueryWithCacheService;
        this.authenticationService = authenticationService;
        this.queryQueueService = queryQueueService;
    }

    @PostMapping(value = Routes.API_PUSH + "/" + Constants.ZONE_PATH_VARIABLE + "/zone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APICommonResponse push(@PathVariable("zone") String zone) {
        return APIControllerUtil.success(() -> queryQueueService.addQueue(DNSUtil.zoneNameNormalize(zone)));
    }

    @PostMapping(value = Routes.API_QUERY + "/" + Constants.ZONE_PATH_VARIABLE + "/zone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APICommonResponse query(@PathVariable("zone") String zone) {
        final String normalizedZone = DNSUtil.zoneNameNormalize(zone);

        int numberOfEvents = diggerBackendWorker.getNumberOfEvents();

        APICommonResponse apiCommonResponse = new APICommonResponse();
        apiCommonResponse.setStatus(APICommonResponse.Status.failed);

        if (numberOfEvents == 0) {
            zoneQueryWithCacheService
                    .findZone(normalizedZone, (s, zoneRetrieveState) -> true)
                    .ifPresentOrElse(zoneInDatabase -> zoneInDatabase
                            .ifAlreadyInDatabase(reportEntity -> apiCommonResponse.setData(reportEntity))
                            .ifNotInDatabase(zoneBeforeAnalysis -> {
                                        String belongingZone = zoneBeforeAnalysis.belongingZoneName();

                                        if (StringUtil.isNotEmpty(belongingZone)) {
                                            logger.info("trigger zone analyze: {}", belongingZone);
                                            zoneBeforeAnalysis.triggerAnalyzer(authenticationService.getRemoteAddr(), false);
                                            apiCommonResponse.setData("trigger query: " + belongingZone);
                                            apiCommonResponse.setStatus(APICommonResponse.Status.success);
                                        } else {
                                            apiCommonResponse.setData("Invalid zone: " + normalizedZone);
                                        }
                                    }
                            ), () -> apiCommonResponse.setData("Oops: " + normalizedZone));
        } else {
            apiCommonResponse.setData("events " + numberOfEvents);
        }
        return apiCommonResponse;
    }
}
