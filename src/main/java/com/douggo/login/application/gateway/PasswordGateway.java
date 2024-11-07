package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;

import java.util.UUID;

public interface PasswordGateway {

    void createPassword(User user, Password password);

    Password getUserPassword(UUID userId) throws IllegalAccessException;
}
