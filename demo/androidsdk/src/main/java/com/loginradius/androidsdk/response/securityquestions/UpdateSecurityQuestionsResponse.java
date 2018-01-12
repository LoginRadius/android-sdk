package com.loginradius.androidsdk.response.securityquestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

/**
 * Created by loginradius on 11/10/2017.
 */

public class UpdateSecurityQuestionsResponse {
    @SerializedName("IsPosted")
    @Expose
    private Boolean isPosted;
    @SerializedName("Data")
    @Expose
    private LoginRadiusUltimateUserProfile userProfile;

    public Boolean getPosted() {
        return isPosted;
    }

    public void setPosted(Boolean posted) {
        isPosted = posted;
    }

    public LoginRadiusUltimateUserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(LoginRadiusUltimateUserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
