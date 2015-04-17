package com.porter.esas;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(timestamp );
        c = Calendar.getInstance();
        c.setTime(d);

        //Toast.makeText(activity, sdf.format(c), Toast.LENGTH_SHORT).show();
    }
    public Calendar getCalendar()
    {
        return c;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
