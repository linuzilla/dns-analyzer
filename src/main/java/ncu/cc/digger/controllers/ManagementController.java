package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Routes.MANAGE)
public class ManagementController {
//    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);
//
//    private final AuthenticationService authenticationService;
//    private final SceneUserViewRepository sceneUserViewRepository;
//    private final SceneReportViewRepository sceneReportViewRepository;
//
//    public ManagementController(AuthenticationService authenticationService, SceneUserViewRepository sceneUserViewRepository, SceneReportViewRepository sceneReportViewRepository) {
//        this.authenticationService = authenticationService;
//        this.sceneUserViewRepository = sceneUserViewRepository;
//        this.sceneReportViewRepository = sceneReportViewRepository;
//    }
//
//    @GetMapping
//    @MenuItem(value = MenuPaths.VIEW, order = MenuOrders.VIEW, authorities = { RolesEnum.SYSOP })
//    public String home() {
//        return Routes.redirect(Routes.MANAGE, Routes.VIEW_INDEX);
//    }
//
//    @GetMapping(Routes.VIEW_INDEX)
//    @MenuItem(value = MenuPaths.MANAGEMENT_SELECT, order = 100, authorities = { RolesEnum.SYSOP })
//    public String index(Model model) {
//        return authenticationService.getUserDetails()
//                .map(appUserDetails -> {
//                    var scenes = this.sceneUserViewRepository.findByUserId(appUserDetails.getUid());
//
//                    if (scenes.size() == 0) {
//                        return Views.VIEW_NA;
//                    } else {
//                        model.addAttribute(FormVariables.SCENES, scenes);
//
////                        StackTraceUtil.print1(scenes);
//                        return Views.VIEW_INDEX;
//                    }
//                }).orElse(Views.VIEW_NA);
//    }
//
//    @GetMapping(Routes.MANAGE_SELECT + "/{scene}")
//    public String selectScene(@PathVariable("scene") Integer scene) {
//        logger.info("Select Scene: {}", scene);
//
//        if (authenticationService.getAppUserAuthentication()
//                .map(appUserAuthentication -> {
//                    logger.info("appUser: {}", appUserAuthentication.getName());
//                    SceneUserViewEntityPK pk = new SceneUserViewEntityPK();
//                    pk.setUserId(appUserAuthentication.getAppUser().getUid());
//                    pk.setSceneId(scene);
//
//                    Optional<SceneUserViewEntity> sceneUserViewEntity = this.sceneUserViewRepository.findById(pk);
//
//                    if (sceneUserViewEntity.isPresent()) {
//                        CurrentScene currentScene = new CurrentScene(
//                                sceneUserViewEntity.get().getSceneId(),
//                                sceneUserViewEntity.get().getSceneName(),
//                                sceneUserViewEntity.get().getRole()
//                        );
//                        StackTraceUtil.print1(currentScene);
//                        appUserAuthentication.setCurrentScene(currentScene);
//                        return true;
//                    }
//                    return false;
//                }).orElse(false)) {
//            return Routes.redirect(Routes.MANAGE, Routes.VIEW_BROWSE);
//        } else {
////            redirectAttributes.addFlashAttribute(Constants.FLASH_MESSAGE,
////                    "Failed to set Management Zones");
//            return Routes.redirect(Routes.MANAGE);
//        }
//    }
//
////    private CurrentScene getCurrentScene() {
////        return authenticationService.getAppUserAuthentication()
////                .map(AppUserAuthentication::getCurrentScene)
////                .orElse(null);
////    }
//
//    @GetMapping(Routes.VIEW_BROWSE)
//    @MenuItem(value = MenuPaths.MANAGEMENT_BROWSE, order = 200, authorities = { RolesEnum.SYSOP })
//    public String browseScene() {
//        return authenticationService.getAppUserAuthentication()
//                .map(AppUserAuthentication::getCurrentScene)
//                .filter(Objects::nonNull)
//                .map(currentScene -> Views.VIEW_BROWSE)
//                .orElse(Routes.redirect(Routes.MANAGE));
//    }
//
//    @GetMapping(Routes.MANAGE_BACKEND + Routes.MANAGE_BACKEND_BROWSE + "/{pageNo}")
//    @ResponseBody
//    public Page<SceneReportViewEntity> browsePage(@PathVariable("pageNo") Integer pageNo) {
//        return authenticationService.getAppUserAuthentication()
//                .map(AppUserAuthentication::getCurrentScene)
//                .map(CurrentScene::getSceneId)
//                .map(sceneId -> {
//                    PageRequest pageable = PageRequest.of(pageNo, 200);
//                    return sceneReportViewRepository.findBySceneIdOrderBySeverityUrgentAscSeverityHighAscSeverityMediumAscSeverityLowAscSeverityInfoAsc(sceneId, pageable);
//                })
//                .orElse(Page.empty());
//    }
}
