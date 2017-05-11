package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by loginradius on 11-May-17.
 */

public class EmailPromptAutoLoginPingAPI {
    public void getResponse(LoginParams value, final AsyncHandler<LoginData> handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("clientGuid", value.getClientGuid());


        RestRequest.get(Endpoint.getEmailPromptAutoLogin()+"/ping", params, new AsyncHandler<String>() {
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
