package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;

public class UserScopeMapper {

    public UserScopeEntity toEntity(User userWithScope) {
        return UserScopeEntity.of(userWithScope);
    }

    public User toDomain(UserScopeEntity UserScopeCreated) {
        return UserScopeEntity.toDomain(UserScopeCreated);
    }
}
