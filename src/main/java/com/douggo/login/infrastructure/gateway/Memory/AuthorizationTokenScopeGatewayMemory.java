package com.douggo.login.infrastructure.gateway.Memory;

import com.douggo.login.application.gateway.AuthorizationTokenScopeGateway;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AuthorizationTokenScopeGatewayMemory implements AuthorizationTokenScopeGateway {

    private final List<AuthorizationTokenScopeEntity> repository;

    public AuthorizationTokenScopeGatewayMemory(List<AuthorizationTokenScopeEntity> repository) {
        this.repository = repository;
    }

    @Override
    public boolean doesTokenHasAnyRequiredScope(String token, String[] requiredScopes) {
        return Arrays.stream(requiredScopes)
                .anyMatch(requiredScope ->
                        this.repository.stream()
                                .filter(tokenScopeEntity -> tokenScopeEntity.getToken().getId().compareTo(UUID.fromString(token)) == 0)
                                .anyMatch(tokenScopeEntity -> tokenScopeEntity.getScope().getName().equalsIgnoreCase(requiredScope))
                );
    }
}
