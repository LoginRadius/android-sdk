package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.login.LoginData;

/**
 * Created by loginradius on 10/6/2017.
 */

public class VerifyEmailResponse {
    @SerializedName("IsPosted")
    @Expose
    private Boolean isPosted;

    @SerializedName("Data")
    @Expose
    private LoginData data;

    /**
     *
     * @return
     * The isPosted
     */
    public Boolean getPosted() {
        return isPosted;
    }

    /**
     *
     * @param isPosted
     * The isPosted
     */
    public void setPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    /**
     *
     * @return
     * The data
     */
    public LoginData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LoginData data) {
        this.data = data;
    }
}
