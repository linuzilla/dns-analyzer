package ncu.cc.commons.webdev.interceptors;

import ncu.cc.commons.webdev.utils.MenuBuilder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private MenuBuilder menuBuilder;

    public RequestInterceptor(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        menuBuilder.handleRequest(request);
//
//        return super.preHandle(request, response, handler);
//    }
}
