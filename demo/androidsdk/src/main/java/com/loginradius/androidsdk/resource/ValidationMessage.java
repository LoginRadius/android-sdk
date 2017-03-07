package com.loginradius.androidsdk.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/2/2016.
 */
public class ValidationMessage {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confermpassword")
    @Expose
    private String confermpassword;
    @SerializedName("requiredfield")
    @Expose
    private String requiredfield;

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The confermpassword
     */
    public String getConfermpassword() {
        return confermpassword;
    }

    /**
     *
     * @param confermpassword
     * The confermpassword
     */
    public void setConfermpassword(String confermpassword) {
        this.confermpassword = confermpassword;
    }

    /**
     *
     * @return
     * The requiredfield
     */
    public String getRequiredfield() {
        return requiredfield;
    }

    /**
     *
     * @param requiredfield
     * The requiredfield
     */
    public void setRequiredfield(String requiredfield) {
        this.requiredfield = requiredfield;
    }

}
