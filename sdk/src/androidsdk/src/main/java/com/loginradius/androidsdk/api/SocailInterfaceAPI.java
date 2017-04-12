package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;

import java.util.HashMap;
import java.util.Map;


public class SocailInterfaceAPI
{
	public void getResponse(LoginParams value, final AsyncHandler<SocialInterface> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		RestRequest.get(Endpoint.getSocialInterfaceUrl(value.apikey),params,new AsyncHandler<String>()
       {
		@Override
		public void onSuccess(String response) {
			response=response.replace("loginRadiusAppJsonLoaded(","");
			response=response.substring(0,response.length()-1);
			SocialInterface socialInterface = JsonDeserializer.deserializeJson(response,SocialInterface.class);
			handler.onSuccess(socialInterface);
		}

		@Override
		public void onFailure(Throwable error, String response) {
			handler.onFailure(error, response);
		}
		});
	}
}
