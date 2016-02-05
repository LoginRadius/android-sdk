package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.status.LoginRadiusStatus;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
 * Retrieve the status messages from the user's social account.
 * Data will be normalized into LoginRadius standard data format.
 */
public class StatusAPI 
{
	private static final String[] providers = {"facebook", "twitter", "linkedin", "vk", "renren"};
	/**
	 * Gives user's status on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusStatus[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
        RestRequest.get("/api/v2/status", params,new AsyncHandler<String>() 
         {
			@Override
			public void onSuccess(String response) 
			 {
				LoginRadiusStatus[] status = JsonDeserializer.deserializeJson(response,LoginRadiusStatus[].class);
				handler.onSuccess(status);
			 }


			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}

}