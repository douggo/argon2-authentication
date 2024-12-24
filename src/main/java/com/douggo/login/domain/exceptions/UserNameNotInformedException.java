package com.douggo.login.domain.exceptions;

public class UserNameNotInformedException extends RuntimeException {

    private final int statusCode;

    public UserNameNotInformedException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
