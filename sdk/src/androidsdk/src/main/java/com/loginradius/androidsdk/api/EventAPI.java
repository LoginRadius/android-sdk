package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.event.LoginRadiusEvent;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 The Event API is used to get the event data from the user's social account. 
 The data will be normalized into LoginRadius data format.
 */

public class EventAPI 
{

	private static final String[] providers = {"facebook", "msn"};
	
	/**
	 * Get user's events
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusEvent[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
     	RestRequest.get(Endpoint.getV2_EVENT(), params,new AsyncHandler<String>()
     	{

			@Override
			public void onSuccess(String response) {
				LoginRadiusEvent[] event = JsonDeserializer.deserializeJson(response,LoginRadiusEvent[].class);
				handler.onSuccess(event);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}

}