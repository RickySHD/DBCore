package dev.rickyshd.dbcore;

import dev.rickyshd.dbcore.exceptions.ConnectionClosedException;
import dev.rickyshd.dbcore.exceptions.DatabaseAccessException;

import java.sql.Connection;
import java.sql.SQLException;

class TransactionalConnection extends SimpleConnection {
    TransactionalConnection(Connection connection) {
        super(connection);

        try {
            if (connection.isClosed()) throw new ConnectionClosedException();

            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public void commit() {
        try {
            if (connection.isClosed()) throw new ConnectionClosedException();

            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            if (connection.isClosed()) throw new ConnectionClosedException();

            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }
}
