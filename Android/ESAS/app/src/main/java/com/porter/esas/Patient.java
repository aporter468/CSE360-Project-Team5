package com.porter.esas;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Created by Alex on 4/20/15.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int ID;
    private ArrayList<Survey> surveys;
    public Patient(JSONObject jsoninfo)
    {
        try {
            firstName = jsoninfo.get("firstname").toString();
            lastName = jsoninfo.get("lastname").toString();
            ID = Integer.parseInt(jsoninfo.get("patientid").toString());
            phone = jsoninfo.get("phone").toString();
            email = jsoninfo.get("email").toString();
        }
        catch(JSONException e){}

    }
    public void setupSurveys()
    {}

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public int getID() {
        return ID;
    }


    public ArrayList<Survey> getSurveys() {
        return surveys;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }


}
