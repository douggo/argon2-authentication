package com.douggo.login.application.dto;

import com.douggo.login.domain.entity.AuthorizationToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSuccessDto {

    private final UUID token;

    private final UUID refreshToken;

    private final LocalDateTime timeToExpire;

    private AuthSuccessDto(UUID token, UUID refreshToken, LocalDateTime timeToExpire) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.timeToExpire = timeToExpire;
    }

    public static AuthSuccessDto from(AuthorizationToken token, RefreshToken refreshToken) {
        return new AuthSuccessDto(token.getId(), refreshToken.getId(), token.getExpiredAt());
    }

    public UUID getToken() {
        return token;
    }

    public LocalDateTime getTimeToExpire() {
        return timeToExpire;
    }

    @Override
    public String toString() {
        return "AuthSuccessDto{" +
                "token=" + token +
                ", refreshToken=" + refreshToken +
                ", timeToExpire=" + timeToExpire +
                '}';
    }
}
