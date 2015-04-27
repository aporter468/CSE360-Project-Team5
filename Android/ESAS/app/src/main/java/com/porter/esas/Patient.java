package com.porter.esas;
import android.util.Log;

import org.json.JSONArray;
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
    MainActivity mainActivity;
    public Patient(JSONObject jsoninfo, MainActivity mainActivity)
    {
        this.mainActivity= mainActivity;
        if(jsoninfo!=null) {
            try {
                firstName = jsoninfo.get("firstname").toString();
                lastName = jsoninfo.get("lastname").toString();
                ID = Integer.parseInt(jsoninfo.get("patientid").toString());
                phone = jsoninfo.get("phone").toString();
                email = jsoninfo.get("email").toString();
            } catch (JSONException e) {
            }
        }

    }
    public void setupSurveys(String surveysJSONstring)
    {
        Log.e("mylog","surveys for : "+ID);
        surveys = new ArrayList<Survey>();
        if (surveysJSONstring.length() > 0)//send empty from register
        {
            try {
                JSONObject surveysJSON = new JSONObject(surveysJSONstring);
                JSONArray surveysArray = surveysJSON.getJSONArray("surveys");
                for (int i = 0; i < surveysArray.length(); i++) {
                    JSONObject survey = (JSONObject) surveysArray.get(i);
                    int[] surveyArray = new int[8];
                    for (int j = 0; j < 8; j++) {

                        surveyArray[j] = Integer.parseInt(survey.get(Survey.SERVER_FIELD_NAMES[j]).toString());
                    }
                    Survey newS = new Survey(surveyArray, "");
                    newS.setComments(survey.get("comments").toString());
                    Log.e("mylog","setup survey comments: "+survey.get("comments").toString());
                    newS.setDate(Long.parseLong(survey.get("timestamp").toString()), mainActivity);
                    surveys.add(newS);
                }

            } catch (JSONException e) {
            }
        }

    }

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
    public String getInfoString()
    {
        return "Name: "+firstName+" "+lastName+
                "\n Email: "+email+
                "\n Phone: "+phone;
    }

}
