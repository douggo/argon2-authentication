package com.douggo.login.application.gateway;

public interface AuthorizationTokenScopeGateway {

    boolean doesTokenHasAnyRequiredScope(String token, String[] requiredScopes);
}
