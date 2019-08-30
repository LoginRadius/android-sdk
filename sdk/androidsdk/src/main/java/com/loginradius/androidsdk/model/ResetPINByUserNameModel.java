package com.loginradius.androidsdk.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResetPINByUserNameModel extends SecurityQuestionModel {


    @SerializedName("Username")
    @Expose
    private String userName;

    @SerializedName("Otp")
    @Expose
    private String otp;

    @SerializedName("PIN")
    @Expose
    private String pin;



    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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