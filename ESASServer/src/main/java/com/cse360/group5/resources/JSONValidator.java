package com.cse360.group5.resources;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONValidator {
    public static final String[] requiredPatientFields = {"firstname", "lastname", "email", "password", "providerid"};
    public static final String[] requiredProviderFields = {"firstname", "lastname", "email", "password"};
    public static final String[] requiredSurveyFields = {"pain", "drowsiness", "nausea", "appetite", "shortnessofbreath", "depression", "anxiety", "wellbeing"};
    public static final String optionalPhone = "phone";

    /**
     * Checks if the jsonRequest request contains the required patient fields.
     *
     * @param jsonRequest
     * @return validity
     */
    public static boolean validPatientRegistrationRequest(String jsonRequest) {
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);

            for (String field : requiredPatientFields) {
                if (!jsonObject.has(field)) {
                    return false;
                }
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Checks if the jsonRequest request contains the valid provider fields
     *
     * @param jsonRequest
     * @return validity
     */
    public static boolean validProviderRegistrationRequest(String jsonRequest) {
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);

            for (String field : requiredProviderFields) {
                if (!jsonObject.has(field)) {
                    return false;
                }
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Checks if the jsonRequest has the patientid field
     *
     * @param jsonRequest
     * @return validity
     */
    public static boolean validPatientInformationRequest(String jsonRequest) {
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);

            if (!jsonObject.has("patientid")) {
                return false;
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Checks if the jsonRequest has the required survey fields
     *
     * @param jsonRequest
     * @return
     */
    public static boolean validSurveySubmissionRequest(String jsonRequest) {
        try {
            JSONObject jsonObject = new JSONObject(jsonRequest);

            for (String field : requiredSurveyFields) {
                if (!jsonObject.has(field)) {
                    return false;
                }
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
