package com.cse360.group5.resources;

import com.cse360.group5.database.AuthenticationConnector;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public class RegistrationResource extends BaseResource {

    /**
     * Responds to requests to "/register". Takes in a JSON request and depending
     * on the fields present either registers the user as a patient or provider.
     *
     * @param jsonRequest
     * @return
     */
    @Post("json")
    public Representation register(JSONObject jsonRequest) {
        AuthenticationConnector authenticationConnector = new AuthenticationConnector();

        try {
            // Retrieve required fields for registration
            String firstname = jsonRequest.getString("firstname");
            String lastname = jsonRequest.getString("lastname");
            String email = jsonRequest.getString("email");
            String password = jsonRequest.getString("password");

            // Check for optional phone parameter
            int phone = 0;
            if (jsonRequest.has("phone")) {
                phone = jsonRequest.getInt("phone");
            }

            if (jsonRequest.has("providerid")) {
                // Request is for patient registration
                int providerid = jsonRequest.getInt("providerid");
                authenticationConnector.registerPatient(firstname, lastname, email, password, phone, providerid);
                return messageRepresentation("Patient successfully registered");
            } else {
                // Request is for provider registration
                authenticationConnector.registerProvider(firstname, lastname, email, password, phone);
                return messageRepresentation("Provider successfully registered");
            }
        } catch (JSONException e) {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return messageRepresentation("Required fields missing or invalid data types from JSON request");
        }
    }
}
