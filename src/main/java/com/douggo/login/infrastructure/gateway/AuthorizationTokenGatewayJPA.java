package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenRepository;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeEntity;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeRepository;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthorizationTokenGatewayJPA implements AuthorizationTokenGateway {

    private final AuthorizationTokenRepository repository;
    private final UserScopeRepository userScopeRepository;
    private final AuthorizationTokenScopeRepository authorizationTokenScopeRepository;
    private final AuthorizationTokenMapper mapper;

    public AuthorizationTokenGatewayJPA(
            AuthorizationTokenRepository repository,
            UserScopeRepository userScopeRepository,
            AuthorizationTokenScopeRepository authorizationTokenScopeRepository,
            AuthorizationTokenMapper mapper
    ) {
        this.repository = repository;
        this.userScopeRepository = userScopeRepository;
        this.authorizationTokenScopeRepository = authorizationTokenScopeRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorizationToken generateAuthorizationToken(User user) {
        Optional<List<UserScopeEntity>> userScopes = this.userScopeRepository.findById_UserId(user.getId());

        if (userScopes.isEmpty()) {
            throw new EmptyResultDataAccessException("User doesn't have any scopes associated!", 0);
        }

        LocalDateTime now = LocalDateTime.now();
        AuthorizationTokenEntity tokenCreated = this.repository.save(
                this.mapper.toEntity(AuthorizationToken.of(UUID.randomUUID(), user, now, now.plusMinutes(5)))
        );
        this.authorizationTokenScopeRepository.saveAll(AuthorizationTokenScopeEntity.fromUserScopes(userScopes.get(), tokenCreated));

        return this.mapper.toDomain(tokenCreated);
    }
}
