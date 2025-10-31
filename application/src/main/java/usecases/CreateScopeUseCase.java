package usecases;

import entity.Scope;
import gateway.ScopeGateway;

public class CreateScopeUseCase {

    private final ScopeGateway scopeGateway;

    public CreateScopeUseCase(ScopeGateway scopeGateway) {
        this.scopeGateway = scopeGateway;
    }

    public Scope execute(Scope scope) {
        return this.scopeGateway.createScope(scope);
    }

}
