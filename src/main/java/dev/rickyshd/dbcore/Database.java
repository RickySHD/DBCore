package dev.rickyshd.dbcore;

import org.jetbrains.annotations.Nullable;

import static dev.rickyshd.dbcore.ConnectionPool.DEFAULT_POOL_SIZE;

public abstract class Database implements ConnectionProvider {
    @Nullable private final String filename;

    protected Database(@Nullable String filename) {
        this.filename = filename;
    }

    public static Database h2() {
        return new H2Database();
    }

    public static Database h2(String path) {
        return new H2Database(path);
    }

    public String filename() {
        return filename;
    }

    abstract public void close();

    public ConnectionPool getConnectionPool() {
        return getConnectionPool(false, DEFAULT_POOL_SIZE);
    }

    abstract public ConnectionPool getConnectionPool(boolean useTransaction, int poolSize);
}
