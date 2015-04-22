package com.porter.esas;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import android.util.Log;
/**
 * Created by Alex on 3/19/15.
 */

public class Survey {
    public static String[] SURVEY_FIELDS = {"Pain","Drowsiness","Nausea","Appetite","Shortness of Breath","Depression","Anxiety","Wellbeing"};
  public static String[] SERVER_FIELD_NAMES = {"pain","drowsiness","nausea","appetite","shortnessofbreath","depression","anxiety","wellbeing"};
  public static int NUM_SURVEY_FIELDS = 8;
    private int[] surveyValues;
    private String comments;
    private Calendar c;
    private String patientID;
    private Patient patient;
    public Survey(int[] surveyValues, String comments)
    {
        this.surveyValues= surveyValues;
        this.comments = comments;
       // this.date = date;
    }

    public int[] getSurveyValues() {
        return surveyValues;
    }

    public void setSurveyValues(int[] surveyValues) {
        this.surveyValues = surveyValues;
    }

    public String getComments() {
        return comments;
    }

    public void setDate(long timestamp, MainActivity activity)
    {
        Date d = new Date(timestamp );
        c = Calendar.getInstance();
        c.setTime(d);

        //Toast.makeText(activity, sdf.format(c), Toast.LENGTH_SHORT).show();
    }
    public Calendar getCalendar()
    {
        return c;
    }
    public String getDateText()
    {
        return c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR);
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getFullValueString()
    {
        String s = "";
        for(int i =0; i<NUM_SURVEY_FIELDS; i++)
        {
            s+=SURVEY_FIELDS[i]+": "+surveyValues[i]+"\n";

        }
        return s;
    }


    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }
    public String getPatientString()
    {
        return patient.getID()+": "+patient.getFirstName()+" "+patient.getLastName();
    }
    public Patient getPatient()
    {
        return patient;
    }
}
