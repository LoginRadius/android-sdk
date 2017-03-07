package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.Map;


public class TraditionalUserLoginAPI {
    public void getResponse(LoginParams value, final AsyncHandler<LoginData> handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            params.put("phone", value.getPhone());
        }else if(value.username!=null){
            params.put("username", value.getUsername());
        }else {
            params.put("email", value.getEmail());
        }
        params.put("password", value.getPassword());
        params.put("verificationUrl", value.getVerificationUrl());
        params.put("emailTemplate", value.getEmailTemplate());

        RestRequest.get(Endpoint.getLoginUrl(), params, new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                LoginData logindata = JsonDeserializer.deserializeJson(response, LoginData.class);
                handler.onSuccess(logindata);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
