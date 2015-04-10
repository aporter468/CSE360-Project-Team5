package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PatientResource extends ServerResource {

    @Get("json")
    public String getPatientInformation(String jsonRequest) {
        InformationConnector informationConnector = new InformationConnector();
        PatientUser patientUser;

        // Retrieve the patient we are getting
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            patientUser = (PatientUser) user;
        } else if (user instanceof ProviderUser) {
            ProviderUser providerUser = (ProviderUser) user;
            if (JSONValidator.validPatientInformationRequest(jsonRequest)) {
                int patientid;
                try {
                    JSONObject jsonObject = new JSONObject(jsonRequest);

                    patientid = jsonObject.getInt("patientid");
                } catch (JSONException e) {
                    throw new RuntimeException("The patientid field was not of the correct type");
                }

                patientUser = informationConnector.getPatientUser(patientid);

                // Check that the patient is one of the provider's patients
                if (patientUser.getProviderId() != Integer.valueOf(providerUser.getIdentifier())) {
                    throw new RuntimeException("The requested patient is not one of your patients");
                }
            } else {
                throw new RuntimeException("Request is not json or does not contain patientid field");
            }
        } else {
            throw new RuntimeException("User is not instance of either type. Should not happen");
        }

        // Populate the patient information jsonResponse
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("firstname", patientUser.getFirstName());
            jsonResponse.put("lastname", patientUser.getLastName());
            jsonResponse.put("email", patientUser.getEmail());
            jsonResponse.put("phone", patientUser.getPhone());
            jsonResponse.put("providerid", patientUser.getProviderId());
        } catch (JSONException e) {
            throw new RuntimeException("Could not add field to patient json");
        }

        return jsonResponse.toString();
    }


}
