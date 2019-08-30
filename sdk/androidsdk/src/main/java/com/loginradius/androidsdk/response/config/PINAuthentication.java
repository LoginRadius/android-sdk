package com.loginradius.androidsdk.response.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PINAuthentication {
    @SerializedName("IsEnabled")
    @Expose
    private String isEnabled;
    @SerializedName("Configuration")
    @Expose
    private PINConfiguration pinConfigurations;

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public PINConfiguration getPinConfigurations() {
        return pinConfigurations;
    }

    public void setPinConfigurations(PINConfiguration pinConfigurations) {
        this.pinConfigurations = pinConfigurations;
    }
}
