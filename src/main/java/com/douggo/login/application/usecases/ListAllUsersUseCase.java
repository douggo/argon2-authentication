package com.douggo.login.application.usecases;

import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.User;

import java.util.Collections;
import java.util.List;

public class ListAllUsersUseCase {

    private final UserGateway userGateway;

    public ListAllUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return this.userGateway.getAll().orElse(Collections.emptyList());
    }

}
