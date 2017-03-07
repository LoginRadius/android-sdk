package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.page.LoginRadiusPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
The Page API is used to get the page data from the user's social account. 
The data will be normalized into LoginRadius data format.
*/

public class PageAPI 
{
	private static final String[] providers = {"facebook", "vk", "linkedin"};
	private String pageName;
	public String getPageName() {return pageName;}
	public void setPageName(String pageName) {this.pageName = pageName;}
	
	/**
	 * Gives page information of the page
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token, final AsyncHandler<LoginRadiusPage> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("pagename", pageName);
		RestRequest.get(Endpoint.getV2_PAGE(), params,new AsyncHandler<String>()
		{
			@Override
		    public void onSuccess(String response)
			{
			 LoginRadiusPage page = JsonDeserializer.deserializeJson(response,LoginRadiusPage.class);
			 handler.onSuccess(page);
		    }

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
