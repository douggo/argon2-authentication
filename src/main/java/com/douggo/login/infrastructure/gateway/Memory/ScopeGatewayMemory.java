package com.douggo.login.infrastructure.gateway.Memory;

import com.douggo.login.application.gateway.ScopeGateway;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;

import java.util.ArrayList;
import java.util.List;

public class ScopeGatewayMemory implements ScopeGateway {

    private final ScopeMapper mapper;
    private final List<ScopeEntity> repository;

    public ScopeGatewayMemory(ScopeMapper mapper) {
        this.mapper = mapper;
        this.repository = new ArrayList<>();
    }

    @Override
    public Scope createScope(Scope scope) {
        ScopeEntity scopeEntity = this.mapper.toEntity(scope);
        this.repository.add(scopeEntity);
        return this.mapper.toDomain(scopeEntity);
    }

    @Override
    public List<Scope> getAllScopes() {
        return this.repository.stream()
                .map(this.mapper::toDomain)
                .toList();
    }

}
