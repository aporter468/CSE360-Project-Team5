package com.cse360.group5.database;

import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InformationConnector extends ESASConnector {
    public PatientUser getPatientUser(String email) {
        String query = "SELECT patientid, firstname, lastname, email, phone, providerid FROM patients WHERE email = ?";
        Connection connection = getConnection();
        PatientUser patientUser = null;

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);

            ResultSet resultSet = pstat.executeQuery();

            boolean hasRow = resultSet.next();
            if (hasRow) {
                int patientId = resultSet.getInt("patientid");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                int providerId = resultSet.getInt("providerid");

                patientUser = new PatientUser(patientId, firstName, lastName, email, phone, providerId);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return patientUser;
    }

    public ProviderUser getProviderUser(int providerid) {
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
