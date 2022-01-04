package ncu.cc.digger.controllers.base;

import ncu.cc.digger.constants.MessageKeys;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.security.AuthenticationService;
import ncu.cc.digger.security.RecaptchaService;
import ncu.cc.digger.services.MessageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public abstract class WithCaptcha {
    @Value(ValueConstants.GOOGLE_RECAPTCHA_KEY_SITE)
    protected String siteKey;
    @Value(ValueConstants.GOOGLE_RECPATCHA_KEY_SECRET)
    protected String secretKey;
    @Value(ValueConstants.GOOGLE_RECPATCHA_API_SCRIPT)
    protected String apiScript;
    @Autowired
    protected AuthenticationService authenticationService;
    @Autowired
    protected RecaptchaService recaptchaService;
    @Autowired
    protected MessageResourceService messageResourceService;

    protected void addRecaptchaVariable(Model model) {
        model.addAttribute("siteKey", siteKey);
        model.addAttribute("apiScript", apiScript);
    }

    protected  Mono<Boolean> verifyCaptcha(final String recaptchaResponse, String remoteAddress) {
        if (recaptchaResponse == null || "".equals(recaptchaResponse)) {
            return Mono.just(false);
        } else {
            return recaptchaService.validateResponseForID(recaptchaResponse, remoteAddress)
                    .map(recaptchaVerificationResultEnum -> {
                        switch (recaptchaVerificationResultEnum) {
                            case SUCCESSFUL:
                                return true;
                            case FAILED:
                            case ERROR:
                            default:
                                return false;
                        }
                    });
        }
    }

    protected void bindingWithCaptcha(BindingResult bindingResult, String recaptchaResponse, String remoteAddress) {
        if (! verifyCaptcha(recaptchaResponse, remoteAddress).block()) {
            bindingResult.rejectValue("recaptchaResponse", "error.signup",
                    messageResourceService.getMessage(MessageKeys.SIGNUP_FORM_ERROR_CAPTHA));
        }
    }
}
