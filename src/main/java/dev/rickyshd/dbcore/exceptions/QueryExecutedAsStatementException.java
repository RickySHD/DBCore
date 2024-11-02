package dev.rickyshd.dbcore.exceptions;

public class QueryExecutedAsStatementException extends RuntimeException {
    public QueryExecutedAsStatementException() {
    }

    public QueryExecutedAsStatementException(String message) {
        super(message);
    }

    public QueryExecutedAsStatementException(Throwable cause) {
        super(cause);
    }
}
