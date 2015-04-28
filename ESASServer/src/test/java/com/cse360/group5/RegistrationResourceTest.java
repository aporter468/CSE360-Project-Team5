package com.cse360.group5;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

public class RegistrationResourceTest {

    private String uri = "http://localhost:3888/register";
    private ClientResource clientResource;
    private JSONObject jsonObject;

    @Before
    public void setup() {
        clientResource = new ClientResource(uri);
        jsonObject = new JSONObject();
    }

    @Test
    public void noInputTest() {
        boolean failed = false;
        try {
            clientResource.post(new JsonRepresentation(jsonObject));
        } catch (ResourceException e) {
            failed = true;
        }
        assert(failed);
    }

    @Test
    public void registerProviderNoPhone() throws JSONException, IOException {
        jsonObject.put("firstname", "test0");
        jsonObject.put("lastname", "test0");
        jsonObject.put("email", "test0@email.com");
        jsonObject.put("password", "password");
        Representation representation = clientResource.post(new JsonRepresentation(jsonObject), MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(representation.getText());
        assert(clientResource.getStatus().isSuccess());
        assert(jsonObject.get("message").equals("Provider successfully registered"));
    }

    @Test
    public void registerProviderPhone() throws JSONException, IOException {
        jsonObject.put("firstname", "test1");
        jsonObject.put("lastname", "test1");
        jsonObject.put("email", "test1@email.com");
        jsonObject.put("password", "password");
        jsonObject.put("phone", 12);
        Representation representation = clientResource.post(new JsonRepresentation(jsonObject), MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(representation.getText());
        assert(clientResource.getStatus().isSuccess());
        assert(jsonObject.get("message").equals("Provider successfully registered"));
    }

    @Test
    public void registerPatientNoPhone() throws JSONException, IOException {
        jsonObject.put("firstname", "test2");
        jsonObject.put("lastname", "test2");
        jsonObject.put("email", "test2@email.com");
        jsonObject.put("password", "password");
        jsonObject.put("providerid", 1);
        Representation representation = clientResource.post(new JsonRepresentation(jsonObject), MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(representation.getText());
        assert(clientResource.getStatus().isSuccess());
        assert(jsonObject.get("message").equals("Patient successfully registered"));
    }

    @Test
    public void registerPatientPhone() throws JSONException, IOException {
        jsonObject.put("firstname", "test3");
        jsonObject.put("lastname", "test3");
        jsonObject.put("email", "test3@email.com");
        jsonObject.put("password", "password");
        jsonObject.put("providerid", 2);
        Representation representation = clientResource.post(new JsonRepresentation(jsonObject), MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(representation.getText());
        assert(clientResource.getStatus().isSuccess());
        assert(jsonObject.get("message").equals("Patient successfully registered"));
    }
}
