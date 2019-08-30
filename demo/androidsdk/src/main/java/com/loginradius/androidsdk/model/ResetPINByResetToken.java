package com.loginradius.androidsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPINByResetToken {


    @SerializedName("PIN")
    @Expose
    private String pin;

    @SerializedName("ResetToken")
    @Expose
    private String resetToken;



    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getResetToken() {
        return resetToken;
    }
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}