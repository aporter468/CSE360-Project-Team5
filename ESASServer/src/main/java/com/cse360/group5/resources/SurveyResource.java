package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.database.SurveyConnector;
import com.cse360.group5.surveys.SurveyResult;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Date;

public class SurveyResource extends ServerResource {

    @Get
    public String getPatientSurveys() {
        SurveyConnector surveyConnector = new SurveyConnector();
        ArrayList<SurveyResult> surveyResults;

        // Retrieve the patient id that we are submitting the survey for
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            PatientUser patientUser = (PatientUser) user;
            int patientid = Integer.valueOf(patientUser.getIdentifier());
            surveyResults = surveyConnector.getPatientSurveys(patientid);
        } else if (user instanceof ProviderUser) {
            ProviderUser providerUser = (ProviderUser) user;

            // Retrieve the patientid in the URL
            Integer patientid;
            try {
                Object obj = getRequest().getAttributes().get("patientid");
                if (obj == null) {
                    patientid = null;
                } else {
                    String patientidStr = obj.toString();
                    patientid = Integer.valueOf(patientidStr);
                }
            } catch (ClassCastException e) {
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                return "Patient ID in url is not of type Integer";
            }

            if (patientid != null) {
                InformationConnector informationConnector = new InformationConnector();
                PatientUser patientUser = informationConnector.getPatientUser(patientid);
                if (patientUser.getProviderId() == Integer.valueOf(providerUser.getIdentifier())) {
                    surveyResults = surveyConnector.getPatientSurveys(patientid);
                } else {
                    getResponse().setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
                    return "The requested patient is not one of your patients";
                }
            } else {
                surveyResults = surveyConnector.getTopSurveys(Integer.valueOf(providerUser.getIdentifier()));
            }
        } else {
            throw new RuntimeException("User is not instance of either type. Should not happen");
        }

        JSONObject jsonResponse = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray();
            for (SurveyResult survey : surveyResults) {
                jsonArray.put(survey.toJSON());
            }

            jsonResponse.put("surveys", jsonArray);
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

            String comments = "";
            if (jsonObject.has("comment")) {
                comments = jsonObject.getString("comments");
            }

            long timestamp = (new Date()).getTime();

            surveyConnector.submitSurvey(patientid, pain, drowsiness, nausea, appetite,
                    shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp);
        } catch (JSONException e) {
            throw new RuntimeException("Failed to retrieve json variable");
        }

        return "Submitted Successfully";
    }
}
