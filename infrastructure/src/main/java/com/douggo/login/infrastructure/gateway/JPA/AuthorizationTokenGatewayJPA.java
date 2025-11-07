package com.douggo.login.infrastructure.gateway.JPA;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;
import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenRepository;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeEntity;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeRepository;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeRepository;
import com.douggo.login.infrastructure.security.exceptions.DataNotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AuthorizationTokenGatewayJPA implements AuthorizationTokenGateway {

    private final AuthorizationTokenRepository repository;
    private final UserScopeRepository userScopeRepository;
    private final AuthorizationTokenScopeRepository authorizationTokenScopeRepository;
    private final AuthorizationTokenMapper mapper;
    private final Clock clock;

    public AuthorizationTokenGatewayJPA(
            AuthorizationTokenRepository repository,
            UserScopeRepository userScopeRepository,
            AuthorizationTokenScopeRepository authorizationTokenScopeRepository,
            AuthorizationTokenMapper mapper,
            Clock clock
    ) {
        this.repository = repository;
        this.userScopeRepository = userScopeRepository;
        this.authorizationTokenScopeRepository = authorizationTokenScopeRepository;
        this.mapper = mapper;
        this.clock = clock;
    }

    @Override
    public AuthorizationToken generateAuthorizationToken(Session session, User user) {
        List<UserScopeEntity> userScopes = this.userScopeRepository.findById_UserId(user.getId())
                .orElseThrow(() -> new DataNotFoundException("User doesn't have any scopes associated!"));

        LocalDateTime now = LocalDateTime.now(this.clock);
        AuthorizationTokenEntity tokenCreated = this.repository.save(
                this.mapper.toEntity(AuthorizationToken.of(UUID.randomUUID(), session, user, now, now.plusMinutes(5)))
        );

        this.authorizationTokenScopeRepository.saveAll(AuthorizationTokenScopeEntity.fromUserScopes(userScopes, tokenCreated));

        return this.mapper.toDomain(tokenCreated);
    }

    @Override
    public AuthorizationToken getTokenFrom(UUID authToken) {
        AuthorizationTokenEntity tokenEntity = this.repository.findById(authToken)
                .orElseThrow(() -> new DataNotFoundException("Token does't exists!"));
        return this.mapper.toDomain(tokenEntity);
    }

    @Override
    public boolean isTokenExpired(String token) {
        AuthorizationTokenEntity tokenEntity = this.repository.findById(UUID.fromString(token))
                .orElseThrow(() -> new DataNotFoundException("Token doesn't exists!"));
        return tokenEntity.getExpiredAt().isBefore(LocalDateTime.now(this.clock));
    }

}
