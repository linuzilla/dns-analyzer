package ncu.cc.digger.config;

import ncu.cc.digger.constants.BeanIds;
import ncu.cc.digger.sessionbeans.AnonymousUserSession;
import ncu.cc.digger.sessionbeans.MenuBarSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SessionBeansConfig {
    @Bean(BeanIds.MENUBAR_SESSION_BEAN)
    @SessionScope
    MenuBarSession menuBarSession() {
        return new MenuBarSession();
    }

    @Bean(BeanIds.ANONYMOUS_USER_SESSION_BEAN)
    @SessionScope
    AnonymousUserSession anonymousUserSession() {
        return new AnonymousUserSession();
    }
}
