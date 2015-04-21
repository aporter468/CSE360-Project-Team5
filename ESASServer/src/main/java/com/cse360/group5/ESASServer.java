package com.cse360.group5;

import com.cse360.group5.authentication.AuthenticationVerifier;
import com.cse360.group5.resources.*;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

/**
 * The main entry point of ESASServer and the definition and routing of the Restlet server.
 */
public class ESASServer extends Application {

    public static void main(String[] args) throws Exception {

        // Define the http server component
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 3888);

        // Connect this application to the server
        component.getDefaultHost().attach(new ESASServer());

        // Start the server
        component.start();
    }

    /**
     * Define the routing from url to ServerResource associated with this component.
     *
     * @return
     */
    @Override
    public Restlet createInboundRoot() {
        Router baseRouter = new Router(getContext());

        Router privateRouter = new Router(getContext());
        privateRouter.attach("/surveys", SurveyResource.class);
        privateRouter.attach("/surveys/{patientid}", SurveyResource.class);
        privateRouter.attach("/patients", PatientResource.class);
        privateRouter.attach("/patients/{patientid}", PatientResource.class);
        privateRouter.attach("/providers", ProviderResource.class);

        // Define the authenticator using AuthenticationVerifier and HTTP_BASIC authentication
        ChallengeAuthenticator challengeAuthenticator = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "Edmonton Symptom Assessment System Data");
        challengeAuthenticator.setVerifier(new AuthenticationVerifier());
        challengeAuthenticator.setNext(privateRouter);

        // Define the public resources
        baseRouter.attach("/register", RegistrationResource.class);

        // Attach the private resource routing guarded by challengeAuthenticator
        baseRouter.attach("/v1", challengeAuthenticator);

        return baseRouter;
    }
}
