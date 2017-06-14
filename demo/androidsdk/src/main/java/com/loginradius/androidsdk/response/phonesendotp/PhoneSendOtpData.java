package com.loginradius.androidsdk.response.phonesendotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 08-Jun-17.
 */

public class PhoneSendOtpData {

    @SerializedName("Data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
