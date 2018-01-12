/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loginradius.androidsdk.response.userprofile;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Administrator
 */
public class ProviderAccessCredential {

    @SerializedName("AccessToken")
    public String accessToken;
    @SerializedName("TokenSecret")
    public String tokenSecret;

    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The AccessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The tokenSecret
     */
    public String getTokenSecret() {
        return tokenSecret;
    }

    /**
     *
     * @param tokenSecret
     * The TokenSecret
     */
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
