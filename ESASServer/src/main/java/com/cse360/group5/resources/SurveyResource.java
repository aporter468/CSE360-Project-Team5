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
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.ArrayList;
import java.util.Date;

public class SurveyResource extends BaseResource {

    /**
     * Responds to GET requests to "/surveys" and "/surveys/{patientid}". If the
     * user is a patient we return the patients surveys that have been submitted.
     * If the user is a provider and requests to "/surveys" we return the top
     * surveys their patients have submitted. If the request goes to "/surveys/{patientid}"
     * then the specific patient's surveys are returned if the patient is one
     * of the provider's patients.
     *
     * @return
     */
    @Get
    public Representation getPatientSurveys() {
        SurveyConnector surveyConnector = new SurveyConnector();
        ArrayList<SurveyResult> surveyResults;
        JSONObject jsonResults;

        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            PatientUser patientUser = (PatientUser) user;
            int patientid = Integer.valueOf(patientUser.getIdentifier());
            surveyResults = surveyConnector.getPatientSurveys(patientid);

            try {
                jsonResults = surveysToJSON(surveyResults);
            } catch (JSONException e) {
                getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                return messageRepresentation("Error creating JSON of surveys");
            }

            return new JsonRepresentation(jsonResults);
        } else if (user instanceof ProviderUser) {
            ProviderUser providerUser = (ProviderUser) user;
            int providerid = Integer.valueOf(providerUser.getIdentifier());

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
                return messageRepresentation("Patient ID in url is not of type: Integer");
            }

            // Check if the request is routed to "/surveys" or "/surveys/{patientid}"
            if (patientid != null) {
                // Retrieve the patient with the specific patient id
                InformationConnector informationConnector = new InformationConnector();
                PatientUser patientUser = informationConnector.getPatientUser(patientid);

                // Check that the patient is one of the provider's patients
                if (patientUser.getProviderId() != providerid) {
                    getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
                    return messageRepresentation("Cannot request information for patients that are not yours");
                }

                surveyResults = surveyConnector.getPatientSurveys(patientid);

                try {
                    jsonResults = surveysToJSON(surveyResults);
                } catch (JSONException e) {
                    getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                    return messageRepresentation("Error creating JSON of surveys");
                }

                return new JsonRepresentation(jsonResults);
            } else {
                // No specific patient: grab top surveys for provider
                surveyResults = surveyConnector.getTopSurveys(providerid);

                try {
                    jsonResults = surveysToJSON(surveyResults);
                } catch (JSONException e) {
                    getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                    return messageRepresentation("Error creating JSON of surveys");
                }

                return new JsonRepresentation(jsonResults);
            }
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            return messageRepresentation("User is not a provider or patient");
        }
    }

    /**
     * Creates a JSONObject containing {surveys: [(each survey converted to json]}
     * from the ArrayList of SurveyResult's.
     *
     * @param surveys An ArrayList of SurveyResult's to convert to JSON
     * @return A JSONObject containing {surveys: [(each survey converted to json]}
     * @throws JSONException
     */
    private JSONObject surveysToJSON(ArrayList<SurveyResult> surveys) throws JSONException {
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (SurveyResult survey: surveys) {
            jsonArray.put(survey.toJSON());
        }
        jsonResponse.put("surveys", jsonArray);
        return jsonResponse;
    }

    /**
     * Responds to POST requests to "/surveys". If the user submitting the survey
     * is a patient we retrieve necessary survey fields from their POST request
     * and add the survey to the database.
     *
     * @param jsonRequest
     * @return
     */
    @Post("json")
    public Representation submitSurvey(JSONObject jsonRequest) {
        SurveyConnector surveyConnector = new SurveyConnector();
        int patientid;

        // Retrieve the patient id that we are submitting the survey for
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            PatientUser patientUser = (PatientUser) user;
            patientid = Integer.valueOf(patientUser.getIdentifier());
        } else if (user instanceof ProviderUser) {
            getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
            return messageRepresentation("Providers cannot submit surveys");
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            return messageRepresentation("User is not a provider or patient");
        }

        // Extract json survey data and submit into database
        try {
            int pain = jsonRequest.getInt("pain");
            int drowsiness = jsonRequest.getInt("drowsiness");
            int nausea = jsonRequest.getInt("nausea");
            int appetite = jsonRequest.getInt("appetite");
            int shortnessofbreath = jsonRequest.getInt("shortnessofbreath");
            int depression = jsonRequest.getInt("depression");
            int anxiety = jsonRequest.getInt("anxiety");
            int wellbeing = jsonRequest.getInt("wellbeing");

            // Check for optional comments section
            String comments = "";
            if (jsonRequest.has("comment")) {
                comments = jsonRequest.getString("comments");
            }

            // Grab the server current timestamp
            long timestamp = (new Date()).getTime();

            surveyConnector.submitSurvey(patientid, pain, drowsiness, nausea, appetite,
                    shortnessofbreath, depression, anxiety, wellbeing, comments, timestamp);

            return messageRepresentation("Submitted survey successfully");
        } catch (JSONException e) {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return messageRepresentation("Required fields missing or invalid data types from JSON request");
        }
    }
}
