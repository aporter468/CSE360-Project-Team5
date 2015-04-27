package com.cse360.group5.authentication;

import com.cse360.group5.database.AuthenticationConnector;
import org.restlet.Request;
import org.restlet.security.SecretVerifier;
import org.restlet.security.User;

public class AuthenticationVerifier extends SecretVerifier {

    /**
     * Verifies that the credentials constitute a legitimate User - either
     * patient or provider - with the database. Also sets this User inside
     * the request object for use down the pipeline.
     *
     * @param s
     * @param chars
     * @return
     */
    @Override
    public int verify(String s, char[] chars) {
        AuthenticationConnector authenticationConnector = new AuthenticationConnector();

        User user = authenticationConnector.authenticate(s, String.valueOf(chars));

        if (user != null) {
            // Add user information to the request
            Request request = Request.getCurrent();
            request.getAttributes().put("user", user);

            return RESULT_VALID;
        }
        return RESULT_INVALID;
    }
}
