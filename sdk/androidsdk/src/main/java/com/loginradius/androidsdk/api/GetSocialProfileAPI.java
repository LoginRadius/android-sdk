package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 9/7/2016.
 */
public class GetSocialProfileAPI {

    public void getResponse(LoginParams value, lrAccessToken token, final AsyncHandler<LoginRadiusUltimateUserProfile> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);

        RestRequest.get(Endpoint.getSocialIdentities(),params,new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                LoginRadiusUltimateUserProfile ultimateUserProfile = JsonDeserializer.deserializeJson(response, LoginRadiusUltimateUserProfile.class);
                handler.onSuccess(ultimateUserProfile);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
