package com.porter.esas;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by Alex on 3/19/15.
 */

public class Survey {
    public static String[] SURVEY_FIELDS = {"Pain","Drowsiness","Nausea","Appetite","Shortness of Breath","Depression","Anxiety","Wellbeing"};
  public static String[] SERVER_FIELD_NAMES = {"pain","drowsiness","nausea","appetite","shortnessofbreath","depression","anxiety","wellbeing"};
  public static int[] symptomthresholds = {5,5,4,4,3,2,2,4};
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
        if(!comments.equals(""))
             s+= "Comments: "+comments+"\n";
        return s;
    }
    public int getIsCritical()
    {
        int c = 0;
        for (int i =0; i<NUM_SURVEY_FIELDS; i++)
        {
            if(surveyValues[i]>symptomthresholds[i])
                c++;
        }
        if(c>5)
            return 2;
        else if (c>0)
            return 1;
        return 0;
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
