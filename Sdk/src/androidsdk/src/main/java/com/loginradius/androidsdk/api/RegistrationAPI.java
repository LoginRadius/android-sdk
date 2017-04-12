package com.loginradius.androidsdk.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.customobject.CreateCustomObject;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.register.RegistrationData;

import java.util.HashMap;
import java.util.LinkedHashMap;



public class RegistrationAPI {

    public void getResponse(LoginParams value, RegistrationData registrationData , final AsyncHandler<RegisterResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("sott",value.sott);
        if (value.smsTemplate!=null){
            params.put("smsTemplate",value.smsTemplate);
        }else {
            params.put("verificationUrl", value.verificationUrl);
            params.put("emailTemplate", value.emailTemplate);
        }



        RestRequest.post(null,Endpoint.getRegistrationUrl(),params,new Gson().toJson(registrationData),new AsyncHandler<String>()
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
