package ncu.cc.digger.services;

import ncu.cc.digger.sessionbeans.MenuBarSession;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface MenuService {
    List<MenuBarSession.Entry> buildMenu();

    void fillLocalizationMessage(List<MenuBarSession.Entry> menu);
}
