package com.loginradius.androidsdk.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResetPINByEmailModel extends SecurityQuestionModel {


    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Otp")
    @Expose
    private String otp;

    @SerializedName("PIN")
    @Expose
    private String pin;



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
}