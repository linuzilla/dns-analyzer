package ncu.cc.digger.controllers;

import ncu.cc.digger.constants.Constants;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Controller
@SessionAttributes(Constants.FLASH_MESSAGE)
@RequestMapping(Routes.FLASH)
public class FlashMessageController {
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(@ModelAttribute(Constants.FLASH_MESSAGE) final String value) {
        return Views.FLASH;
    }
}
