package dev.rickyshd.dbcore.exceptions;

public class ConnectionClosedException extends RuntimeException {
    public ConnectionClosedException() {
    }

    public ConnectionClosedException(String message) {
        super(message);
    }

    public ConnectionClosedException(Throwable cause) {
        super(cause);
    }
}
