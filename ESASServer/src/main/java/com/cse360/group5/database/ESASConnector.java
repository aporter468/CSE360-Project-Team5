package com.cse360.group5.database;

import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;

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

    protected PatientUser getPatientUser(int patientId) {
        String query = "SELECT firstname, lastname, email, phone, providerid FROM patients WHERE patientid = ?";
        Connection connection = getConnection();
        PatientUser patientUser = null;

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientId);

            ResultSet resultSet = pstat.executeQuery();

            boolean hasRow = resultSet.next();
            if (hasRow) {
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String email = resultSet.getString(3);
                int phone = resultSet.getInt(4);
                int providerId = resultSet.getInt(5);

                patientUser = new PatientUser(patientId, firstName, lastName, email, phone, providerId);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return patientUser;
    }

    protected ProviderUser getProviderUser(int providerid) {
        String query = "SELECT firstname, lastname, email, phone FROM providers WHERE patientid = ?";
        Connection connection = getConnection();
        ProviderUser providerUser = null;

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, providerid);

            ResultSet resultSet = pstat.executeQuery();

            boolean hasRow = resultSet.next();
            if (hasRow) {
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String email = resultSet.getString(3);
                int phone = resultSet.getInt(4);

                providerUser = new ProviderUser(providerid, firstName, lastName, email, phone);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return providerUser;
    }
}
