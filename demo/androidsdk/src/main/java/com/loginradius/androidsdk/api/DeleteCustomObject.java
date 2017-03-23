package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;

import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.register.DeleteResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 23-Nov-16.
 */

public class DeleteCustomObject {

    public void getResponse(LoginParams value, lrAccessToken token,final AsyncHandler<DeleteResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);
        params.put("objectname",value.objectname);




        RestRequest.delete(null,Endpoint.getCustomObjectUrl()+"/"+value.objectRecordId,params,"",new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                DeleteResponse deleteResponse = JsonDeserializer.deserializeJson(response,DeleteResponse.class);
                handler.onSuccess(deleteResponse);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
