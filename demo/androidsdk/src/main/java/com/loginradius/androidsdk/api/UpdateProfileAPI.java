package com.loginradius.androidsdk.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 8/26/2016.
 */
public class UpdateProfileAPI {

    public void getResponse(LoginParams value, lrAccessToken token, JsonObject update, final AsyncHandler<RegisterResponse> handler)
    {

        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);
        if (value.verificationUrl!=null){
            params.put("verificationUrl",value.verificationUrl);
        }if (value.emailTemplate!=null){
        params.put("emailTemplate",value.emailTemplate);

    }
        RestRequest.put(null, Endpoint.getUpdateProfileUrl(),params,new Gson().toJson(update),new AsyncHandler<String>()
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



