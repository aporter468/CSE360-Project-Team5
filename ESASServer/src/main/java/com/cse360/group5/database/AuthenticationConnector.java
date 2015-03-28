package com.cse360.group5.database;

import com.cse360.group5.authentication.PasswordEncryption;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.restlet.security.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationConnector extends ESASConnector {

    public User authenticate(String email, String password) {
        User user = authenticateProvider(email, password);
        if (user == null) {
            user = authenticatePatient(email, password);
        }
        return user;
    }

    public PatientUser authenticatePatient(String email, String password) {
        String query = "SELECT patientid, firstname, lastname, phone, providerid FROM patients WHERE email = ? AND password = ?";
        Connection connection = getConnection();
        PatientUser patientUser = null;

        String encryptedPassword = PasswordEncryption.encrypt(password);

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            pstat.setString(2, encryptedPassword);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            boolean hasRow = resultSet.next();
            if (hasRow) {
                int patientId = resultSet.getInt("patientid");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                int providerId = resultSet.getInt("providerid");

                patientUser = new PatientUser(patientId, firstName, lastName, email, phone, providerId);
            }

            // Close the resources
            resultSet.close();
            pstat.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return patientUser;
    }

    public ProviderUser authenticateProvider(String email, String password) {
        String query = "SELECT providerid, firstname, lastname, phone FROM providers WHERE email = ? AND password = ?";
        Connection connection = getConnection();
        ProviderUser providerUser = null;

        String encryptedPassword = PasswordEncryption.encrypt(password);

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            pstat.setString(2, encryptedPassword);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            boolean hasRow = resultSet.next();
            if (hasRow) {
                int providerid = resultSet.getInt("providerid");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");

                providerUser = new ProviderUser(providerid, firstName, lastName, email, phone);
            }

            // Close the resources
            resultSet.close();
            pstat.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return providerUser;
    }

    public void registerPatient(String firstName, String lastName, String email, String password, int phone, int providerid) {
        String insertion = "INSERT INTO patients(firstname, lastname, email, password, phone, providerid) VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();

        String encryptedPassword = PasswordEncryption.encrypt(password);

        try {
            PreparedStatement pstat = connection.prepareStatement(insertion);
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, email);
            pstat.setString(4, encryptedPassword);
            pstat.setInt(5, phone);
            pstat.setInt(6, providerid);

            boolean success = pstat.execute();
            pstat.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public void registerProvider(String firstName, String lastName, String email, String password, int phone) {
        String insertion = "INSERT INTO providers(firstname, lastname, email, password, phone) VALUES(?, ?, ?, ?, ?)";
        Connection connection = getConnection();

        String encryptedPassword = PasswordEncryption.encrypt(password);

        try {
            PreparedStatement pstat = connection.prepareStatement(insertion);
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, email);
            pstat.setString(4, encryptedPassword);
            pstat.setInt(5, phone);

            boolean success = pstat.execute();
            pstat.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }
    }

}
