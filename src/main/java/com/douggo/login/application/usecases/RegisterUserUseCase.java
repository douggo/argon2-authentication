package com.douggo.login.application.usecases;

import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.User;

public class RegisterUserUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public RegisterUserUseCase(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public User execute(User userDomain) {
        User userPersisted = this.userGateway.register(userDomain);
        this.passwordGateway.createPassword(userPersisted, userDomain.getPasswords().getFirst());
        return userPersisted;
    }

}