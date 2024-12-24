package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.AuthorizationTokenScopeGateway;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenRepository;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class AuthorizationTokenScopeGatewayJPA implements AuthorizationTokenScopeGateway {

    private AuthorizationTokenScopeRepository repository;
    private AuthorizationTokenRepository tokenRepository;

    public AuthorizationTokenScopeGatewayJPA(
            AuthorizationTokenScopeRepository repository,
            AuthorizationTokenRepository tokenRepository
    ) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public UserDetails convertIntoUserDetails(UUID token) throws IllegalArgumentException {
        String userId = this.tokenRepository.findById(token)
                .orElseThrow(() -> new IllegalArgumentException("Token doesn't exists!"))
                .getUser()
                .getId()
                .toString();

        List<SimpleGrantedAuthority> authorities = this.repository.findById_TokenId(token)
                .orElseThrow(() -> new IllegalArgumentException("Token doesn't have scopes associated with!"))
                .stream()
                .map(authorizationTokenScopeEntity -> new SimpleGrantedAuthority(authorizationTokenScopeEntity.getScope()
                        .getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(userId, "", authorities);
    }
}
