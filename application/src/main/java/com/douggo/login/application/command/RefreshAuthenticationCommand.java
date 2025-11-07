package com.douggo.login.application.command;

import java.util.UUID;

public class RefreshAuthenticationCommand {

    private final UUID authorizationToken;
    private final UUID refreshToken;
    private final UUID userId;

    public RefreshAuthenticationCommand(UUID authorizationToken, UUID refreshToken, UUID userId) {
        this.authorizationToken = authorizationToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public UUID getAuthorizationToken() {
        return authorizationToken;
    }

    public UUID getRefreshToken() {
        return refreshToken;
    }

    public UUID getUserId() {
        return userId;
    }
}
