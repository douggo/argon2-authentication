package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Scope;

import java.util.List;

public interface ScopeGateway {

    Scope createScope(Scope scope);

    List<Scope> getAllScopes();
}
