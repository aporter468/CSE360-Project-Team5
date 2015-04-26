package com.cse360.group5.database;

import com.cse360.group5.authentication.PasswordEncryption;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.restlet.security.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manages all database requests involving authentication including logins and registrations.
 */
public class AuthenticationConnector extends BaseConnector {

    /**
     * Authenticates the user first trying as a provider, then as a patient.
     *
     * @param email
     * @param password
     * @return User Information
     */
    public User authenticate(String email, String password) {
        User user = authenticateProvider(email, password);
        if (user == null) {
            user = authenticatePatient(email, password);
        }
        return user;
    }

    /**
     * Authenticates the patient against the database.
     *
     * @param email
     * @param password
     * @return Patient Information
     */
    public PatientUser authenticatePatient(String email, String password) {
        String query = "SELECT password, patientid, firstname, lastname, phone, providerid FROM patients WHERE email = ?";
        Connection connection = getConnection();
        PatientUser patientUser = null;

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            boolean hasRow = resultSet.next();
            if (hasRow) {
                // Check that the password is correct
                boolean correctPassword = PasswordEncryption.check(password, resultSet.getString("password"));

                // If correct set patientUser to the patients information
                if (correctPassword) {
                    int patientId = resultSet.getInt("patientid");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    int phone = resultSet.getInt("phone");
                    int providerId = resultSet.getInt("providerid");

                    patientUser = new PatientUser(patientId, firstName, lastName, email, phone, providerId);
                }
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

    /**
     * Authenticates the provider against the database.
     *
     * @param email
     * @param password
     * @return Provider Information
     */
    public ProviderUser authenticateProvider(String email, String password) {
        String query = "SELECT password, providerid, firstname, lastname, phone FROM providers WHERE email = ?";
        Connection connection = getConnection();
        ProviderUser providerUser = null;

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            boolean hasRow = resultSet.next();
            if (hasRow) {
                // Check if correct password
                boolean correctPassword = PasswordEncryption.check(password, resultSet.getString("password"));

                // Sets providerUser information if correct password
                if (correctPassword) {
                    int providerid = resultSet.getInt("providerid");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    int phone = resultSet.getInt("phone");

                    providerUser = new ProviderUser(providerid, firstName, lastName, email, phone);
                }
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

    /**
     * Takes in required patient fields and adds a row to the patient table.
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param phone
     * @param providerid
     */
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

    /**
     * Takes in required provider fields and adds a row to the provider table.
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param phone
     */
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
