package com.loginradius.androidsdk.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/1/2016.
 */
public class CustomizeSuccessMessage {


    @SerializedName("registermessage")
    @Expose
    private String registermessage;
    @SerializedName("forgotpasswordmessage")
    @Expose
    private String forgotpasswordmessage;


    /**
     *
     * @return
     * The registermessage
     */
    public String getRegistermessage() {
        return registermessage;
    }

    /**
     *
     * @param registermessage
     * The registermessage
     */
    public void setRegistermessage(String registermessage) {
        this.registermessage = registermessage;
    }

    /**
     *
     * @return
     * The forgotpasswordmessage
     */
    public String getForgotpasswordmessage() {
        return forgotpasswordmessage;
    }

    /**
     *
     * @param forgotpasswordmessage
     * The forgotpasswordmessage
     */
    public void setForgotpasswordmessage(String forgotpasswordmessage) {
        this.forgotpasswordmessage = forgotpasswordmessage;
    }
}
