package security.exceptions;

public class DataNotFoundException extends RuntimeException {

    private final int statusCode;

    public DataNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
