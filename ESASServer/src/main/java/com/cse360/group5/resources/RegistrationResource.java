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
    private final String[] requiredPatientFields = {"firstname", "lastname", "email", "password", "providerid"};
    private final String[] requiredProviderFields = {"firstname", "lastname", "email", "password"};
    private final String optionalPhone = "phone";

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

        if (validPatient(value)) {
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
                if (jsonObject.has(optionalPhone)) {
                    int phone = jsonObject.getInt(optionalPhone);
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
        } else if (validProvider(value)) {
            try {
                // Parse the value into json
                JSONObject jsonObject = new JSONObject(value);

                // Retrieve required fields
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");

                // Check for optional phone parameter and register the patient
                if (jsonObject.has(optionalPhone)) {
                    int phone = jsonObject.getInt(optionalPhone);
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

    /**
     * Checks if the json request contains the required patient fields.
     *
     * @param json
     * @return validity
     */
    private boolean validPatient(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            boolean valid = true;
            for (String field : requiredPatientFields) {
                if (!jsonObject.has(field)) {
                    valid = false;
                    break;
                }
            }

            return valid;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Checks if the json request contains the valid provider fields
     *
     * @param json
     * @return validity
     */
    private boolean validProvider(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            boolean valid = true;
            for (String field : requiredProviderFields) {
                if (!jsonObject.has(field)) {
                    valid = false;
                    break;
                }
            }

            return valid;
        } catch (JSONException e) {
            return false;
        }
    }
}
