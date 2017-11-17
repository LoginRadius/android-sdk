package com.loginradius.androidsdk.response.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/23/2017.
 */

public class InstantSignin {

    @SerializedName("EmailLink")
    @Expose
    private Boolean emailLink;
    @SerializedName("SmsOtp")
    @Expose
    private Boolean smsOtp;

    public Boolean getEmailLink() {
        return emailLink;
    }

    public void setEmailLink(Boolean emailLink) {
        this.emailLink = emailLink;
    }

    public Boolean getSmsOtp() {
        return smsOtp;
    }

    public void setSmsOtp(Boolean smsOtp) {
        this.smsOtp = smsOtp;
    }
}
