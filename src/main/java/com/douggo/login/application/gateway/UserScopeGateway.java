package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;

import java.util.UUID;

public interface UserScopeGateway {

    User bindScopeToUser(User userWithScope);

    User getAllScopesFromUser(UUID userId) throws IllegalAccessException;

}
