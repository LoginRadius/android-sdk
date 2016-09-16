package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.checkin.LoginRadiusCheckIn;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
*The Check In API is used to get check-ins data from the user's social account. 
*The data will be normalized into LoginRadius data format.
*/

public class CheckInAPI 
{
	private static final String API_V2_CHECKIN = "/api/v2/checkin";
	private static final String[] providers = {"vk", "foursquare", "facebook"};
	/**
	 * Get check in data by user
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusCheckIn[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
     	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
    	RestRequest.get(API_V2_CHECKIN, params,new AsyncHandler<String>() 
    	{
			@Override
			public void onSuccess(String response) 
			{
				LoginRadiusCheckIn[] checkin = JsonDeserializer.deserializeJson(response,LoginRadiusCheckIn[].class);
				handler.onSuccess(checkin);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
