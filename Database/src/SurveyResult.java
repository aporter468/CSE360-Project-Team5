public class SurveyResult {
    private int patientid;
    private int painindex;
    private int timestamp;

    public SurveyResult(int patientid, int painindex, int timestamp) {
        this.patientid = patientid;
        this.painindex = painindex;
        this.timestamp = timestamp;
    }

    public int getPatientid() {
        return patientid;
    }

    public int getPainindex() {
        return painindex;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
