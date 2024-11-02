package dev.rickyshd.dbcore.exceptions;

public class QueryTimeoutException extends RuntimeException {
    public QueryTimeoutException() {
    }

    public QueryTimeoutException(String message) {
        super(message);
    }

    public QueryTimeoutException(Throwable cause) {
        super(cause);
    }
}
