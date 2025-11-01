package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserGateway {

    List<User> getAll();

    User getUserByEmail(String email) throws IllegalAccessException;

    User register(User user);

    User getUserById(UUID id) throws IllegalAccessException;

}
