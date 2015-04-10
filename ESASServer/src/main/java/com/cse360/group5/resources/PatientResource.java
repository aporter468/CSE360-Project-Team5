package com.cse360.group5.resources;

import com.cse360.group5.database.InformationConnector;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PatientResource extends ServerResource {

    @Get
    public String getPatientInformation(String jsonRequest) {
        InformationConnector informationConnector = new InformationConnector();


        return jsonRequest;
    }


}
