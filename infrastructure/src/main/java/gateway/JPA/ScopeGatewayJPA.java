package gateway.JPA;

import entity.Scope;
import gateway.ScopeGateway;
import gateway.mappers.ScopeMapper;
import persistence.scope.ScopeRepository;

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
