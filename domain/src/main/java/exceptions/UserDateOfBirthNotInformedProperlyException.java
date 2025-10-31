package exceptions;

public class UserDateOfBirthNotInformedProperlyException extends RuntimeException {

    private final int statusCode;

    public UserDateOfBirthNotInformedProperlyException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
