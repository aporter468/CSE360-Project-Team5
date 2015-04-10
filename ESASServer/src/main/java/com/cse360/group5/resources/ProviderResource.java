package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import com.cse360.group5.users.PatientUser;
import com.cse360.group5.users.ProviderUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProviderResource extends ServerResource{

    @Get
    public String getProviderInformation(String jsonRequest) {
        InformationConnector informationConnector = new InformationConnector();
        ProviderUser providerUser;

        // Retrieve the Provider we are getting information of depending on which user made the request
        Object user = this.getRequest().getAttributes().get("user");
        if (user instanceof ProviderUser) {
            providerUser = (ProviderUser) user;
        } else if (user instanceof PatientUser) {
            PatientUser patientUser = (PatientUser) user;

            // Retrieve the patient's provider
            int providerId = patientUser.getProviderId();
            providerUser = informationConnector.getProviderUser(providerId);
        } else {
            throw new RuntimeException("User is not instance of either type. Should not happen");
        }

        // Populate the provider information jsonResponse
        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse.put("identifier", providerUser.getIdentifier());
            jsonResponse.put("firstname", providerUser.getFirstName());
            jsonResponse.put("lastname", providerUser.getLastName());
            jsonResponse.put("email", providerUser.getEmail());
            jsonResponse.put("phone", providerUser.getPhone());
        } catch (JSONException e) {
            throw new RuntimeException("Could not add field to provider json");
        }

        return jsonResponse.toString();
    }
}
