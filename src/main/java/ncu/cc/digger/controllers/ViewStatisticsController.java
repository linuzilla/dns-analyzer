package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.models.statistics.*;
import ncu.cc.digger.models.user.CurrentScene;
import ncu.cc.digger.repositories.SceneReportViewRepository;
import ncu.cc.digger.security.AppUserAuthentication;
import ncu.cc.digger.security.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(Routes.VIEW + Routes.VIEW_BACKEND)
public class ViewStatisticsController {
    private final AuthenticationService authenticationService;
    private final SceneReportViewRepository sceneReportViewRepository;

    public ViewStatisticsController(AuthenticationService authenticationService, SceneReportViewRepository sceneReportViewRepository) {
        this.authenticationService = authenticationService;
        this.sceneReportViewRepository = sceneReportViewRepository;
    }

    private Optional<Integer> currentScene() {
        return authenticationService.getAppUserAuthentication()
                .map(AppUserAuthentication::getCurrentScene)
                .filter(Objects::nonNull)
                .map(CurrentScene::getSceneId);
    }

    @GetMapping(Routes.VIEW_BACKEND_SEVERITY)
    public List<SeverityLevelStatistics> findSeverityLevelStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findSeverityLevelStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_EDNS)
    public List<NonCompliantEdnsStatistics> findNonCompliantEdnsStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findNonCompliantEdnsStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_IPV6)
    public List<IPv6AvailableStatistics> findIPv6AvailableStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findIPv6AvailableStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_DNSSEC)
    public List<DnssecEnabledStatistics> findDnssecEnabledStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findDnssecEnabledStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_PROBLEMS)
    public List<NumberOfProblemsStatistics> findNumberOfProblemsStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findNumberOfProblemsStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_SERVERS)
    public List<NumberOfServersStatistics> findNumberOfServersStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findNumberOfServersStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_AXFR)
    public List<OpenAxfrStatistics> findOpenAxfrStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findOpenAxfrStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_RECURSIVE)
    public List<OpenRecursiveStatistics> findOpenRecursiveStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findOpenRecursiveStatistics)
                .orElse(Collections.emptyList());
    }

    @GetMapping(Routes.VIEW_BACKEND_RRSET)
    public List<RrsetInconsistencyStatistics> findRrsetInconsistencyStatistics() {
        return currentScene()
                .map(sceneReportViewRepository::findRrsetInconsistencyStatistics)
                .orElse(Collections.emptyList());
    }
}
