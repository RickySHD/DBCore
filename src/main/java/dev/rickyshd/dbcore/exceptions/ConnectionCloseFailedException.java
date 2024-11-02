package dev.rickyshd.dbcore.exceptions;

public class ConnectionCloseFailedException extends RuntimeException {
    public ConnectionCloseFailedException() {
    }

    public ConnectionCloseFailedException(String message) {
        super(message);
    }

    public ConnectionCloseFailedException(Throwable cause) {
        super(cause);
    }
}
