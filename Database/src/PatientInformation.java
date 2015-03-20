public class PatientInformation {
    private int patientId;
    private int providerId;
    private String name;
    private String email;
    private int phone;

    public PatientInformation(int patientId, int providerId, String name, String email, int phone) {
        this.patientId = patientId;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getEmail() {
        return email;
    }
}
