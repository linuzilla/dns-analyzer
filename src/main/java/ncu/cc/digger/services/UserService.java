package ncu.cc.digger.services;

import ncu.cc.digger.entities.UserEntity;

public interface UserService {
    UserEntity findOrCreateUser(String email);
}
