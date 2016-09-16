package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.following.LoginRadiusFollowing;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
The Following API is used to get the followers' information from the user's social account. 
The data will be normalized into LoginRadius data format.
 */

public class FollowingAPI 
{
	private static final String[] providers = {"twitter"};
	/**
	 * Gives user's followers on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusFollowing[]> handler) {
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		RestRequest.get("/api/v2/following", params,new AsyncHandler<String>() 
		{
			@Override
			public void onSuccess(String response) 
			{
				LoginRadiusFollowing[] following = JsonDeserializer.deserializeJson(response,LoginRadiusFollowing[].class);
				handler.onSuccess(following);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
