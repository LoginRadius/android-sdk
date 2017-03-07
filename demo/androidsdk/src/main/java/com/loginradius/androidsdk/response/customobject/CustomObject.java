package com.loginradius.androidsdk.response.customobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 23-Nov-16.
 */

public class CustomObject {

    @SerializedName("customobject")
    @Expose
    private CustomobjectData customobject;

    /**
     *
     * @return
     * The customobject
     */
    public CustomobjectData getCustomobject() {
        return customobject;
    }

    /**
     *
     * @param customobject
     * The customobject
     */
    public void setCustomobject(CustomobjectData customobject) {
        this.customobject = customobject;
    }

}
