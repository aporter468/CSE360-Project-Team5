package com.cse360.group5.database;

import java.sql.*;

/**
 * Opens and releases connections to the sqlite database containing ESAS records.
 */
public class ESASConnector {
    private final String URL = "jdbc:sqlite:esasrecords.db";
    private final String DRIVER = "org.sqlite.JDBC";

    /**
     * Opens and returns a new Connection object to the database.
     *
     * @return Database Connection
     */
    protected Connection getConnection() {
        Connection connection;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find sqlite jdbc driver.", e);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Cannot find database at given path.", e);
        }
        return connection;
    }

    /**
     * Cleans up the Connection object to the database.
     *
     * @param connection
     */
    protected void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Could not release database connection.", e);
            }
        }
    }
}
