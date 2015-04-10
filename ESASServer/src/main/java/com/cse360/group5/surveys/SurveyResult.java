package com.cse360.group5.surveys;

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
    private int timestamp;

    public SurveyResult(int patientid, int pain, int drowsiness, int nausea, int appetite,
                        int shortnessofbreath, int depression, int anxiety, int wellbeing,
                        String comments, int timestamp) {
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

    public int getTimestamp() {
        return timestamp;
    }
}
