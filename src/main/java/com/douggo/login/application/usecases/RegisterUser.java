package com.douggo.login.application.usecases;

import com.douggo.login.application.gateway.UserUseCaseRepository;
import com.douggo.login.domain.entity.User;

public class RegisterUser {

    private final UserUseCaseRepository repository;

    public RegisterUser(UserUseCaseRepository repository) {
        this.repository = repository;
    }

    public User execute(User user) {
        return this.repository.register(user);
    }

}