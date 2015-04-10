package com.cse360.group5.database;

import com.cse360.group5.surveys.SurveyResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyConnector extends ESASConnector {
    public ArrayList<SurveyResult> getPatientSurveys(int patientid) {
        final String query = "SELECT pain, drowsiness, nausea, appetite, shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp FROM surveys WHERE patientid = ? ORDER BY timestamp DESC";
        ArrayList<SurveyResult> surveys = new ArrayList<SurveyResult>();
        Connection connection = getConnection();

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientid);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            while (resultSet.next()) {
                int pain = resultSet.getInt("pain");
                int drowsiness = resultSet.getInt("drowsiness");
                int nausea = resultSet.getInt("nausea");
                int appetite = resultSet.getInt("appetite");
                int shortnessofbreath = resultSet.getInt("shortnessofbreath");
                int depression = resultSet.getInt("depression");
                int anxiety = resultSet.getInt("anxiety");
                int wellbeing = resultSet.getInt("wellbeing");
                String comments = resultSet.getString("comments");
                int timestamp = resultSet.getInt("timestamp");

                SurveyResult surveyResult = new SurveyResult(patientid, pain, drowsiness, nausea,
                        appetite, shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp);
                surveys.add(surveyResult);
            }

        } catch (SQLException e) {
            // TODO: When does this occur?
            throw new IllegalArgumentException("TBD", e);
        } finally {
            releaseConnection(connection);
        }

        return surveys;
    }

    public void submitSurvey(int patientid, int pain, int drowsiness, int nausea, int appetite, int shortnessofbreath, int depression, int anxiety, int wellbeing, String comments, int timestamp) {
        final String query = "INSERT INTO surveys (patientid, pain, drowsiness, nausea, appetite, shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();

        try {
            // Set up sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientid);
            pstat.setInt(2, pain);
            pstat.setInt(3, drowsiness);
            pstat.setInt(4, nausea);
            pstat.setInt(5, appetite);
            pstat.setInt(6, shortnessofbreath);
            pstat.setInt(7, depression);
            pstat.setInt(8, anxiety);
            pstat.setInt(9, wellbeing);
            pstat.setString(10, comments);
            pstat.setInt(11, timestamp);

            // Query and obtain the results
            boolean success = pstat.execute();
            if (!success) {
                // TODO: Flesh out message
                throw new SQLException("Insertion failed");
            }
        } catch (SQLException e) {
            // TODO: When does this occur?
            throw new IllegalArgumentException("TBD", e);
        } finally {
            releaseConnection(connection);
        }
    }
}
