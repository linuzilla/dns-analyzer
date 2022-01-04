package ncu.cc.digger.security;

import ncu.cc.digger.constants.RolesEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface AuthenticationService {
    default Mono<SecurityContext> getReactiveSecurityContext() {
        return ReactiveSecurityContextHolder.getContext();
    }

    default Mono<Authentication> getReactiveAuthentication() {
        return getReactiveSecurityContext().map(SecurityContext::getAuthentication);
    }

    default Function<Context, Context> reactiveLogout() {
        return ReactiveSecurityContextHolder.clearContext();
    }
//    String getRemoteAddr();
//    void logout();
    Mono<String> reactiveCurrentUser();
    Mono<AppUserAuthentication> reactiveGetAuthentication();

    default Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    Optional<AppUserAuthentication> getAppUserAuthentication();

    String currentUser();
    String getRemoteAddr();

    boolean isAuthenticated();

    AppUserAuthentication authenticatedAs(final String userName);

    void logout();

    Optional<AppUserDetails> getUserDetails();

    boolean hasRole(RolesEnum rolesEnum);
    boolean hasRole(GrantedAuthority grantedAuthority);
    boolean hasAnyRole(RolesEnum ...rolesEnums);
}
