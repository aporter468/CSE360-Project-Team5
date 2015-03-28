package com.cse360.group5;

import com.cse360.group5.authentication.AuthenticationVerifier;
import com.cse360.group5.resources.LoginResource;
import com.cse360.group5.resources.RegistrationResource;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

public class ESASServer extends Application {

    public static void main(String[] args) throws Exception {

        // Define the http server component
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 3888);

        // Connect the authenticate component to its server
        component.getDefaultHost().attach("/v1", new ESASServer());

        // Start the server
        component.start();
    }

    @Override
    public Restlet createInboundRoot() {
        ChallengeAuthenticator challengeAuthenticator = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "realm");
        challengeAuthenticator.setVerifier(new AuthenticationVerifier());

        Router router = new Router(getContext());
        router.attach("/login", LoginResource.class);
        router.attach("/register", RegistrationResource.class);

        challengeAuthenticator.setNext(router);

        //return challengeAuthenticator;
        return router;
    }
}
