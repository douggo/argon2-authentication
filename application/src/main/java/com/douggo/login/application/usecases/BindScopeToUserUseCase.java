package com.douggo.login.application.usecases;

import com.douggo.login.domain.entity.User;
import com.douggo.login.application.gateway.UserScopeGateway;

public class BindScopeToUserUseCase {

    private final UserScopeGateway userScopeGateway;

    public BindScopeToUserUseCase(UserScopeGateway userScopeGateway) {
        this.userScopeGateway = userScopeGateway;
    }

    public User execute(User userWithScope) {
        return this.userScopeGateway.bindScopeToUser(userWithScope);
    }
}
