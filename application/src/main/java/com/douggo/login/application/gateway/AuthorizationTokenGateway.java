package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;

import java.util.UUID;

public interface AuthorizationTokenGateway {

    AuthorizationToken generateAuthorizationToken(Session session, User user);

    AuthorizationToken getTokenFrom(UUID authToken);

    boolean isTokenExpired(String token);
}
