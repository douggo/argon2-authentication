package security.exceptions;

public class ApiErrorResponse {

    private final String exceptionName;
    private final int status;
    private final String message;

    public ApiErrorResponse(String exceptionName, int status, String message) {
        this.exceptionName = exceptionName;
        this.status = status;
        this.message = message;
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
