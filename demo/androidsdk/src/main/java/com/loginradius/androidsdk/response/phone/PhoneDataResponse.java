package com.loginradius.androidsdk.response.phone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 27-Jul-17.
 */

public class PhoneDataResponse {

    @SerializedName("Data")
    @Expose
    private PhoneForgotPasswordData data;

    public PhoneForgotPasswordData getData() {
        return data;
    }

    public void setData(PhoneForgotPasswordData data) {
        this.data = data;
    }
}
