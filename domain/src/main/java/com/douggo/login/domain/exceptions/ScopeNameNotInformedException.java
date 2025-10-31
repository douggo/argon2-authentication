package com.douggo.login.domain.exceptions;

public class ScopeNameNotInformedException extends RuntimeException {

    private final int statusCode;

    public ScopeNameNotInformedException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
