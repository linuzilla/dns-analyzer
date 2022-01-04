package ncu.cc.digger.security;

import ncu.cc.commons.utils.StackTraceUtil;
import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.BeanIds;
import ncu.cc.digger.converters.AppUserConverter;
import ncu.cc.digger.entities.SystemUserEntity;
import ncu.cc.digger.repositories.SystemUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@Qualifier(BeanIds.USER_DETAILS_SERVICE_BEAN)
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private static final Pattern USER_PATTERN = Pattern.compile(
            "^\\w+(\\.\\w+){0,2}(@[-\\w]+(\\.[-\\w]+){1,6})?$"
    );

    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(SystemUserRepository systemUserRepository, PasswordEncoder passwordEncoder) {
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        StackTraceUtil.print1(userName);

        if (USER_PATTERN.matcher(userName).matches()) {
            logger.info("loadUserByUsername: {} (matched pattern)", userName);
            Optional<SystemUserEntity> optional = systemUserRepository.findById(userName.toLowerCase());

            if (optional.isPresent() && StringUtil.isNotEmpty(optional.get().getPassword())) {
                return AppUserConverter.fromUserEntity(optional.get());
            } else if (optional.isPresent()) {
                logger.info("user {} have no password", userName);
            } else {
                logger.info("user {} not in database", userName);
            }
        }
        throw new UsernameNotFoundException("User not found");
    }
}
