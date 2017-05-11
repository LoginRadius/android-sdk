package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;

import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by loginradius on 11-May-17.
 */

public class EmailPromptAutoLoginAPI {

    public void getResponse(LoginParams value, final AsyncHandler<RegisterResponse> handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("email", value.getEmail());
        params.put("clientGuid", value.getClientGuid());
        params.put("autoLoginEmailTemplate", value.getAutoLoginEmailTemplate());
        params.put("welcomeEmailTemplate ", value.getWelcomeEmailTemplate());

        RestRequest.get(Endpoint.getEmailPromptAutoLogin(), params, new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                RegisterResponse logindata = JsonDeserializer.deserializeJson(response, RegisterResponse.class);
                handler.onSuccess(logindata);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
