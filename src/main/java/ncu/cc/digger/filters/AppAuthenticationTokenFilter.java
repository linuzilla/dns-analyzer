package ncu.cc.digger.filters;

import ncu.cc.digger.security.AuthenticationHelper;
import ncu.cc.digger.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

@Component
public class AppAuthenticationTokenFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationTokenFilter.class);
    private final AuthenticationService authenticationService;

    public AppAuthenticationTokenFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Optional.ofNullable(AuthenticationHelper.authentication())
                .filter(Authentication::isAuthenticated)
                .filter(OAuth2AuthenticationToken.class::isInstance)
                .map(OAuth2AuthenticationToken.class::cast)
                .map(AbstractAuthenticationToken::getName)
                .map(authenticationService::authenticatedAs)
                .ifPresent(AuthenticationHelper::setAuthentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
