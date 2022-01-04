package ncu.cc.digger.controllers_api;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.controllers_api.utils.APIControllerUtil;
import ncu.cc.digger.models.APICommonResponse;
import ncu.cc.digger.services.EDNSComplianceTestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Routes.API_PATH + Routes.API_EDNS)
public class APIEDNSQueryController {
    public static class EDNSQueryRequest {
        private String zone;
        private String server;

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }
    }

    private final EDNSComplianceTestService ednsComplianceTestService;

    public APIEDNSQueryController(EDNSComplianceTestService ednsComplianceTestService) {
        this.ednsComplianceTestService = ednsComplianceTestService;
    }

    @PostMapping(value = Routes.API_EDNS_ANALYZE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<EDNSComplianceTestService.EDNSComplianceTestResult> ednsComplianceTest(@RequestBody EDNSQueryRequest request) {
        StackTraceUtil.print1(request);

        if (request.server != null && request.zone != null) {
            return ednsComplianceTestService.performEDNSTests(request.getZone(), request.getServer());
        } else {
            return Mono.empty();
        }
    }
}