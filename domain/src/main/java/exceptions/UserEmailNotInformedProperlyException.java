package exceptions;

public class UserEmailNotInformedProperlyException extends RuntimeException {

    private final int statusCode;

    public UserEmailNotInformedProperlyException(String message) {
        super(message);
        this.statusCode = 422; // UNPROCESSABLE ENTITY
    }

    public int getStatusCode() {
      return statusCode;
    }
}
