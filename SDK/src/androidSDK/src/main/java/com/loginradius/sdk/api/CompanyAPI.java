package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.company.LoginRadiusCompany;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
 *The Company API is used to get the followed company's data in the user's social account. 
 *The data will be normalized into LoginRadius data format.
 * 
 */

public class CompanyAPI 
{
	private static final String API_V2_COMPANY = "/api/v2/company";
	private static final String[] providers = {"linkedin", "facebook"};

	/**
	 * Get company names followed by user
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusCompany[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
     	RestRequest.get(API_V2_COMPANY,params,new AsyncHandler<String>() 
     	{
			@Override
			public void onSuccess(String response) 
			{
				LoginRadiusCompany[] company = JsonDeserializer.deserializeJson(response,LoginRadiusCompany[].class);
				handler.onSuccess(company);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}