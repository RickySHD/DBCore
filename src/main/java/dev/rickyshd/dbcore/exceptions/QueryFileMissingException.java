package dev.rickyshd.dbcore.exceptions;

public class QueryFileMissingException extends RuntimeException {
    public QueryFileMissingException(String message) {
        super(message);
    }

    public QueryFileMissingException() {
    }

    public QueryFileMissingException(Throwable cause) {
        super(cause);
    }
}
