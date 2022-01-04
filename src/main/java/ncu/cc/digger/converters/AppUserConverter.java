package ncu.cc.digger.converters;

import com.google.gson.Gson;
import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.Miscellaneous;
import ncu.cc.digger.constants.RolesEnum;
import ncu.cc.digger.entities.SystemUserEntity;
import ncu.cc.digger.security.AppUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppUserConverter {
    private static final Logger logger = LoggerFactory.getLogger(AppUserConverter.class);

    public static AppUserDetails fromUserEntity(SystemUserEntity entity) {
        AppUserDetails details = new AppUserDetails(entity.getAccount(), rolesAsStream(entity).collect(Collectors.toList()));
        details.setPassword(entity.getPassword());
        return details;
    }

    public static Stream<GrantedAuthority> rolesAsStream(SystemUserEntity systemUserEntity) {
        if (StringUtil.isNotEmpty(systemUserEntity.getRolesJson())) {
            return ((List<String>) new Gson().fromJson(systemUserEntity.getRolesJson(), Miscellaneous.LIST_TYPE))
                    .stream()
                    .map(RolesEnum::byName)
                    .filter(role -> RolesEnum.ROLE_NULL != role)
                    .peek(role -> logger.trace("add role: {} to {}", role.shortName(), systemUserEntity.getAccount()))
                    .map(RolesEnum::getAuthority);
        } else {
            return Stream.empty();
        }
    }
}
