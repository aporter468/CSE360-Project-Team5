package com.cse360.group5.users;

import org.restlet.security.User;

public class ProviderUser extends User {

    private int phone;

    public ProviderUser(int providerid, String firstName, String lastName, String email, int phone) {
        super(String.valueOf(providerid), new char[0], firstName, lastName, email);
        this.phone = phone;
    }

    public int getPhone() {
        return this.phone;
    }
}
