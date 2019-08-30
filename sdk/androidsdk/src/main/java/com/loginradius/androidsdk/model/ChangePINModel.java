package com.loginradius.androidsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePINModel {


    @SerializedName("NewPIN")
    @Expose
    private String newPin;

    @SerializedName("OldPIN")
    @Expose
    private String oldPin;


    public String getNewPin() {
        return newPin;
    }
    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
    public String getOldPin() {
        return oldPin;
    }
    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }
}