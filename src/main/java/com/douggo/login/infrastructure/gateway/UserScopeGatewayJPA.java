package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.UserScopeGateway;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.UserScopeMapper;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeRepository;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserScopeGatewayJPA implements UserScopeGateway {

    private final UserScopeRepository repository;
    private final UserScopeMapper mapper;

    public UserScopeGatewayJPA(UserScopeRepository repository, UserScopeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User bindScopeToUser(User userWithScope) {
        return this.mapper.toDomain(this.repository.save(this.mapper.toEntity(userWithScope)));
    }

    @Override
    public User getAllScopesFromUser(UUID userId) throws IllegalAccessException {
        Optional<List<UserScopeEntity>> userScopes = this.repository.findById_UserId(userId);
        if (userScopes.isEmpty()) {
            throw new IllegalAccessException("User doesn't have scopes");
        }
        List<Scope> scopes = userScopes.get()
                .stream()
                .map(userScope -> Scope.of(userScope.getId().getScopeId(), userScope.getScope().getName()))
                .toList();
        return new User.Builder()
                .id(userId)
                .scopes(scopes)
                .createWithoutValidation();
    }

}
