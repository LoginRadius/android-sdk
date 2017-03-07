package com.loginradius.androidsdk.response.customobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 23-Nov-16.
 */

public class GetCustomObject {

    @SerializedName("Custom Object")
    @Expose
    private CustomObject_ customObject;

    /**
     *
     * @return
     * The customObject
     */
    public CustomObject_ getCustomObject() {
        return customObject;
    }

    /**
     *
     * @param customObject
     * The Custom Object
     */
    public void setCustomObject(CustomObject_ customObject) {
        this.customObject = customObject;
    }

}
