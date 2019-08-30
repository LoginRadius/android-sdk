package com.loginradius.androidsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SecurityQuestionModel  {


    @SerializedName("SecurityAnswer")
    @Expose
    private Map<String,String> securityAnswer;




    public Map<String,String> getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(Map<String,String> securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}