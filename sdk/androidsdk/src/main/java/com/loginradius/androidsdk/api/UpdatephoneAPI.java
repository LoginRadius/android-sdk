package com.loginradius.androidsdk.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.phone.PhoneResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 8/26/2016.
 */
public class UpdatephoneAPI {

    public void getResponse(LoginParams value, lrAccessToken token, JsonObject update, final AsyncHandler<PhoneResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);

        RestRequest.put(null, Endpoint.getUpdatephoneUrl(),params,new Gson().toJson(update),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                PhoneResponse updatephone = JsonDeserializer.deserializeJson(response,PhoneResponse.class);
                handler.onSuccess(updatephone);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
