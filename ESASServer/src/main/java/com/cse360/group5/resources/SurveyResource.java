package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.database.SurveyConnector;
import com.cse360.group5.surveys.SurveyResult;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;

public class SurveyResource extends ServerResource {

    @Get
    public String getPatientSurveys(String jsonRequest) {
        SurveyConnector surveyConnector = new SurveyConnector();
        PatientUser patientUser;

        // Retrieve the patient id that we are submitting the survey for
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            patientUser = (PatientUser) user;
        } else if (user instanceof ProviderUser) {
            if (JSONValidator.validPatientInformationRequest(jsonRequest)) {
                InformationConnector informationConnector = new InformationConnector();
                try {
                    JSONObject jsonObject = new JSONObject(jsonRequest);
                    int patientid = jsonObject.getInt("patientid");

                    patientUser = informationConnector.getPatientUser(patientid);
                } catch (JSONException e) {
                    throw new RuntimeException("Failed to retrieve json field");
                }
            } else {
                throw new RuntimeException("Invalid json request");
            }
        } else {
            throw new RuntimeException("User is not instance of either type. Should not happen");
        }
        int patientid = Integer.valueOf(patientUser.getIdentifier());

        ArrayList<SurveyResult> surveyResults = surveyConnector.getPatientSurveys(patientid);

        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("surveys", surveyResults);
        } catch (JSONException e) {
            throw new RuntimeException("Failed to add survey results to json response");
        }

        return jsonResponse.toString();
    }

    @Post("json")
    public String submitSurvey(String jsonRequest) {
        SurveyConnector surveyConnector = new SurveyConnector();
        PatientUser patientUser;

        // Retrieve the patient id that we are submitting the survey for
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            patientUser = (PatientUser) user;
        } else if (user instanceof ProviderUser) {
            throw new RuntimeException("Providers cannot submit surveys");
        } else {
            throw new RuntimeException("User is not instance of either type. Should not happen");
        }
        int patientid = Integer.valueOf(patientUser.getIdentifier());

        // Validate the json request
        if (!JSONValidator.validSurveySubmissionRequest(jsonRequest)) {
            throw new RuntimeException("Invalid json request");
        }

        // Extract json survey data and submit into database
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);
            int pain = jsonObject.getInt("pain");
            int drowsiness = jsonObject.getInt("drowsiness");
            int nausea = jsonObject.getInt("nausea");
            int appetite = jsonObject.getInt("appetite");
            int shortnessofbreath = jsonObject.getInt("shortnessofbreath");
            int depression = jsonObject.getInt("depression");
            int anxiety = jsonObject.getInt("anxiety");
            int wellbeing = jsonObject.getInt("wellbeing");
            int timestamp = jsonObject.getInt("timestamp");

            String comments = "";
            if (jsonObject.has("comment")) {
                comments = jsonObject.getString("comments");
            }

            surveyConnector.submitSurvey(patientid, pain, drowsiness, nausea, appetite,
                    shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp);
        } catch (JSONException e) {
            throw new RuntimeException("Failed to retrieve json variable");
        }

        return "Submitted Successfully";
    }
}
