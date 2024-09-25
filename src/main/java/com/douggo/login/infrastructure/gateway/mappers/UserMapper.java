package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.user.UserEntity;

public class UserMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.fromUserDomain(user);
    }

    public User toDomain(UserEntity user) {
        return UserEntity.toDomain(user);
    }

}
