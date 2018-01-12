package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/9/2017.
 */

public class UpdateResponse {
    @SerializedName("IsPosted")
    @Expose
    private Boolean isPosted;

    /**
     *
     * @return
     * The isPosted
     */
    public Boolean getPosted() {
        return isPosted;
    }

    /**
     *
     * @param isPosted
     * The isPosted
     */
    public void setPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }
}
