package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import java.util.ArrayList;

public class PatientResource extends BaseResource {

    /**
     * Responds to requests to "/patients" and "/patients/{patientid}". If the user
     * requesting information is a patient it returns the users information in both
     * addresses. If the user is a provider it will return all of the providers
     * patients for "/patients" and a specific patients information for
     * "/patients/{patientid}".
     *
     * @return
     */
    @Get
    public Representation getPatientInformation() {
        InformationConnector informationConnector = new InformationConnector();
        PatientUser patientUser;
        JSONObject jsonResponse;

        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            patientUser = (PatientUser) user;

            // Transform the patients information to JSON
            try {
                jsonResponse = patientUser.toJSON();
            } catch (JSONException e) {
                getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                return messageRepresentation("Error creating JSON of patient");
            }

            return new JsonRepresentation(jsonResponse);
        } else if (user instanceof ProviderUser) {
            ProviderUser providerUser = (ProviderUser) user;
            int providerid = Integer.valueOf(providerUser.getIdentifier());

            // Retrieve the patientid in the URL if it exists
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

            // If specific patient return that patient, else return all patients
            if (patientid != null) {
                patientUser = informationConnector.getPatientUser(patientid);

                // Check that the patient requested is one of the Provider's patients
                if (patientUser.getProviderId() != providerid) {
                    getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN);
                    return messageRepresentation("Cannot request information for patients that are not yours");
                }

                // Transform the patients information to JSON
                try {
                    jsonResponse = patientUser.toJSON();
                } catch (JSONException e) {
                    getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                    return messageRepresentation("Error creating JSON of patient");
                }

                return new JsonRepresentation(jsonResponse);
            } else {
                ArrayList<PatientUser> patients = informationConnector.getProvidersPatients(providerid);
                try {
                    jsonResponse = patientsToJSON(patients);
                } catch (JSONException e) {
                    getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
                    return messageRepresentation("Error creating JSON of patients");
                }
                return new JsonRepresentation(jsonResponse);
            }
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            return messageRepresentation("User is not a provider or patient");
        }
    }

    /**
     * Creates a JSONObject containing {patients: [(each patient converted to json]}
     * from the ArrayList of PatientUser's.
     *
     * @param patientUsers An ArrayList of PatientUser's to convert to JSON
     * @return A JSONObject containing {patients: [(each patient converted to json]}
     * @throws JSONException
     */
    private JSONObject patientsToJSON(ArrayList<PatientUser> patientUsers) throws JSONException {
        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (PatientUser patientUser: patientUsers) {
            jsonArray.put(patientUser.toJSON());
        }
        jsonResponse.put("patients", jsonArray);
        return jsonResponse;
    }
}
