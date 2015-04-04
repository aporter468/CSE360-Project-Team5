package com.cse360.group5.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SurveyResource extends ServerResource {

    @Get
    public String getSurveys(String value) {
        return value;
    }
}
