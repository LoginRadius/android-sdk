package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.like.LoginRadiusLike;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;
/**
The Likes API is used to get likes data from the user's social account. 
The data will be normalized into LoginRadius' data format.
 */
public class LikeAPI 
{
	private static final String API_V2_LIKE = "/api/v2/like";
	private static final String[] providers = {"wordpress", "facebook"};
	
	/**
	 * Gives user's likes on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusLike[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		RestRequest.get(API_V2_LIKE, params,new AsyncHandler<String>() 
		{
			@Override
			public void onSuccess(String response) 
			{
			  LoginRadiusLike[] like = JsonDeserializer.deserializeJson(response,LoginRadiusLike[].class);
			  handler.onSuccess(like);
		    }

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
	 });
	}
}
