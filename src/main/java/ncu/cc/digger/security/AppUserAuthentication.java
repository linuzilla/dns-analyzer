package ncu.cc.digger.security;

import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.models.user.CurrentScene;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class AppUserAuthentication implements Authentication {
    private final AppUserDetails appUser;
    private final Set<GrantedAuthority> authorities;
    private boolean authenticated;
    private long code;
    private CurrentScene currentScene;

    public AppUserAuthentication(AppUserDetails appUser, List<GrantedAuthority> authorities) {
        this.appUser = appUser;
        this.authorities = new HashSet<>(authorities);
        this.authenticated = true;

        rehash();
    }

    private void rehash() {
        StringBuilder builder = new StringBuilder();

        authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .map(s -> "[" + s + "]")
                .forEach(builder::append);

        String v = builder.toString();

        code = v.length() * 100000L + v.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return appUser;
    }

    @Override
    public Object getPrincipal() {
        return appUser.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        authenticated = false;
    }

    @Override
    public String getName() {
        return appUser.getUsername();
    }

    public void revokeAuthority(GrantedAuthority grantedAuthority) {
        authorities.remove(grantedAuthority);
        rehash();
    }

    public void addAuthority(GrantedAuthority grantedAuthority) {
        authorities.add(grantedAuthority);
        rehash();
    }

    public boolean hasRole(RolesEnum rolesEnum) {
        return authorities.contains(rolesEnum.getAuthority());
    }

    public boolean hasRole(GrantedAuthority authority) {
        return authorities.contains(authority);
    }

    public boolean hasAnyRole(RolesEnum ...rolesEnums) {
        return Stream.of(rolesEnums)
                .anyMatch(rolesEnum -> authorities.contains(rolesEnum.getAuthority()));
    }

    public AppUserDetails getAppUser() {
        return appUser;
    }

    public long getCode() {
        return code;
    }

    @Null
    public CurrentScene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(CurrentScene currentScene) {
        this.currentScene = currentScene;
    }
}
