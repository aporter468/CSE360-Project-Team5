package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class ProviderResource extends BaseResource {

    /**
     * Responds to requests to "/providers". If the user requesting information
     * is a patient we return the patient's provider's information and if the
     * user is a provider we return their information.
     *
     * @param jsonRequest
     * @return
     */
    @Get
    public Representation getProviderInformation(String jsonRequest) {
        InformationConnector informationConnector = new InformationConnector();
        ProviderUser providerUser;

        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof PatientUser) {
            PatientUser patientUser = (PatientUser) user;

            // Retrieve the patient's provider
            int providerId = patientUser.getProviderId();
            providerUser = informationConnector.getProviderUser(providerId);
        } else if (user instanceof ProviderUser) {
            providerUser = (ProviderUser) user;
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
            return messageRepresentation("User is not a provider or patient");
        }

        JSONObject jsonResponse;
        try {
            jsonResponse = providerUser.toJSON();
        } catch (JSONException e) {
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return messageRepresentation("Error creating JSON of provider");
        }

        return new JsonRepresentation(jsonResponse);
    }
}
