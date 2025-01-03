package com.douggo.login.infrastructure.gateway.Memory;

import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import com.douggo.login.infrastructure.security.exceptions.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AuthorizationTokenGatewayMemory implements AuthorizationTokenGateway {

    private final List<AuthorizationTokenEntity> tokenRepository;
    private final List<UserScopeEntity> userScopeRepository;
    private final List<AuthorizationTokenScopeEntity> tokenScopeRepository;
    private final AuthorizationTokenMapper mapper;

    public AuthorizationTokenGatewayMemory(
            List<AuthorizationTokenEntity> tokenRepository,
            List<UserScopeEntity> userScopeRepository,
            List<AuthorizationTokenScopeEntity> tokenScopeRepository,
            AuthorizationTokenMapper mapper
    ) {
        this.tokenRepository = tokenRepository;
        this.userScopeRepository = userScopeRepository;
        this.tokenScopeRepository = tokenScopeRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorizationToken generateAuthorizationToken(User user) {
        List<UserScopeEntity> userScopes = this.userScopeRepository.stream()
                .filter(scope -> scope.getUser()
                        .getId()
                        .compareTo(user.getId()) == 0)
                .toList();
        LocalDateTime now = LocalDateTime.now();
        AuthorizationTokenEntity tokenCreated = this.mapper.toEntity(AuthorizationToken.of(UUID.randomUUID(), user, now, now.plusMinutes(5)));
        this.tokenRepository.add(tokenCreated);
        this.tokenScopeRepository.addAll(AuthorizationTokenScopeEntity.fromUserScopes(userScopes, tokenCreated));
        return this.mapper.toDomain(tokenCreated);
    }

    @Override
    public boolean isTokenExpired(String token) {
         AuthorizationTokenEntity tokenFound = this.tokenRepository.stream()
                .filter(tokenEntity -> tokenEntity.getId().compareTo(UUID.fromString(token)) == 0)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Token doesn't exists!"));
        return tokenFound.getExpiredAt().isBefore(LocalDateTime.now());
    }

}
