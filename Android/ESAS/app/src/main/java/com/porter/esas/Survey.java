package com.porter.esas;
import java.util.Calendar;
/**
 * Created by Alex on 3/19/15.
 */

public class Survey {
    public static int numSurveyFields = 9;
    public static String[] SURVEY_FIELDS = {"Pain","Drowsiness","Nausea","Lack of Appetite","Shortness of Breath","No Depression","No Anxiety","Best Wellbeing"};
  public static String[] SERVER_FIELD_NAMES = {"pain","drowsiness","nausea","appetite","shortnessofbreath","depression","anxiety","wellbeing"};
    private int[] surveyValues;
    private String comments;
    private Calendar date;
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

    public void setComments(String comments) {
        this.comments = comments;
    }
}
