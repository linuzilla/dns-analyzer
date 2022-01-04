package ncu.cc.digger.constants;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public enum RolesEnum {
    ROLE_NULL(RolesEnum.NULL),              // dummy, null
    ROLE_USER(RolesEnum.USER),              // any logged in user
    ROLE_VIEW(RolesEnum.VIEW),
    ROLE_MULTIVIEW(RolesEnum.MULTIVIEW),
    ROLE_SYSOP(RolesEnum.SYSOP),             // system administrator
    ROLE_ADMIN(RolesEnum.ADMIN),             // system administrator
    ROLE_DEVELOPER(RolesEnum.DEVELOPER),        // developer

    ROLE_APICLIENT(RolesEnum.APICLIENT);         // developer

    public static final String NULL = "NULL";
    public static final String USER = "USER";
    public static final String VIEW = "VIEW";
    public static final String MULTIVIEW = "MULTIVIEW";
    public static final String SYSOP = "SYSOP";
    public static final String ADMIN = "ADMIN";
    public static final String DEVELOPER = "DEVELOPER";

    public static final String APICLIENT = "APICLIENT";

    private static final String PREFIX = "ROLE_";
    private static final Map<String,GrantedAuthority> allAuthorities = new HashMap<>();
    private final String value;
    private final GrantedAuthority authority;

    private static class MyGrantedAuthority implements GrantedAuthority {
        private final RolesEnum authorityEnum;
        private final String authority;

        private MyGrantedAuthority(RolesEnum authorityEnum) {
            this.authorityEnum = authorityEnum;
            this.authority = authorityEnum.name();
        }

        @Override
        public String getAuthority() {
            return authority;
        }

        private RolesEnum getAuthorityEnum() {
            return authorityEnum;
        }

        @Override
        public String toString() {
            return getAuthorityEnum().shortName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyGrantedAuthority that = (MyGrantedAuthority) o;
            return Objects.equals(authority, that.authority);
        }

        @Override
        public int hashCode() {
            return authority.hashCode();
        }
    }

    RolesEnum(String value) {
        this.value = value;
        authority = new MyGrantedAuthority(this);

        Assert.isTrue(value.equals(shortName()), "Oops! " + value + " not match with " + shortName());
    }

//    public String getAuthority() {
//        return authority.getAuthority();
//    }

    public static RolesEnum byName(final String name) {
        for (RolesEnum entry : RolesEnum.values()) {
            if (name.equals(entry.authority.getAuthority())) {
                return entry;
            }
        }

        for (RolesEnum entry : RolesEnum.values()) {
            if ((PREFIX + name).equals(entry.authority.getAuthority())) {
                return entry;
            }
        }

        return ROLE_NULL;
    }

    public static final GrantedAuthority toAuthority(final String name) {
        for (RolesEnum entry : RolesEnum.values()) {
            if (name.equals(entry.authority.getAuthority())) {
                return entry.getAuthority();
            }
        }

        for (RolesEnum entry : RolesEnum.values()) {
            if ((PREFIX + name).equals(entry.authority.getAuthority())) {
                return entry.getAuthority();
            }
        }

        GrantedAuthority authority = null;

        synchronized (allAuthorities) {
            String fullName = name.startsWith(PREFIX) ? name : PREFIX + name;

            authority = allAuthorities.get(fullName);
            if (authority == null) {
                authority = new SimpleGrantedAuthority(fullName);
                allAuthorities.put(fullName, authority);
            }
        }
        return authority;
    }

    public String shortName() {
        if (this.name().startsWith(PREFIX)) {
            return this.name().substring(PREFIX.length());
        } else {
            return this.name();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.shortName();
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }
}
