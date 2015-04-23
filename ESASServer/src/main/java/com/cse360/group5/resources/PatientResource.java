package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;

public class PatientResource extends ServerResource {

    @Get
    public String getPatientInformation() {
        InformationConnector informationConnector = new InformationConnector();
        PatientUser patientUser;

        // Retrieve the patient we are getting
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            patientUser = (PatientUser) user;
        } else if (user instanceof ProviderUser) {
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
                patientUser = informationConnector.getPatientUser(patientid);
            } else {
                ProviderUser providerUser = (ProviderUser) user;
                ArrayList<PatientUser> patients = informationConnector.getProvidersPatients(Integer.valueOf(providerUser.getIdentifier()));
                return patientsToJSON(patients).toString();
            }
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            return "User is not a provider or patient.";
        }

        return patientToJSON(patientUser).toString();
    }

    private JSONObject patientToJSON(PatientUser patientUser) {
        // Populate the patient information jsonResponse
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("patientid", Integer.valueOf(patientUser.getIdentifier()));
            jsonResponse.put("firstname", patientUser.getFirstName());
            jsonResponse.put("lastname", patientUser.getLastName());
            jsonResponse.put("email", patientUser.getEmail());
            jsonResponse.put("phone", patientUser.getPhone());
            jsonResponse.put("providerid", patientUser.getProviderId());
        } catch (JSONException e) {
            throw new RuntimeException("Could not add field to patient json");
        }

        return jsonResponse;
    }

    private JSONObject patientsToJSON(ArrayList<PatientUser> patientUsers) {
        // Populate the patient information jsonResponse
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for (PatientUser patientUser: patientUsers) {
                JSONObject patientJSON = patientToJSON(patientUser);
                jsonArray.put(patientJSON);
            }
            jsonResponse.put("patients", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException("Could not add field to patient json");
        }

        return jsonResponse;
    }
}
