package com.loginradius.androidsdk.response.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/27/2017.
 */

public class PasswordLength {
    @SerializedName("Min")
    @Expose
    private Integer Min;
    @SerializedName("Max")
    @Expose
    private Integer Max;

    public Integer getMin() {
        return Min;
    }

    public void setMin(Integer min) {
        Min = min;
    }

    public Integer getMax() {
        return Max;
    }

    public void setMax(Integer max) {
        Max = max;
    }
}
