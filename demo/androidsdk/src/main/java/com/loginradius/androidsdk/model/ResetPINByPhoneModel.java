package com.loginradius.androidsdk.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResetPINByPhoneModel extends SecurityQuestionModel {


    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("Otp")
    @Expose
    private String otp;

    @SerializedName("PIN")
    @Expose
    private String pin;



    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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