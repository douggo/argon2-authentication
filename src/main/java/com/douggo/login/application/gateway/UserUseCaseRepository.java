package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.User;

import java.util.List;

public interface UserUseCaseRepository {

    List<User> listAll();

    User register(User user);

}