package com.loginradius.androidsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneTouchLoginPhoneModel {
    @SerializedName("phone")
    @Expose
    private String phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
