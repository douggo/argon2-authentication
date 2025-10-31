package gateway.Memory;

import entity.Scope;
import entity.User;
import gateway.UserScopeGateway;
import gateway.mappers.UserScopeMapper;
import persistence.userScope.UserScopeEntity;

import java.util.List;
import java.util.UUID;

public class UserScopeGatewayMemory implements UserScopeGateway {

    private final List<UserScopeEntity> repository;
    private final UserScopeMapper mapper;

    public UserScopeGatewayMemory(List<UserScopeEntity> repository, UserScopeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User bindScopeToUser(User userWithScope) {
        UserScopeEntity userScopeEntity = this.mapper.toEntity(userWithScope);
        this.repository.add(userScopeEntity);
        return this.mapper.toDomain(userScopeEntity);
    }

    @Override
    public User getAllScopesFromUser(UUID userId) throws IllegalAccessException {
        return new User.Builder()
                .id(userId)
                .scopes(this.repository.stream()
                        .filter(userScopeEntity -> userScopeEntity.getUser().getId().compareTo(userId) == 0)
                        .map(userScopeEntity -> Scope.of(userScopeEntity.getId().getScopeId(), userScopeEntity.getScope().getName()))
                        .toList())
                .createWithoutValidation();
    }

}
