package ncu.cc.digger.controllers;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Controller
public class AppErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

    @Value(ValueConstants.APPLICATION_DEBUG)
    private boolean includeStackTrace;

//    @Autowired
//    private ErrorAttributes errorAttributes;

    @ExceptionHandler(Exception.class)
    public String index(Exception e) {
        StackTraceUtil.print1(e.getClass().getName());
        e.printStackTrace();
        return Views.ERROR_403;
    }

    @Override
    public String getErrorPath() {
        return Routes.ERROR;
    }

//    @RequestMapping(value = Routes.ERROR) //, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String error(WebRequest request, HttpServletResponse response) {
//        Map<String,Object> attributes = getErrorAttributes(request, includeStackTrace);
//
////        if (request instanceof ServletWebRequest) {
////            HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
////
////            logger.info(httpServletRequest.getRequestURI());
////        }
//
//        String path = attributes.get("path").toString();
//        String status = attributes.get("status").toString();
//        String error = attributes.get("error").toString();
//
////        attributes.forEach((k,v) -> {
////            logger.info("{}: {} -> {}", path, k, v.toString());
////        });
//
////        logger.warn(attributes.get("message").toString());
//
//        logger.warn("status: {}, path: {}, error: {}", status, path, error);
//
//        if (path.startsWith(request.getContextPath() + Routes.API_PATH)) {
//            try {
//                response.sendError(HttpStatus.UNAUTHORIZED.value());
//            } catch (IOException e) {
//            }
//        }
//
//        return Views.ERROR_DEFAULT;
//    }
//
//    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
//        return errorAttributes.getErrorAttributes(request, includeStackTrace);
//    }
}
