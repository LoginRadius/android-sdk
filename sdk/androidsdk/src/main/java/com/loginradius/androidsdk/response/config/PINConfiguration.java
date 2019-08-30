package com.loginradius.androidsdk.response.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PINConfiguration {

    @SerializedName("PINLogin")
    @Expose
    private boolean pinLogin;
    @SerializedName("IsRequired")
    @Expose
    private boolean isRequired;
    @SerializedName("AskOnRegistration")
    @Expose
    private boolean askOnRegistration;
    @SerializedName("AskOnLogin")
    @Expose
    private boolean askOnLogin;
    @SerializedName("AskOnlyOnFirstLogin")
    @Expose
    private boolean askOnlyOnFirstLogin;

    public boolean isPinLogin() {
        return pinLogin;
    }

    public void setPinLogin(boolean pinLogin) {
        this.pinLogin = pinLogin;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public boolean isAskOnRegistration() {
        return askOnRegistration;
    }

    public void setAskOnRegistration(boolean askOnRegistration) {
        this.askOnRegistration = askOnRegistration;
    }

    public boolean isAskOnLogin() {
        return askOnLogin;
    }

    public void setAskOnLogin(boolean askOnLogin) {
        this.askOnLogin = askOnLogin;
    }

    public boolean isAskOnlyOnFirstLogin() {
        return askOnlyOnFirstLogin;
    }

    public void setAskOnlyOnFirstLogin(boolean askOnlyOnFirstLogin) {
        this.askOnlyOnFirstLogin = askOnlyOnFirstLogin;
    }
}
