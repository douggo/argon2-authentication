package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.ScopeGateway;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import com.douggo.login.infrastructure.persistence.scope.ScopeRepository;

import java.util.List;

public class ScopeGatewayJPA implements ScopeGateway {

    private final ScopeRepository repository;
    private final ScopeMapper mapper;

    public ScopeGatewayJPA(ScopeRepository repository, ScopeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Scope createScope(Scope scope) {
        return this.mapper.toDomain(this.repository.save(this.mapper.toEntity(scope)));
    }

    @Override
    public List<Scope> getAllScopes() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

}
