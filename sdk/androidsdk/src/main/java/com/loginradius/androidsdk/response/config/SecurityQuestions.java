package com.loginradius.androidsdk.response.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.securityquestions.SecurityQuestionsResponse;

import java.util.List;

/**
 * Created by loginradius on 10/27/2017.
 */

public class SecurityQuestions {
    @SerializedName("Questions")
    @Expose
    private List<SecurityQuestionsResponse> questions = null;
    @SerializedName("SecurityQuestionCount")
    @Expose
    private Integer securityQuestionCount;

    public List<SecurityQuestionsResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SecurityQuestionsResponse> questions) {
        this.questions = questions;
    }

    public Integer getSecurityQuestionCount() {
        return securityQuestionCount;
    }

    public void setSecurityQuestionCount(Integer securityQuestionCount) {
        this.securityQuestionCount = securityQuestionCount;
    }
}
