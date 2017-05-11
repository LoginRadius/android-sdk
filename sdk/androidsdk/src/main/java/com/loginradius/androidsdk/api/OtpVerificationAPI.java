package com.loginradius.androidsdk.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;



import java.util.HashMap;
import java.util.LinkedHashMap;

public class OtpVerificationAPI {

    public void getResponse(LoginParams value, JsonObject data , final AsyncHandler<LoginData> handler)
    {
        String VerifyotpUrl=null;
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());
        if (value.otp!=null) {
            params.put("otp",value.getOtp());
          VerifyotpUrl= Endpoint.getVerifyotpUrl();
        }else {
            VerifyotpUrl= Endpoint.getForgotPasswordUrlMobile();
        }



        RestRequest.put(null, VerifyotpUrl,params,new Gson().toJson(data),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                LoginData verificationResponse = JsonDeserializer.deserializeJson(response,LoginData.class);
                handler.onSuccess(verificationResponse);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
