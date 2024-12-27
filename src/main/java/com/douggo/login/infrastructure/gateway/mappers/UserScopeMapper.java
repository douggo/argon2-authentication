package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.User;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;

import java.util.Objects;

public class UserScopeMapper {

    public UserScopeEntity toEntity(User userWithScope) {
        if (Objects.isNull(userWithScope)) {
            throw new ObjectIsNullException(User.class.getSimpleName());
        }
        return UserScopeEntity.of(userWithScope);
    }

    public User toDomain(UserScopeEntity userScopeCreated) {
        if (Objects.isNull(userScopeCreated)) {
            throw new ObjectIsNullException(UserScopeEntity.class.getSimpleName());
        }
        return UserScopeEntity.toDomain(userScopeCreated);
    }
}
