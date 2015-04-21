package com.porter.esas;
import java.util.ArrayList;
/**
 * Created by Alex on 4/20/15.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private int ID;
    private ArrayList<Survey> surveys;
    public Patient(String firstName, String lastName, int ID)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
    }
    public void setSurveys(ArrayList<Survey> surveys)
    {
        this.surveys = surveys;
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
}
