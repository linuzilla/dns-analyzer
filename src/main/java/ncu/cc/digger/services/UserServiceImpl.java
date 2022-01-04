package ncu.cc.digger.services;

import ncu.cc.commons.utils.BigIntegerUtil;
import ncu.cc.digger.entities.UserEntity;
import ncu.cc.digger.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findOrCreateUser(String email) {
        return this.userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setEmail(email);
                    userEntity.setNickname(email);
                    userEntity.setRole(0);
                    userEntity.setPassword(null);
                    userEntity.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
                    userEntity = this.userRepository.save(userEntity);

                    userEntity.setUuid(BigInteger.valueOf(BigIntegerUtil.generateRandomUniqueLong(userEntity.getId())));
                    userEntity = this.userRepository.save(userEntity);

                    logger.info("Create user entry ( account: {}, uid: {}, uuid: {} )",
                            email, userEntity.getId(), userEntity.getUuid());

                    return userEntity;
                });
    }
}
