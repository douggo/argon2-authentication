package usecases;

import entity.User;
import gateway.UserScopeGateway;

import java.util.UUID;

public class ListAllUserScopesUseCase {

    private final UserScopeGateway userScopeGateway;

    public ListAllUserScopesUseCase(UserScopeGateway userScopeGateway) {
        this.userScopeGateway = userScopeGateway;
    }

    public User execute(UUID userId) throws IllegalAccessException {
        return this.userScopeGateway.getAllScopesFromUser(userId);
    }
}
