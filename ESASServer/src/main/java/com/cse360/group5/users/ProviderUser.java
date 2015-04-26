package com.cse360.group5.users;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.security.User;

/**
 * Contains all information a provider has. Extends Restlet's User to provide phone information.
 */
public class ProviderUser extends User {

    private int phone;

    public ProviderUser(int providerid, String firstName, String lastName, String email, int phone) {
        super(String.valueOf(providerid), new char[0], firstName, lastName, email);
        this.phone = phone;
    }

    public int getPhone() {
        return this.phone;
    }

    /**
     * JSONifys the ProviderUser to a JSONObject containing publishable fields.
     *
     * @return
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("identifier", this.getIdentifier());
        jsonObject.put("firstname", this.getFirstName());
        jsonObject.put("lastname", this.getLastName());
        jsonObject.put("email", this.getEmail());
        jsonObject.put("phone", this.getPhone());
        return jsonObject;
    }
}
