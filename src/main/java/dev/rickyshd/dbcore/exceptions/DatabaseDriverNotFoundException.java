package dev.rickyshd.dbcore.exceptions;

public class DatabaseDriverNotFoundException extends RuntimeException {
    public DatabaseDriverNotFoundException(String message) {
        super(message);
    }
}
