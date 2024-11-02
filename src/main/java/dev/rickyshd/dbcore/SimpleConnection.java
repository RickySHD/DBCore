package dev.rickyshd.dbcore;

import dev.rickyshd.dbcore.exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.util.*;

class SimpleConnection implements Connection {
    protected final java.sql.Connection connection;

    private static final int CONNECTION_VALID_TIMEOUT = 1;

    SimpleConnection(java.sql.Connection connection) {
        this.connection = connection;
    }

    public boolean isAlive() {
        try {
            return connection.isValid(CONNECTION_VALID_TIMEOUT);
        } catch (SQLException ignored) {
            return false;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionCloseFailedException(e);
        }
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public List<DataRow> executeQuery(@NotNull String query, Object... arguments) {
        try {
            if (connection.isClosed()) throw new ConnectionClosedException();

            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                // fill prepared statement
                int i = 1;
                for (Object arg : arguments) stmt.setObject(i++, arg);

                ResultSet resultSet = stmt.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();

                List<DataRow> result = new ArrayList<>();
                List<String> labels = new ArrayList<>();

                for (int j=1; j <= metaData.getColumnCount(); j++)
                    labels.add(metaData.getColumnLabel(j));

                while (resultSet.next()) {
                    LinkedHashMap<String, Object> m = new LinkedHashMap<>();
                    for (String label : labels) m.put(label, resultSet.getObject(label));

                    result.add(DataRow.of(m));
                }

                resultSet.close();
                stmt.close();
                return result;

            } catch (SQLTimeoutException e) {
                throw new QueryTimeoutException(e);
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public int executeStatement(@NotNull String query, Object... arguments) {
        try {
            if (connection.isClosed()) throw new ConnectionClosedException();

            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                // fill prepared statement
                int i = 1;
                for (Object arg : arguments) stmt.setObject(i++, arg);

                if (stmt.execute()) throw new QueryExecutedAsStatementException(stmt.toString());
                int updateCount = stmt.getUpdateCount();
                stmt.close();

                return updateCount;

            } catch (SQLTimeoutException e) {
                throw new QueryTimeoutException(e);
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public int executeFile(@NotNull String path) {
        File file = new File(path);
        String queries;

        try (InputStream in = new FileInputStream(file)) {
            queries = new String(in.readAllBytes());
        } catch (FileNotFoundException e) {
            throw new QueryFileMissingException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int res = 0;
        for (String query : queries.split(";"))
            res += executeStatement(query);

        return res;
    }
}
