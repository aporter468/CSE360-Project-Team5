package com.cse360.group5.surveys;

import org.json.JSONException;
import org.json.JSONObject;

public class SurveyResult {
    private int patientid;
    private int pain;
    private int drowsiness;
    private int nausea;
    private int appetite;
    private int shortnessofbreath;
    private int depression;
    private int anxiety;
    private int wellbeing;
    private String comments;
    private long timestamp;

    public SurveyResult(int patientid, int pain, int drowsiness, int nausea, int appetite,
                        int shortnessofbreath, int depression, int anxiety, int wellbeing,
                        String comments, long timestamp) {
        this.patientid = patientid;
        this.pain = pain;
        this.drowsiness = drowsiness;
        this.nausea = nausea;
        this.appetite = appetite;
        this.shortnessofbreath = shortnessofbreath;
        this.depression = depression;
        this.anxiety = anxiety;
        this.wellbeing = wellbeing;
        this.comments = comments;
        this.timestamp = timestamp;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientid", this.patientid);
            jsonObject.put("pain", this.pain);
            jsonObject.put("drowsiness", this.drowsiness);
            jsonObject.put("nausea", this.nausea);
            jsonObject.put("appetite", this.appetite);
            jsonObject.put("shortnessofbreath", this.shortnessofbreath);
            jsonObject.put("depression", this.depression);
            jsonObject.put("anxiety", this.anxiety);
            jsonObject.put("wellbeing", this.wellbeing);
            jsonObject.put("comments", this.comments);
            jsonObject.put("timestamp", this.timestamp);

            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException("Could not populate json object in survey result");
        }
    }

    public int getPatientid() {
        return patientid;
    }

    public int getPain() {
        return pain;
    }

    public int getDrowsiness() {
        return drowsiness;
    }

    public int getNausea() {
        return nausea;
    }

    public int getAppetite() { return appetite; }

    public int getShortnessofbreath() {
        return shortnessofbreath;
    }

    public int getDepression() {
        return depression;
    }

    public int getAnxiety() {
        return anxiety;
    }

    public int getWellbeing() {
        return wellbeing;
    }

    public String getComments() {
        return comments;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
