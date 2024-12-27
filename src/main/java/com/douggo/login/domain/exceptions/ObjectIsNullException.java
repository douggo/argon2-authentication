package com.douggo.login.domain.exceptions;

public class ObjectIsNullException extends RuntimeException {

    private final int statusCode;

    public ObjectIsNullException(String className) {
        super("The " + className + " object cannot be null!");
        this.statusCode = 400; // BAD REQUEST
    }

    public int getStatusCode() {
        return statusCode;
    }

}
