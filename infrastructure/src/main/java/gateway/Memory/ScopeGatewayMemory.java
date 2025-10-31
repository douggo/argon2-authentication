package gateway.Memory;

import entity.Scope;
import gateway.ScopeGateway;
import gateway.mappers.ScopeMapper;
import persistence.scope.ScopeEntity;

import java.util.List;

public class ScopeGatewayMemory implements ScopeGateway {

    private final ScopeMapper mapper;
    private final List<ScopeEntity> repository;

    public ScopeGatewayMemory(ScopeMapper mapper, List<ScopeEntity> repository) {
        this.mapper = mapper;
        this.repository = repository;
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
