package exceptions;

public class PasswordNotHashedProperlyException extends RuntimeException {

    private final int statusCode;

    public PasswordNotHashedProperlyException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
