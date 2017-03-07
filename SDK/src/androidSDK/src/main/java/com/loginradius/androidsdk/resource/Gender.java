package com.loginradius.androidsdk.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 9/30/2016.
 */
public class Gender {

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("gender1")
    @Expose
    private String gender1;
    @SerializedName("gender2")
    @Expose
    private String gender2;

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The gender1
     */
    public String getGender1() {
        return gender1;
    }

    /**
     *
     * @param gender1
     * The gender1
     */
    public void setGender1(String gender1) {
        this.gender1 = gender1;
    }

    /**
     *
     * @return
     * The gender2
     */
    public String getGender2() {
        return gender2;
    }

    /**
     *
     * @param gender2
     * The gender2
     */
    public void setGender2(String gender2) {
        this.gender2 = gender2;
    }

}
