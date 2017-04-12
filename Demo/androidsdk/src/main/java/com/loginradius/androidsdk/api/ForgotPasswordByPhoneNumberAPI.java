package com.loginradius.androidsdk.api;


import com.google.gson.Gson;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;

import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ForgotPasswordByPhoneNumberAPI {
    public void getResponse(LoginParams value, ForgotPasswordData data , final AsyncHandler<PhoneForgotPasswordResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());
        if (value.smsTemplate!=null) {
            params.put("smsTemplate",value.getSmsTemplate());
        }







        RestRequest.post(null, Endpoint.getForgotPasswordUrlMobile(),params,new Gson().toJson(data),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                PhoneForgotPasswordResponse forgotResponse = JsonDeserializer.deserializeJson(response,PhoneForgotPasswordResponse.class);
                handler.onSuccess(forgotResponse);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
