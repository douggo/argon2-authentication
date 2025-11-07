package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenEntity;

import java.util.Objects;

public class RefreshTokenMapper {

    public RefreshTokenEntity toEntity(RefreshToken domain) {
        if (Objects.isNull(domain)) {
            throw new ObjectIsNullException(RefreshTokenEntity.class.getSimpleName());
        }
        return RefreshTokenEntity.toEntity(domain);
    }

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        if (Objects.isNull(entity)) {
            throw new ObjectIsNullException(RefreshTokenEntity.class.getSimpleName());
        }
        return RefreshTokenEntity.toDomain(entity);
    }

}
