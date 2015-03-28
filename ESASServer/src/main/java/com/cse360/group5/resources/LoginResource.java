package com.cse360.group5.resources;

import com.cse360.group5.database.AuthenticationConnector;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.security.User;

public class LoginResource extends ServerResource {

    @Post("json")
    public String login(String value) {
        AuthenticationConnector authenticationConnector = new AuthenticationConnector();

        if (validLoginInfo(value)) {
            try {
                // Parse json object
                JSONObject jsonObject = new JSONObject(value);

                // retrieve required fields
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");

                // login
                User user = authenticationConnector.authenticate(email, password);

                if (user == null) {
                    getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                    return "";
                } else {
                    return "Login successful";
                }
            } catch (JSONException e) {
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                return "";
            }
        } else {
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return "";
        }
    }

    boolean validLoginInfo(String json) {
        try {
            // Parse json object
            JSONObject jsonObject = new JSONObject(json);

            // check for required fields
            if (jsonObject.has("email") && jsonObject.has("password")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }
}
