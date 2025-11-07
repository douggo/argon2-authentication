package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.entity.Session;

public interface RefreshTokenGateway {

    RefreshToken generateRefreshToken(Session session);

}
