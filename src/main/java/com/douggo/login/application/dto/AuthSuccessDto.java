package com.douggo.login.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSuccessDto {

    private final UUID token;

    private final LocalDateTime timeToExpire;

    private AuthSuccessDto(UUID token, LocalDateTime timeToExpire) {
        this.token = token;
        this.timeToExpire = timeToExpire;
    }

    public static AuthSuccessDto of(UUID token, LocalDateTime timeToExpire) {
        return new AuthSuccessDto(token, timeToExpire);
    }

    public UUID getToken() {
        return token;
    }

    public LocalDateTime getTimeToExpire() {
        return timeToExpire;
    }
}
