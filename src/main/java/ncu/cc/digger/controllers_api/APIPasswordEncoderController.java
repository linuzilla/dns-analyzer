package ncu.cc.digger.controllers_api;

import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.controllers_api.utils.APIControllerUtil;
import ncu.cc.digger.models.APICommonResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.API_PATH + Routes.API_MISC)
public class APIPasswordEncoderController {
    private final PasswordEncoder passwordEncoder;

    public APIPasswordEncoderController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(Routes.API_MISC_ENCODE + "/{plain}")
    public APICommonResponse encode(@PathVariable("plain") String plain) {
        return APIControllerUtil.success(() -> passwordEncoder.encode(plain));
    }
}
