package dto;

import entity.AuthorizationToken;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSuccessDto {

    private final UUID token;

    private final LocalDateTime timeToExpire;

    private AuthSuccessDto(UUID token, LocalDateTime timeToExpire) {
        this.token = token;
        this.timeToExpire = timeToExpire;
    }

    public static AuthSuccessDto of(AuthorizationToken token) {
        return new AuthSuccessDto(token.getId(), token.getExpiredAt());
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
                ", timeToExpire=" + timeToExpire +
                '}';
    }
}
