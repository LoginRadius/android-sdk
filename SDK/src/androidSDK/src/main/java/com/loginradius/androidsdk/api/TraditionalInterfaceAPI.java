package com.loginradius.androidsdk.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TraditionalInterfaceAPI {

	public void getResponse(LoginParams value,final AsyncHandler<List<UserRegisteration>> handler) {
		Map<String, String> params = new HashMap<String, String>();
		RestRequest.get(Endpoint.getRegisterInterfaceUrl(value.apikey),params,new AsyncHandler<String>() {
		@Override
		public void onSuccess(String response) {
			 response=response.replace("loginRadiusAppRaasSchemaJsonLoaded(","");
			 response=response.substring(0,response.length()-1);
			Type listType = new TypeToken<ArrayList<UserRegisteration>>(){}.getType();
			List<UserRegisteration> ragisterInterfacedata = new Gson().fromJson(response, listType);
			handler.onSuccess(ragisterInterfacedata);
		}
		   @Override
		public void onFailure(Throwable error, String response) {
			handler.onFailure(error, response);
		   }
		});
	}
}
