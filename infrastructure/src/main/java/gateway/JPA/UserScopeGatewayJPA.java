package gateway.JPA;

import entity.Scope;
import entity.User;
import gateway.UserScopeGateway;
import gateway.mappers.UserScopeMapper;
import persistence.userScope.UserScopeEntity;
import persistence.userScope.UserScopeRepository;
import security.exceptions.DataNotFoundException;

import java.util.List;
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
        List<UserScopeEntity> userScopes = this.repository.findById_UserId(userId)
                .orElseThrow(() -> new DataNotFoundException("User doesn't have scopes"));
        List<Scope> scopes = userScopes
                .stream()
                .map(userScope -> Scope.of(userScope.getId().getScopeId(), userScope.getScope().getName()))
                .toList();
        return new User.Builder()
                .id(userId)
                .scopes(scopes)
                .createWithoutValidation();
    }

}
