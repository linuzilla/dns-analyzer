package ncu.cc.commons.webdev.utils;

import ncu.cc.commons.webdev.models.MenuEntry;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface MenuBuilder {
//    void handleRequest(HttpServletRequest request);
    MenuEntry getRootMenuEntry();
}
