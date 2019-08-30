package com.loginradius.androidsdk.response.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PINInfo {

    @SerializedName("PIN")
    @Expose
    private String pin;
    @SerializedName("Skipped")
    @Expose
    private String skipped;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }
}
