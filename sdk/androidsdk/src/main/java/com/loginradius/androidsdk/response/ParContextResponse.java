package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class ParContextResponse {

    @SerializedName("authorization_details")
    @Expose
    public LinkedHashMap<String, Object> authorizationDetails;

    @SerializedName("challenge")
    @Expose
    public String challenge;

    public LinkedHashMap<String, Object> getAuthorizationDetails() {
        return authorizationDetails;
    }

    public void setAuthorizationDetails(LinkedHashMap<String, Object> authorizationDetails) {
        this.authorizationDetails = authorizationDetails;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }
}
