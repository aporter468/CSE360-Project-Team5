package com.cse360.group5.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class SurveyResource extends ServerResource {

    @Get
    public String getPatientSurveys(String jsonRequest) {
        return jsonRequest;
    }

    @Post
    public String submitSurvey(String jsonRequest) {
        return jsonRequest;
    }
}
