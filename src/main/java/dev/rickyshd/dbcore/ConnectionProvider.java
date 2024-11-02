package dev.rickyshd.dbcore;

public interface ConnectionProvider {
    Connection getConnection();

    Connection getTransactionalConnection();
}
