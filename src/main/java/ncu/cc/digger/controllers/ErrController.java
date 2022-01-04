package ncu.cc.digger.controllers;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.digger.constants.Routes;
import ncu.cc.digger.constants.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class ErrController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrController.class);

    @Value("${application.debug}")
    private boolean includeStackTrace;

    @Autowired
    private ErrorAttributes errorAttributes;

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

    @RequestMapping(value = Routes.ERROR, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String error(WebRequest request, HttpServletResponse response) {
        Map<String,Object> attributes = getErrorAttributes(request, includeStackTrace);

        var path = attributes.get("path").toString();
        var contextPath = request.getContextPath();
        var status = (Integer) attributes.get("status");

        logger.warn("Path: {}, Status {},  Error: {},  Message: {}",
                path,
                status,
                attributes.get("error").toString(),
                attributes.get("message").toString()
        );

        try {
            if (path.startsWith(contextPath + Routes.RESOURCES)) {
                response.sendError(HttpStatus.NOT_FOUND.value());
            } else if (path.startsWith(contextPath + Routes.API_PATH)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            } else if (status == HttpStatus.NOT_FOUND.value()) {
                response.sendRedirect(Routes.ROOT);
            } else {
                return Views.ERROR_DEFAULT;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Views.BLANK;
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }
}
