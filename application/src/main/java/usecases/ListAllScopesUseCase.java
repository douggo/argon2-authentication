package usecases;

import entity.Scope;
import gateway.ScopeGateway;

import java.util.List;

public class ListAllScopesUseCase {

    private final ScopeGateway scopeGateway;

    public ListAllScopesUseCase(ScopeGateway scopeGateway) {
        this.scopeGateway = scopeGateway;
    }

    public List<Scope> execute() {
        return this.scopeGateway.getAllScopes();
    }

}
