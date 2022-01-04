package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import org.apache.maven.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Routes.SIGNUP)
public class SignUpController {
    @GetMapping
    public String signup(Model model) {
        return Views.SIGNUP;
    }
}
