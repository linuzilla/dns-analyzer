package ncu.cc.digger.controllers.backends;

import ncu.cc.digger.constants.BeanIds;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.security.AppUserAuthentication;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.LocaleService;
import ncu.cc.digger.services.MenuService;
import ncu.cc.digger.sessionbeans.MenuBarSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(Routes.BACKEND)
public class MenuBackendController {
    private static final Logger logger = LoggerFactory.getLogger(MenuBackendController.class);
    private final AuthenticationService authenticationService;
    private final MenuService menuService;
    private final LocaleService localeService;

    @Resource(name = BeanIds.MENUBAR_SESSION_BEAN)
    private MenuBarSession menuBarSession;

    public MenuBackendController(AuthenticationService authenticationService, MenuService menuService, LocaleService localeService) {
        this.authenticationService = authenticationService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @GetMapping(Routes.BACKEND_MENU)
    public List<MenuBarSession.Entry> menuBackend() {
        var authentication = authenticationService.getAuthentication();

        Locale locale = localeService.currentLocale();

        if (authentication instanceof AppUserAuthentication) {
            var appUserAuthentication = (AppUserAuthentication) authentication;

            if (menuBarSession.getMenu() == null ||
                    appUserAuthentication.getCode() != menuBarSession.getCode()) {

                var builtMenu = menuService.buildMenu();

                menuBarSession.setMenu(builtMenu);
                menuBarSession.setLocale(locale);
                menuBarSession.setCode(appUserAuthentication.getCode());
                menuService.fillLocalizationMessage(menuBarSession.getMenu());
                logger.trace("rebuild menu for {}",
                        appUserAuthentication.getName() == null ? "anonymous" : appUserAuthentication.getName());
                return menuBarSession.getMenu();
            }
            return menuBarSession.getMenu();
        } else {
            if (menuBarSession.getMenu() == null) {
                var builtMenu = menuService.buildMenu();

                menuBarSession.setMenu(builtMenu);
                menuBarSession.setLocale(locale);
                menuBarSession.setCode(0L);
                menuService.fillLocalizationMessage(menuBarSession.getMenu());
                logger.trace("rebuild menu for {}", authenticationService.getAuthentication().getPrincipal());
                return menuBarSession.getMenu();
            }
            return menuBarSession.getMenu();
        }
    }
}
