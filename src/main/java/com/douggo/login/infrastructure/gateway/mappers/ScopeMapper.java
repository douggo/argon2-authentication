package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;

public class ScopeMapper {

    public ScopeEntity toEntity(Scope scope) {
        return ScopeEntity.toEntity(scope);
    }

    public Scope toDomain(ScopeEntity scope) {
        return ScopeEntity.toDomain(scope);
    }

}
