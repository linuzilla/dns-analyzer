package ncu.cc.digger.controllers;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.commons.webdev.annotations.MenuItem;
import ncu.cc.digger.constants.*;
import ncu.cc.digger.formbeans.QueryZoneForm;
import ncu.cc.digger.repositories.UserRepository;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.services.LocaleService;
import ncu.cc.digger.services.VersionService;
import ncu.cc.digger.sessionbeans.AnonymousUserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Locale;

@Controller
public class DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final VersionService versionService;
    private final LocaleService localeService;

    public DefaultController(UserRepository userRepository, AuthenticationService authenticationService, VersionService versionService, LocaleService localeService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.versionService = versionService;
        this.localeService = localeService;
    }

    @Resource(name = BeanIds.ANONYMOUS_USER_SESSION_BEAN)
    private AnonymousUserSession anonymousUserSession;

    @GetMapping(Routes.ROOT)
    public String index(Model model) {
//        logger.info("Locale: {}", localeService.currentLocale().toString());
        QueryZoneForm queryZoneForm = new QueryZoneForm();
        queryZoneForm.setZone(anonymousUserSession.getQueryZone());
        model.addAttribute(FormVariables.ZONE_QUERY_FORM, queryZoneForm);

        return Views.INDEX;
    }

    @GetMapping(Routes.ABOUT)
    public String about(Model model) {
        model.addAttribute("remoteAddress", authenticationService.getRemoteAddr());
        model.addAttribute("version", versionService.getVersion());
        return Views.ABOUT;
    }

    @GetMapping(Routes.EXPIRED)
    public String expired() {
        return Views.EXPIRED;
    }

    @GetMapping(Routes.LANG)
    public String lang() {
        Locale locale = localeService.currentLocale();
        logger.info(locale.toString());
        return Routes.redirect(Routes.ROOT);
    }

    @GetMapping(Routes.LOGOUT)
    public String logout() {
        authenticationService.logout();
        return Routes.redirect(Routes.ROOT);
    }

    @GetMapping(Routes.FAVICON)
    @ResponseBody
    public void returnNoFavicon() {
    }

    @GetMapping(Routes.ROBOTS)
    public String robots() {
        logger.warn("robots from {}", authenticationService.getRemoteAddr());
        return Views.ROBOTS;
    }

    @RequestMapping(value = Routes.ERROR403, method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE })
//    @ResponseBody
    public String error403() {
        StackTraceUtil.print1("403");
        return Views.E403;
    }
}
