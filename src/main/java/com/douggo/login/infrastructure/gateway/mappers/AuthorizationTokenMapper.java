package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;

public class AuthorizationTokenMapper {

    public AuthorizationToken toDomain(AuthorizationTokenEntity tokenEntity) {
        return AuthorizationTokenEntity.toDomain(tokenEntity);
    }

    public AuthorizationTokenEntity toEntity(AuthorizationToken tokenDomain) {
        return AuthorizationTokenEntity.from(tokenDomain);
    }

}
