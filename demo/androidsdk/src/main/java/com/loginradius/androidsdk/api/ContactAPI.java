package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.LoginRadiusContactCursorResponse;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
The Contact API is used to get contacts/friends/connections data from the user's social account. 
The data will normalized into LoginRadius data format.
*/

public class ContactAPI 
{

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
        RestRequest.get(Endpoint.getV2_CONTACT(), params,new AsyncHandler<String>()
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
