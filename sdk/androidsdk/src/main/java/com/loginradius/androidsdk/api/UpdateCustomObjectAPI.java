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

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by loginradius on 23-Nov-16.
 */

public class UpdateCustomObjectAPI {


    public void getResponse(LoginParams value, lrAccessToken token, JsonObject update , final AsyncHandler<CreateCustomObject> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);
        params.put("objectname",value.objectname);




        RestRequest.put(null, Endpoint.getCustomObjectUrl()+"/"+value.objectRecordId,params,new Gson().toJson(update),new AsyncHandler<String>()
        {
            @Override
            public void onSuccess(String response) {

                CreateCustomObject createCustomObject = JsonDeserializer.deserializeJson(response,CreateCustomObject.class);
                handler.onSuccess(createCustomObject);
            }

            @Override
            public void onFailure(Throwable error, String response) {
                handler.onFailure(error, response);
            }
        });
    }
}
