package com.cse360.group5.database;

import com.cse360.group5.surveys.SurveyResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyConnector extends ESASConnector {
    public List<SurveyResult> getPatientSurveys(int patientid) {
        final String query = "SELECT painindex, timestamp FROM surveys WHERE patientid = ? ORDER BY timestamp DESC";
        List<SurveyResult> surveys = new ArrayList<SurveyResult>();
        Connection connection = getConnection();

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientid);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            while (resultSet.next()) {
                int painindex = resultSet.getInt(1);
                int timestamp = resultSet.getInt(2);
                SurveyResult surveyResult = new SurveyResult(patientid, painindex, timestamp);
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

    public void submitSurvey(SurveyResult survey) {
        final String query = "INSERT INTO surveys VALUES(?, ?, ?)";
        Connection connection = getConnection();

        try {
            // Set up sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, survey.getPatientid());
            pstat.setInt(2, survey.getPainindex());
            pstat.setInt(3, survey.getTimestamp());

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
