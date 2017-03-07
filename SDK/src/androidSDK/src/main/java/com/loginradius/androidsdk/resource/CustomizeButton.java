package com.loginradius.androidsdk.resource;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 9/30/2016.
 */
public class CustomizeButton {

    @SerializedName("loginbutton")
    @Expose
    public String loginbutton;
    @SerializedName("registerbutton")
    @Expose
    private String registerbutton;
    @SerializedName("forgetpasswordbutton")
    @Expose
    private String forgetpasswordbutton;

    /**
     *
     * @return
     * The loginbutton
     */
    public String getLoginbutton() {
        return loginbutton;
    }

    /**
     *
     * @param loginbutton
     * The loginbutton
     */
    public void setLoginbutton(String loginbutton) {
        this.loginbutton = loginbutton;
    }

    /**
     *
     * @return
     * The registerbutton
     */
    public String getRegisterbutton() {
        return registerbutton;
    }

    /**
     *
     * @param registerbutton
     * The registerbutton
     */
    public void setRegisterbutton(String registerbutton) {
        this.registerbutton = registerbutton;
    }

    /**
     *
     * @return
     * The forgetpasswordbutton
     */
    public String getForgetpasswordbutton() {
        return forgetpasswordbutton;
    }

    /**
     *
     * @param forgetpasswordbutton
     * The forgetpasswordbutton
     */
    public void setForgetpasswordbutton(String forgetpasswordbutton) {
        this.forgetpasswordbutton = forgetpasswordbutton;
    }

}
