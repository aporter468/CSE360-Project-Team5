import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ESASDatabaseConnection {
    private Connection connection = null;
    private final String DRIVER = "org.sqlite.JDBC";

    /*
    Opens connection to sqlite database at path.
     */
    public ESASDatabaseConnection(String path) {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find sqlite jdbc driver.", e);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Cannot find database at given path.", e);
        }
    }

    public List<SurveyResult> getPatientSurveys(int patientid) {
        final String query = "SELECT painindex, timestamp FROM surveys WHERE patientid = ? ORDER BY timestamp DESC";
        List<SurveyResult> surveys = new ArrayList<SurveyResult>();

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
        }

        return surveys;
    }

    public void submitSurvey(SurveyResult survey) {
        final String query = "INSERT INTO surveys VALUES(?, ?, ?)";

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
        }
    }

    public PatientInformation getPatientInformation(int patientId) {
        final String query = "SELECT patientid, providerid, name, email, phone FROM patients WHERE patientId = ?";
        PatientInformation patientInformation;

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, patientId);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            resultSet.next();
            int patientid = resultSet.getInt(1);
            int providerid = resultSet.getInt(2);
            String name = resultSet.getString(3);
            String email = resultSet.getString(4);
            int phone = resultSet.getInt(5);
            patientInformation = new PatientInformation(patientId, providerid, name, email, phone);

        } catch (SQLException e) {
            // TODO: When does this occur?
            throw new IllegalArgumentException("TBD", e);
        }

        return patientInformation;
    }

    public ProviderInformation getProviderInformation(int providerId) {
        final String query = "SELECT name, email, phone FROM providers WHERE providerId = ?";
        ProviderInformation providerInformation;

        try {
            // Set up the sql query
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setInt(1, providerId);

            // Query and obtain the results
            ResultSet resultSet = pstat.executeQuery();

            // Extract the results
            resultSet.next(); // get first and only row
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            int phone = resultSet.getInt(4);
            providerInformation = new ProviderInformation(providerId, name, email, phone);

        } catch (SQLException e) {
            // TODO: When does this occur?
            throw new IllegalArgumentException("", e);
        }

        return providerInformation;
    }
}