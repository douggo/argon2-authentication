package usecases;

import entity.User;
import gateway.UserScopeGateway;

public class BindScopeToUserUseCase {

    private final UserScopeGateway userScopeGateway;

    public BindScopeToUserUseCase(UserScopeGateway userScopeGateway) {
        this.userScopeGateway = userScopeGateway;
    }

    public User execute(User userWithScope) {
        return this.userScopeGateway.bindScopeToUser(userWithScope);
    }
}
