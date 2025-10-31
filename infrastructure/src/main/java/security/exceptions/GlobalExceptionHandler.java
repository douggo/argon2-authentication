package security.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.*;
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
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(PasswordCreateDateNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handlePasswordCreateDateNotInformedException(PasswordCreateDateNotInformedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(ScopeNameNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleScopeNameNotInformedException(ScopeNameNotInformedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(ScopeIdNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleScopeIdNotInformedException(ScopeIdNotInformedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(UserEmailNotInformedProperlyException.class)
    public ResponseEntity<ApiErrorResponse> handleUserEmailNotInformedProperlyException(UserEmailNotInformedProperlyException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(UserDateOfBirthNotInformedProperlyException.class)
    public ResponseEntity<ApiErrorResponse> handleUserDateOfBirthNotInformedProperlyException(UserDateOfBirthNotInformedProperlyException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(UserNameNotInformedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNameNotInformedException(UserNameNotInformedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(TokenAuthorizationException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenAuthorizationException(TokenAuthorizationException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiErrorResponse> handleIOException(IOException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                422,
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(422));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonProcessingException(JsonProcessingException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                422,
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(422));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiErrorResponse> handleServletException(ServletException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                422,
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(422));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFoundException(DataNotFoundException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalAccessException(IllegalAccessException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                400,
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(ObjectIsNullException.class)
    public ResponseEntity<ApiErrorResponse> handleObjectIsNullException(ObjectIsNullException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }

}
