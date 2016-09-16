package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.page.LoginRadiusPage;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
The Page API is used to get the page data from the user's social account. 
The data will be normalized into LoginRadius data format.
*/

public class PageAPI 
{
	private static final String[] providers = {"facebook", "vk", "linkedin"};
	private static final String API_V2_PAGE = "/api/v2/page";
    private String pageName;
	public String getPageName() {return pageName;}
	public void setPageName(String pageName) {this.pageName = pageName;}
	
	/**
	 * Gives page information of the page
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusPage> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("pagename", pageName);
		RestRequest.get(API_V2_PAGE, params,new AsyncHandler<String>() 
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
