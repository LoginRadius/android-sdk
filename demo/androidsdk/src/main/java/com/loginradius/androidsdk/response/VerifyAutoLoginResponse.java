package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 8/28/2017.
 */

public class VerifyAutoLoginResponse {

    @SerializedName("IsVerified")
    @Expose
    private Boolean isVerified;

    /**
     *
     * @return
     * The isVerified
     */
    public Boolean getVerified() {
        return isVerified;
    }

    /**
     *
     * @param isVerified
     * The isPosted
     */
    public void setVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}
