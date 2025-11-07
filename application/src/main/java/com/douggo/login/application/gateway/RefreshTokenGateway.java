package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.entity.Session;

import java.util.UUID;

public interface RefreshTokenGateway {

    RefreshToken generateRefreshToken(Session session);

    RefreshToken getFrom(UUID refreshToken);

    void markAsUsed(RefreshToken refreshToken);
}
