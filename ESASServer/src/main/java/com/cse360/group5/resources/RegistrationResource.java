package com.cse360.group5.resources;

import com.cse360.group5.database.AuthenticationConnector;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Registers a patient or provider. Takes in a json request with required patient or provider fields and
 * adds a row to the database for the user.
 */
public class RegistrationResource extends ServerResource {

    /**
     * Takes in a json request, validates its contents, and registers the patient or provider.
     *
     * @param value
     * @return
     */
    @Post("json")
    public String register(String value) {
        // Initialize authentication connection
        AuthenticationConnector authenticationConnector = new AuthenticationConnector();

        if (JSONValidator.validPatientRegistrationRequest(value)) {
            try {
                // Parse the value into json
                JSONObject jsonObject = new JSONObject(value);

                // Retrieve required fields
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");
                int providerid = jsonObject.getInt("providerid");

                // Check for optional phone parameter and register the patient
                if (jsonObject.has("phone")) {
                    int phone = jsonObject.getInt("phone");
                    authenticationConnector.registerPatient(firstname, lastname, email, password, phone, providerid);
                } else {
                    authenticationConnector.registerPatient(firstname, lastname, email, password, 0, providerid);
                }

                // Return success
                return "Patient Registration Successful";
            } catch (JSONException e) {
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                return "";
            }
        } else if (JSONValidator.validProviderRegistrationRequest(value)) {
            try {
                // Parse the value into json
                JSONObject jsonObject = new JSONObject(value);

                // Retrieve required fields
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");

                // Check for optional phone parameter and register the patient
                if (jsonObject.has("phone")) {
                    int phone = jsonObject.getInt("phone");
                    authenticationConnector.registerProvider(firstname, lastname, email, password, phone);
                } else {
                    authenticationConnector.registerProvider(firstname, lastname, email, password, 0);
                }

                // Return success
                return "Provider Registration Successful";
            } catch (JSONException e) {
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                return "";
            }
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return "";
        }
    }




}
