package com.cse360.group5.users;

import org.restlet.security.User;

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
}
