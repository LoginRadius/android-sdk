package com.loginradius.androidsdk.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 9/7/2016.
 */
public class LinkAPI {

    public void getResponse(LoginParams value, String token, JsonObject change, final AsyncHandler<RegisterResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token);

        RestRequest.put(null, Endpoint.getSocialIdentities(),params,new Gson().toJson(change),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                RegisterResponse registerResponse = JsonDeserializer.deserializeJson(response,RegisterResponse.class);
                handler.onSuccess(registerResponse);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
