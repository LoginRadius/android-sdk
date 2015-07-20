package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.mention.LoginRadiusMention;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
The Mention API is used to get mention data from the user's social account. 
The data will be normalized into LoginRadius' data format.
*/

public class MentionAPI 
{
	private static final String[] providers = {"twitter"};
	/**
	 * Gives user's Mentions on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusMention[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
    	RestRequest.get("/api/v2/mention",params,new AsyncHandler<String>() 
    	{
			@Override
			public void onSuccess(String response) 
			{
				LoginRadiusMention[] mention = JsonDeserializer.deserializeJson(response,LoginRadiusMention[].class);
				handler.onSuccess(mention);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}

}