package com.loginradius.androidsdk.response.config;

/**
 * Created by loginradius on 10/23/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.socialinterface.Provider;

import java.util.List;

public class SocialSchema {

    @SerializedName("Providers")
    @Expose
    private List<Provider> providers = null;

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
