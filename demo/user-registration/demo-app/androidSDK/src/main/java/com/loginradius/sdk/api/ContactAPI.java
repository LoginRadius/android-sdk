package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.LoginRadiusContactCursorResponse;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
The Contact API is used to get contacts/friends/connections data from the user's social account. 
The data will normalized into LoginRadius data format.
*/

public class ContactAPI 
{
	private static final String API_V2_CONTACT = "/api/v2/contact";
	private static final String[] providers ={"facebook", "twitter", "google", "live", "yahoo", "linkedin", "vk",
			"renren", "foursquare"};
	/**
	 * Gives user's contacts on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusContactCursorResponse> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
        Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
        RestRequest.get(API_V2_CONTACT, params,new AsyncHandler<String>() 
        {
        	@Override
			public void onSuccess(String response) 
        	{
				LoginRadiusContactCursorResponse contact = JsonDeserializer.deserializeJson(response,LoginRadiusContactCursorResponse.class);
				handler.onSuccess(contact);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
