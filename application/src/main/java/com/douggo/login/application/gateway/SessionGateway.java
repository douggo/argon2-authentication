package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;

public interface SessionGateway {

    Session createSession(User user);

}
