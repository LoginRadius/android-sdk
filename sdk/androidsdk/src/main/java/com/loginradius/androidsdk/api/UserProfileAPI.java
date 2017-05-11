package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to retrieve social profile data from the user's social account after authentication.
 * The social profile will be retrieved via oAuth and OpenID protocols.
 * The data is normalized into LoginRadius' standard data format.
 *
 */
public class UserProfileAPI 
{


    private static final String[] providers = {"facebook", "google", "twitter", "instagram", "yahoo", "live",
	   "persona", "wordpress", "vkontakte", "aol", "myopenid", "mixi", "steamcommunity", "hyves", "livejournal",
	   "verisign", "virgilio", "orange", "github", "openid", "renren", "kaixin", "qq", "stackexchange","email","amazon","line","xing","paypal" };
   /**
    * Retrieves User Profile details
    * @param token Authentication token from LoginRadius
    * @param handler Used to handle the success and failure events
    */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusUltimateUserProfile> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", token.apikey);
	    params.put("access_token", token.access_token);
        RestRequest.get(Endpoint.getV2_USERPROFILE(),params,new AsyncHandler<String>()
       {
		@Override
		public void onSuccess(String response) {
			 LoginRadiusUltimateUserProfile userprofile = JsonDeserializer.deserializeJson(response,LoginRadiusUltimateUserProfile.class);
			 handler.onSuccess(userprofile);
		}
		   @Override
		public void onFailure(Throwable error, String response) {
			handler.onFailure(error, response);
		}
		});
	}



}
