package com.porter.esas;
import java.util.Calendar;
/**
 * Created by Alex on 3/19/15.
 */

public class Survey {
    public static int numSurveyFields = 5;
    public static String[] SURVEY_FIELDS = {"Pain","Exhaustion","Nausea","Depression","Anxiety"};
    private int[] surveyValues;
    private String comments;
    private Calendar date;
    public Survey(int[] surveyValues, String comments,Calendar date)
    {
        this.surveyValues= surveyValues;
        this.comments = comments;
        this.date = date;
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
