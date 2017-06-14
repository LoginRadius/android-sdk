package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 08-Jun-17.
 */

public class CheckAvailability {

    @SerializedName("IsExist")
    @Expose
    private Boolean isExist;

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }

}
