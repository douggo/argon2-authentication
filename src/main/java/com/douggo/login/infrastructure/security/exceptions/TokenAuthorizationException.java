package com.douggo.login.infrastructure.security.exceptions;

public class TokenAuthorizationException extends RuntimeException {

    private final int statusCode;

    public TokenAuthorizationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
