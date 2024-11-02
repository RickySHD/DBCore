package dev.rickyshd.dbcore.exceptions;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException() {
    }

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(Throwable cause) {
        super(cause);
    }
}
