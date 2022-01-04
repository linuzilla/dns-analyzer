package ncu.cc.digger.security;

import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.converters.AppUserConverter;
import ncu.cc.digger.entities.UserEntity;
import ncu.cc.digger.models.user.CurrentScene;
import ncu.cc.digger.repositories.SceneUserViewRepository;
import ncu.cc.digger.repositories.SystemUserRepository;
import ncu.cc.digger.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final SystemUserRepository systemUserRepository;
    private final SceneUserViewRepository sceneUserViewRepository;
    private final UserService userService;

    public AuthenticationServiceImpl(SystemUserRepository systemUserRepository, SceneUserViewRepository sceneUserViewRepository, UserService userService) {
        this.systemUserRepository = systemUserRepository;
        this.sceneUserViewRepository = sceneUserViewRepository;
        this.userService = userService;
    }

    @Override
    public Mono<String> reactiveCurrentUser() {
        return getReactiveAuthentication()
                .map(Authentication::getName);
    }

    @Override
    public Mono<AppUserAuthentication> reactiveGetAuthentication() {
        return getReactiveAuthentication()
                .filter(AppUserAuthentication.class::isInstance)
                .map(AppUserAuthentication.class::cast);
    }

    @Override
    public String currentUser() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : authentication.getName();
    }

    @Override
    public String getRemoteAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        String xRealIp = request.getHeader(Constants.REAL_REMOTE_IP_HEADER);
//
//        return (xRealIp != null && xRealIp.length() > 0) ? xRealIp : request.getRemoteAddr();

        return request.getRemoteAddr();
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Override
    public AppUserAuthentication authenticatedAs(final String userName) {
        AppUserDetails details = new AppUserDetails(userName, Collections.emptyList());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(RolesEnum.ROLE_USER.getAuthority());

        systemUserRepository.findById(userName)
                .ifPresent(systemUserEntity ->
                        AppUserConverter.rolesAsStream(systemUserEntity).forEach(grantedAuthorities::add));

        UserEntity userEntity = userService.findOrCreateUser(userName);

        details.setUid(userEntity.getId());
        details.setUuid(userEntity.getUuid());

        var scenes = this.sceneUserViewRepository.findByUserId(details.getUid());

        CurrentScene currentScene = null;

        if (scenes.size() > 0) {
            grantedAuthorities.add(RolesEnum.ROLE_VIEW.getAuthority());

            if (scenes.size() > 1) {
                grantedAuthorities.add(RolesEnum.ROLE_MULTIVIEW.getAuthority());
            }

            currentScene = new CurrentScene(
                    scenes.get(0).getSceneId(),
                    scenes.get(0).getSceneName(),
                    scenes.get(0).getRole()
            );
        }

        var appUserAuthentication = new AppUserAuthentication(details, grantedAuthorities);

        appUserAuthentication.setCurrentScene(currentScene);

        return appUserAuthentication;
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public Optional<AppUserAuthentication> getAppUserAuthentication() {
        Authentication authentication = getAuthentication();

        if (authentication instanceof AppUserAuthentication) {
            return Optional.of((AppUserAuthentication) authentication);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AppUserDetails> getUserDetails() {
        return getAppUserAuthentication()
                .map(AppUserAuthentication::getAppUser);
    }

    @Override
    public boolean hasRole(RolesEnum rolesEnum) {
        return getAppUserAuthentication()
                .map(appUserAuthentication -> appUserAuthentication.hasRole(rolesEnum))
                .orElse(false);
    }

    @Override
    public boolean hasRole(GrantedAuthority grantedAuthority) {
        return getAuthentication().getAuthorities().contains(grantedAuthority);
    }

    @Override
    public boolean hasAnyRole(RolesEnum... rolesEnums) {
        return getAppUserAuthentication()
                .map(appUserAuthentication -> appUserAuthentication.hasAnyRole(rolesEnums))
                .orElse(false);
    }
}