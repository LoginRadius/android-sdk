package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.group.LoginRadiusGroup;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
 The Group API is used to get group data from the user's social account. 
 The data will be normalized into LoginRadius' data format.
 */
public class GroupAPI 
{
	private static final String[] providers = {"linkedin", "facebook", "vk"};
	/**
	 * Gives user's groups on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusGroup[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		RestRequest.get("/api/v2/group", params,new AsyncHandler<String>() 
		{
			@Override
			public void onSuccess(String response) {
				LoginRadiusGroup[] userprofile = JsonDeserializer.deserializeJson(response,LoginRadiusGroup[].class);
				handler.onSuccess(userprofile);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
