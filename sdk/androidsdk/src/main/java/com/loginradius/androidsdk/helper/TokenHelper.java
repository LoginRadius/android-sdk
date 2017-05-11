package com.loginradius.androidsdk.helper;


import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages communication involving Facebook and Google keys for native login
 *
 */
public class TokenHelper 
{

	private String AKey = lrLoginManager.AKey;
	
	/**
	 * Send Facebook token to LR server
	 * @param fbToken Token from facebook
	 * @param handler callback handler
	 */
	public void getResponseFb(String fbToken, final AsyncHandler<lrAccessToken> handler)
	 {
		 	Map<String, String> params = new HashMap<String, String>();
		 	params.put("key",AKey);
		 	params.put("fb_access_token",fbToken);
		 	providerHandler(Endpoint.getFacebookToken_Native(), params, handler);
	 }
	/**
	 * Send Google token to LR server
	 * @param googleToken Token from google
	 * @param handler callback handler
	 */
	public void getResponseGoogle(String googleToken, final AsyncHandler<lrAccessToken> handler)
	  {
		 	Map<String, String> params = new HashMap<String, String>();
		 	params.put("key",AKey);
		 	params.put("google_access_token",googleToken);
		 	providerHandler(Endpoint.getGoogleToken_Native(), params, handler);
	  }


	/**
	 * Generic request
	 * @param uri Url for sending request
	 * @param params query parameters to be used
	 * @param handler callback handler
	 */
	public void providerHandler(String uri, Map<String,String> params, final AsyncHandler<lrAccessToken> handler)
	{
		RestRequest.get(uri,params,new AsyncHandler<String>()
		{
			@Override
			public void onSuccess(String response) {
				lrAccessToken accessToken = JsonDeserializer.deserializeJson(response,lrAccessToken.class);
				handler.onSuccess(accessToken);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
