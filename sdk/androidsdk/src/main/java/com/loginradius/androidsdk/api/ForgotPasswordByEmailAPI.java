package com.loginradius.androidsdk.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.password.ForgotResponse;


import java.util.HashMap;
import java.util.LinkedHashMap;

public class ForgotPasswordByEmailAPI {
    public void getResponse(LoginParams value, ForgotPasswordData data , final AsyncHandler<ForgotResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        if (value.resetPasswordUrl!=null) {
            params.put("resetPasswordUrl", value.getResetPasswordUrl());
        }else if(value.emailTemplate!=null){
            params.put("emailTemplate", value.getEmailTemplate());
        }






        RestRequest.post(null, Endpoint.getForgotPasswordUrlEmail(),params,new Gson().toJson(data),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                ForgotResponse forgotResponse = JsonDeserializer.deserializeJson(response,ForgotResponse.class);
                handler.onSuccess(forgotResponse);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
