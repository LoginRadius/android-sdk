package com.loginradius.androidsdk.response.phonesendotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 08-Jun-17.
 */

public class Data {

    @SerializedName("AccountSid")
    @Expose
    private String accountSid;
    @SerializedName("Sid")
    @Expose
    private String sid;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
