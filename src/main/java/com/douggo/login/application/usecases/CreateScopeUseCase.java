package com.douggo.login.application.usecases;

import com.douggo.login.application.gateway.ScopeGateway;
import com.douggo.login.domain.entity.Scope;

public class CreateScopeUseCase {

    private final ScopeGateway scopeGateway;

    public CreateScopeUseCase(ScopeGateway scopeGateway) {
        this.scopeGateway = scopeGateway;
    }

    public Scope execute(Scope scope) {
        return this.scopeGateway.createScope(scope);
    }

}
