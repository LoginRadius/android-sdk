package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.video.LoginRadiusVideo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The Video API is used to get video data from the user's social account.
 * The data will be normalized into LoginRadius data format.
 */
public class VideoAPI 
{

	private static final String[] providers = {"facebook", "vk", "msn" };
	
     /**
	 * Gives user's videos on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusVideo[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		RestRequest.get(Endpoint.getV2_VIDEO(), params,new AsyncHandler<String>()
		{
			@Override
			public void onSuccess(String response) {
				LoginRadiusVideo[] video = JsonDeserializer.deserializeJson(response,LoginRadiusVideo[].class);
				 handler.onSuccess(video);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
