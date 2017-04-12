package com.loginradius.androidsdk.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ResendotpAPI {
    public void getResponse(LoginParams value, JsonObject data , final AsyncHandler<RegisterResponse> handler)
    {

        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());




        RestRequest.post(null, Endpoint.getVerifyotpUrl(),params,new Gson().toJson(data),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                RegisterResponse resendotp = JsonDeserializer.deserializeJson(response,RegisterResponse.class);
                handler.onSuccess(resendotp);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
