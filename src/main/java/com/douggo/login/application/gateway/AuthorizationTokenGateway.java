package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.User;

public interface AuthorizationTokenGateway {

    AuthorizationToken generateAuthorizationToken(User user);

}
