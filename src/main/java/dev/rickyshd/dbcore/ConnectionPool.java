package dev.rickyshd.dbcore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.rickyshd.dbcore.exceptions.DatabaseAccessException;

import java.sql.SQLException;

public class ConnectionPool implements ConnectionProvider {
    private final HikariDataSource source;
    private final boolean useTransactions;

    static final int DEFAULT_POOL_SIZE = 10;

    ConnectionPool(HikariConfig config, boolean useTransactions) {
        config.setAutoCommit(!useTransactions);

        this.source = new HikariDataSource(config);
        this.useTransactions = useTransactions;
    }

    public void close() {
        source.close();
    }

    @Override
    public Connection getConnection() {
        try {
            return Connection.connection(source.getConnection());
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public Connection getTransactionalConnection() {
        try {
            return Connection.transactional(source.getConnection());
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }
}
