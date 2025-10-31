package exceptions;

public class PasswordCreateDateNotInformedException extends RuntimeException {

    private final int statusCode;

    public PasswordCreateDateNotInformedException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
