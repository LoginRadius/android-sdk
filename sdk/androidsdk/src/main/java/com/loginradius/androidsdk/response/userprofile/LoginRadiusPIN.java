package com.loginradius.androidsdk.response.userprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRadiusPIN {
    @SerializedName("Skipped")
    @Expose
    private Object skipped;
    @SerializedName("PIN")
    @Expose
    private String pin;
    @SerializedName("LastPINChangeDate")
    @Expose
    private String lastPinChangeDate;
    @SerializedName("SkippedDate")
    @Expose
    private String skippedDate;

    public Object getSkipped() {
        return skipped;
    }

    public void setSkipped(Object skipped) {
        this.skipped = skipped;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLastPinChangeDate() {
        return lastPinChangeDate;
    }

    public void setLastPinChangeDate(String lastPinChangeDate) {
        this.lastPinChangeDate = lastPinChangeDate;
    }

    public String getSkippedDate() {
        return skippedDate;
    }

    public void setSkippedDate(String skippedDate) {
        this.skippedDate = skippedDate;
    }
}
