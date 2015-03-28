package com.cse360.group5.authentication;

import com.cse360.group5.database.AuthenticationConnector;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.security.SecretVerifier;
import org.restlet.security.User;

public class AuthenticationVerifier extends SecretVerifier {

    AuthenticationConnector authenticationConnector;
    User user;


    public AuthenticationVerifier() {
        this.authenticationConnector = new AuthenticationConnector();
    }

    @Override
    protected User createUser(String identifier, Request request, Response response) {
        return this.user;
    }

    @Override
    public int verify(String s, char[] chars) {
        this.user = authenticationConnector.authenticate(s, String.valueOf(chars));

        if (this.user != null) {
            return RESULT_VALID;
        }
        return RESULT_INVALID;
    }
}
