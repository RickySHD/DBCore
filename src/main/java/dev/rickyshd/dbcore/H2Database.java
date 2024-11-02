package dev.rickyshd.dbcore;

import com.zaxxer.hikari.HikariConfig;
import dev.rickyshd.dbcore.exceptions.DatabaseAccessException;
import dev.rickyshd.dbcore.exceptions.DatabaseDriverNotFoundException;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.SQLException;

class H2Database extends Database {
    private static int DBID = 0;
    private final JdbcDataSource dataSource;

    H2Database() {
        super(null);
        this.dataSource = getDataSource(String.format("jdbc:h2:mem:db%d;DATABASE_TO_UPPER=false;mode=MySQL", DBID++));
    }

    H2Database(String filename) {
        super(filename);
        this.dataSource = getDataSource(String.format("jdbc:h2:file:%s;DATABASE_TO_UPPER=false;mode=MySQL", filename));
    }

    @Override
    void loadDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseDriverNotFoundException("JDBC driver '%s' not found.");
        }
    }

    @Override
    public void close() {

    }

    @Override
    public Connection getConnection() {
        try {
            return Connection.connection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public Connection getTransactionalConnection() {
        try {
            return Connection.transactional(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public ConnectionPool getConnectionPool(boolean useTransaction, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSource.getURL());
        config.setUsername("sa");
        config.setPassword("sa");
        config.setMaximumPoolSize(poolSize);

        return new ConnectionPool(config, useTransaction);
    }

    private JdbcDataSource getDataSource(String url) {
        JdbcDataSource source = new JdbcDataSource();
        source.setUrl(url);
        source.setUser("sa");
        source.setPassword("sa");
        return source;
    }
}
