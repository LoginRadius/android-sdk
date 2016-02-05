package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.audio.LoginRadiusAudio;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
 * The AudioAPI is used to get audio files data from the user's social account. 
 * The data will be normalized into LoginRadius data format.
 */

public class AudioAPI 
{
	private static final String API_V2_AUDIO = "/api/v2/audio";
	private static final String[] providers = {"vk", "msn"};
	
	/**
	 * Gives user's audio data on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusAudio[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		RestRequest.get(API_V2_AUDIO, params,new AsyncHandler<String>() 
				{
			@Override
			public void onSuccess(String response) 
			{
				LoginRadiusAudio[] audio = JsonDeserializer.deserializeJson(response,LoginRadiusAudio[].class);
				handler.onSuccess(audio);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
				});
	}
}
