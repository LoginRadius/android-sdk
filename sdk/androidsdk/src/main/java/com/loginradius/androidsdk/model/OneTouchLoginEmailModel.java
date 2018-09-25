package com.loginradius.androidsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneTouchLoginEmailModel {
    @SerializedName("clientguid")
    @Expose
    private String clientguid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("qq_captcha_ticket")
    @Expose
    private String qqCaptchaTicket;
    @SerializedName("qq_captcha_randstr")
    @Expose
    private String qqCaptchaRandstr;
    @SerializedName("g-recaptcha-response")
    @Expose
    private String gRecaptchaResponse;

    public String getClientguid() {
        return clientguid;
    }

    public void setClientguid(String clientguid) {
        this.clientguid = clientguid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQqCaptchaTicket() {
        return qqCaptchaTicket;
    }

    public void setQqCaptchaTicket(String qqCaptchaTicket) {
        this.qqCaptchaTicket = qqCaptchaTicket;
    }

    public String getQqCaptchaRandstr() {
        return qqCaptchaRandstr;
    }

    public void setQqCaptchaRandstr(String qqCaptchaRandstr) {
        this.qqCaptchaRandstr = qqCaptchaRandstr;
    }

    public String getGRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setGRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }
}
