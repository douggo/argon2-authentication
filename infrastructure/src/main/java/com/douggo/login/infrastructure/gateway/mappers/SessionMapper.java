package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.persistence.session.SessionEntity;

import java.util.Objects;

public class SessionMapper {

    public SessionEntity toEntity(Session domain) {
        if (Objects.isNull(domain)) {
            throw new ObjectIsNullException(Session.class.getSimpleName());
        }
        return SessionEntity.fromDomain(domain);
    }

    public Session toDomain(SessionEntity entity) {
        if (Objects.isNull(entity)) {
            throw new ObjectIsNullException(SessionEntity.class.getSimpleName());
        }
        return SessionEntity.toDomain(entity);
    }

}
