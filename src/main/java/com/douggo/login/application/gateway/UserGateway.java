package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    Optional<List<User>> getAll();

    User register(User user);

}
