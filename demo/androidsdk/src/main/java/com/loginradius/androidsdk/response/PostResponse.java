package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("IsPosted")
    @Expose
    public boolean isPosted;
    public boolean getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(boolean posted) {
        isPosted = posted;
    }



}
