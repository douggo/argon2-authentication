package exceptions;

public class ScopeIdNotInformedException extends RuntimeException {

    private final int statusCode;

    public ScopeIdNotInformedException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
        return statusCode;
    }
}
