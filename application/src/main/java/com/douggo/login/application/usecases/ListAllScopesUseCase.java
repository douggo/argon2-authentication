package com.douggo.login.application.usecases;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.application.gateway.ScopeGateway;

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
