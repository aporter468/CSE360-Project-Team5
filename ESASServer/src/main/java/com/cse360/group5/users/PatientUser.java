package com.cse360.group5.users;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.security.User;

/**
 * Contains all information that a patient has. Extends Restlet's User to provide phone and provider information.
 */
public class PatientUser extends User {
    private int providerId;
    private int phone;

    public PatientUser(int patientId, String firstName, String lastName, String email, int phone, int providerId) {
        super(String.valueOf(patientId), new char[0], firstName, lastName, email);
        this.providerId = providerId;
        this.phone = phone;
    }

    public int getProviderId() {
        return this.providerId;
    }

    public int getPhone() {
        return this.phone;
    }

    /**
     * JSONifys the PatientUser to a JSONObject containing its publishable fields.
     *
     * @return
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("patientid", Integer.valueOf(this.getIdentifier()));
        jsonObject.put("firstname", this.getFirstName());
        jsonObject.put("lastname", this.getLastName());
        jsonObject.put("email", this.getEmail());
        jsonObject.put("phone", this.getPhone());
        jsonObject.put("providerid", this.getProviderId());
        return jsonObject;
    }
}
