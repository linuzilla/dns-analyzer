package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import ncu.cc.digger.controllers.base.WithCaptcha;
import ncu.cc.digger.formbeans.SignInForm;
import ncu.cc.digger.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Routes.LOGIN)
public class LoginController extends WithCaptcha {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//    private final AuthenticationService authenticationService;
//
//    public LoginController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

    @GetMapping
    public String login(Model model) {
        addRecaptchaVariable(model);
        model.addAttribute("signin", new SignInForm());
        model.addAttribute("portal", "portal");
        model.addAttribute("sender", "sender");

//        logger.info("login from {}", authenticationService.getRemoteAddr());

        return Views.LOGIN;
    }
}
