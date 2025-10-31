package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;

import java.util.Objects;

public class AuthorizationTokenMapper {

    public AuthorizationToken toDomain(AuthorizationTokenEntity tokenEntity) {
        if (Objects.isNull(tokenEntity)) {
            throw new ObjectIsNullException(AuthorizationTokenEntity.class.getSimpleName());
        }
        return AuthorizationTokenEntity.toDomain(tokenEntity);
    }

    public AuthorizationTokenEntity toEntity(AuthorizationToken tokenDomain) {
        if (Objects.isNull(tokenDomain)) {
            throw new ObjectIsNullException(AuthorizationToken.class.getSimpleName());
        }
        return AuthorizationTokenEntity.from(tokenDomain);
    }

}
