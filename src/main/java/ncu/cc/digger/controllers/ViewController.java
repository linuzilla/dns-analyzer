package ncu.cc.digger.controllers;

import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.entities.SceneReportViewEntity;
import ncu.cc.digger.entities.SceneUserViewEntity;
import ncu.cc.digger.entities.SceneUserViewEntityPK;
import ncu.cc.digger.models.user.CurrentScene;
import ncu.cc.digger.repositories.SceneReportViewRepository;
import ncu.cc.digger.repositories.SceneUserViewRepository;
import ncu.cc.digger.security.AppUserAuthentication;
import ncu.cc.digger.security.AuthenticationService;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(Routes.VIEW)
public class ViewController {
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    private final AuthenticationService authenticationService;
    private final SceneUserViewRepository sceneUserViewRepository;
    private final SceneReportViewRepository sceneReportViewRepository;

    public ViewController(AuthenticationService authenticationService, SceneUserViewRepository sceneUserViewRepository, SceneReportViewRepository sceneReportViewRepository) {
        this.authenticationService = authenticationService;
        this.sceneUserViewRepository = sceneUserViewRepository;
        this.sceneReportViewRepository = sceneReportViewRepository;
    }

    @GetMapping
    @MenuItem(value = MenuPaths.VIEW, order = MenuOrders.VIEW, authorities = { RolesEnum.VIEW })
    public String home() {
        return Routes.redirect(Routes.MANAGE, Routes.VIEW_INDEX);
    }

    @GetMapping(Routes.VIEW_INDEX)
    @MenuItem(value = MenuPaths.VIEW_SELECT, order = 1, authorities = { RolesEnum.MULTIVIEW })
    public String index(Model model) {
        return authenticationService.getUserDetails()
                .map(appUserDetails -> {
                    var scenes = this.sceneUserViewRepository.findByUserId(appUserDetails.getUid());

                    if (scenes.size() == 0) {
                        return Views.VIEW_NA;
                    } else {
                        model.addAttribute(FormVariables.SCENES, scenes);

//                        StackTraceUtil.print1(scenes);
                        return Views.VIEW_INDEX;
                    }
                }).orElse(Views.VIEW_NA);
    }

    @GetMapping(Routes.VIEW_SELECT + "/{scene}")
    public String selectScene(@PathVariable("scene") Integer scene) {
        if (authenticationService.getAppUserAuthentication()
                .map(appUserAuthentication -> {
                    SceneUserViewEntityPK pk = new SceneUserViewEntityPK();
                    pk.setUserId(appUserAuthentication.getAppUser().getUid());
                    pk.setSceneId(scene);

                    Optional<SceneUserViewEntity> sceneUserViewEntity = this.sceneUserViewRepository.findById(pk);

                    if (sceneUserViewEntity.isPresent()) {
                        CurrentScene currentScene = new CurrentScene(
                                sceneUserViewEntity.get().getSceneId(),
                                sceneUserViewEntity.get().getSceneName(),
                                sceneUserViewEntity.get().getRole()
                        );
                        logger.info("appUser: {}, scene: {}", appUserAuthentication.getName(), currentScene.getName());
//                        StackTraceUtil.print1(currentScene);
                        appUserAuthentication.setCurrentScene(currentScene);
                        return true;
                    }
                    return false;
                }).orElse(false)) {
            return Routes.redirect(Routes.VIEW, Routes.VIEW_BROWSE);
        } else {
//            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE,
//                    "Failed to set Management Zones");
            return Routes.redirect(Routes.VIEW);
        }
    }

//    private CurrentScene getCurrentScene() {
//        return authenticationService.getAppUserAuthentication()
//                .map(AppUserAuthentication::getCurrentScene)
//                .orElse(null);
//    }

    @GetMapping(Routes.VIEW_BROWSE)
    @MenuItem(value = MenuPaths.VIEW_BROWSE, order = 2, authorities = { RolesEnum.VIEW })
    public String browseScene() {
        return authenticationService.getAppUserAuthentication()
                .map(AppUserAuthentication::getCurrentScene)
                .filter(Objects::nonNull)
                .map(currentScene -> Views.VIEW_BROWSE)
                .orElse(Routes.redirect(Routes.VIEW_SELECT));
    }

    @GetMapping(Routes.VIEW_STATISTICS)
    @MenuItem(value = MenuPaths.VIEW_STATISTICS, order = 3, authorities = { RolesEnum.VIEW })
    public String showStatistics(Model model) {
        return authenticationService.getAppUserAuthentication()
                .map(AppUserAuthentication::getCurrentScene)
                .filter(Objects::nonNull)
                .map(currentScene -> {
                    model.addAttribute(FormVariables.SCENE_TITLE, currentScene.getName());
                    return Views.VIEW_STATISTICS;
                })
                .orElse(Routes.redirect(Routes.VIEW_SELECT));
    }

    @GetMapping(Routes.VIEW_BACKEND + Routes.VIEW_BACKEND_BROWSE + "/{pageNo}")
    @ResponseBody
    public Page<SceneReportViewEntity> browsePage(@PathVariable("pageNo") Integer pageNo) {
        return authenticationService.getAppUserAuthentication()
                .map(AppUserAuthentication::getCurrentScene)
                .map(CurrentScene::getSceneId)
                .map(sceneId -> {
                    PageRequest pageable = PageRequest.of(pageNo, 100);
                    return sceneReportViewRepository.findBySceneIdOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAscZoneIdAsc(sceneId, pageable);
                })
                .orElse(Page.empty());
    }
}
