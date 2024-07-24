package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceRegisterResponse {


    @SerializedName("isRegistered")

    @Expose
    private Boolean isRegistered;

    /**
     * @return The isRegistered
     */
    public Boolean getIsRegistered() {
        return isRegistered;
    }

    /**
     * @param isRegistered The isRegistered
     */
    public void setIsRegistered(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }


}
