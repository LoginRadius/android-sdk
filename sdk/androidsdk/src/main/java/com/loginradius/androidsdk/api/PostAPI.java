package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.post.LoginRadiusPost;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Retrieve posted messages from the user's social account.
 * Data will be normalized into LoginRadius data format.
 *
 */
public class PostAPI 
{
	private static final String[] providers = {"facebook"};
	/**
	 * Gives user's post on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusPost[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
    	RestRequest.get(Endpoint.getV2_POST(), params,new AsyncHandler<String>()
    	{
			@Override
			public void onSuccess(String response)
			{
			  LoginRadiusPost[] post = JsonDeserializer.deserializeJson(response,LoginRadiusPost[].class);
			  handler.onSuccess(post);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}