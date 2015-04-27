package com.cse360.group5.resources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ServerResource;

public class BaseResource extends ServerResource {

    /**
     * Creates a JsonRepresentation of the message
     *
     * @param message The message to package in JSON
     * @return JSON packed message
     */
    public JsonRepresentation messageRepresentation(String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", message);
        } catch (JSONException e) {
            // TODO:
        }
        return new JsonRepresentation(jsonObject);
    }
}
