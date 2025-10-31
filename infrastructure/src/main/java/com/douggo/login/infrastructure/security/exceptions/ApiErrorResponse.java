package com.douggo.login.infrastructure.security.exceptions;

public class ApiErrorResponse {

    private final String exceptionName;
    private final int status;
    private final String message;

    private ApiErrorResponse(String exceptionName, int status, String message) {
        this.exceptionName = exceptionName;
        this.status = status;
        this.message = message;
    }

    public static ApiErrorResponse of(String exceptionName, int status, String message) {
        return new ApiErrorResponse(exceptionName, status, message);
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
