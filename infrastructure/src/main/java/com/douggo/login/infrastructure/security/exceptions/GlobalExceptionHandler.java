package com.douggo.login.infrastructure.security.exceptions;

import com.douggo.login.domain.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordNotHashedProperlyException.class)
    public ResponseEntity<ApiErrorResponse> handlePasswordNotHashedProperlyException(PasswordNotHashedProperlyException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(PasswordCreateDateNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handlePasswordCreateDateNotInformedException(PasswordCreateDateNotInformedException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(ScopeNameNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleScopeNameNotInformedException(ScopeNameNotInformedException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(ScopeIdNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleScopeIdNotInformedException(ScopeIdNotInformedException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(UserEmailNotInformedProperlyException.class)
    public ResponseEntity<ApiErrorResponse> handleUserEmailNotInformedProperlyException(UserEmailNotInformedProperlyException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(UserDateOfBirthNotInformedProperlyException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDateOfBirthNotInformedProperlyException(UserDateOfBirthNotInformedProperlyException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(UserNameNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNameNotInformedException(UserNameNotInformedException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(TokenAuthorizationException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenAuthorizationException(TokenAuthorizationException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiErrorResponse> handleIOException(IOException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        exception.getMessage()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonProcessingException(JsonProcessingException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        exception.getMessage()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiErrorResponse> handleServletException(ServletException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        exception.getMessage()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFoundException(DataNotFoundException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalAccessException(IllegalAccessException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ObjectIsNullException.class)
    public ResponseEntity<ApiErrorResponse> handleObjectIsNullException(ObjectIsNullException exception) {
        return new ResponseEntity<>(
                ApiErrorResponse.of(
                        exception.getClass().getSimpleName(),
                        exception.getStatusCode(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getStatusCode())
        );
    }

}
