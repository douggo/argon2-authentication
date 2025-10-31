package gateway.mappers;

import entity.AuthorizationToken;
import exceptions.ObjectIsNullException;
import persistence.authorizationToken.AuthorizationTokenEntity;

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
