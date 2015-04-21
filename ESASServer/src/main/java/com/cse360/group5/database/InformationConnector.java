package com.cse360.group5.database;

import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InformationConnector extends ESASConnector {
    public PatientUser getPatientUser(int patientid) {
        String query = "SELECT firstname, lastname, email, phone, providerid FROM patients WHERE patientid = ?";
        Connection connection = getConnection();
        PatientUser patientUser = null;

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientid);

            ResultSet resultSet = pstat.executeQuery();

            boolean hasRow = resultSet.next();
            if (hasRow) {
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                int providerId = resultSet.getInt("providerid");

                patientUser = new PatientUser(patientid, firstName, lastName, email, phone, providerId);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return patientUser;
    }

    public ProviderUser getProviderUser(int providerid) {
        String query = "SELECT firstname, lastname, email, phone FROM providers WHERE providerid = ?";
        Connection connection = getConnection();
        ProviderUser providerUser = null;

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, providerid);

            ResultSet resultSet = pstat.executeQuery();

            boolean hasRow = resultSet.next();
            if (hasRow) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phone");

                providerUser = new ProviderUser(providerid, firstName, lastName, email, phone);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return providerUser;
    }

    public ArrayList<PatientUser> getProvidersPatients(int providerid) {
        String query = "SELECT patientid, firstname, lastname, email, phone FROM patients WHERE providerid = ?";
        Connection connection = getConnection();
        ArrayList<PatientUser> patients = new ArrayList<PatientUser>();

        try {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, providerid);

            ResultSet resultSet = pstat.executeQuery();


            while (resultSet.next()) {
                int patientid = resultSet.getInt("patientid");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                PatientUser patientUser = new PatientUser(patientid, firstName, lastName, email, phone, providerid);
                patients.add(patientUser);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            releaseConnection(connection);
        }

        return patients;
    }
}
