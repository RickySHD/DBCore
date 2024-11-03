package dev.rickyshd.dbcore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Connection extends AutoCloseable {

    static Connection connection(java.sql.Connection connection) {
        return new SimpleConnection(connection);
    }

    static Connection transactional(java.sql.Connection connection) {
        return new TransactionalConnection(connection);
    }

    void close();

    boolean isAlive();

    void commit();

    void rollback();

    List<DataRow> executeQuery(@NotNull String query, Object... arguments);

    int executeStatement(@NotNull String query, Object... arguments);

    int executeFile(@NotNull String path);
}
