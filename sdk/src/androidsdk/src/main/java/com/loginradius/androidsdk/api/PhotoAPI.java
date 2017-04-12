package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.photo.LoginRadiusPhoto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
The photo API is used to get photo data from the user's social account. 
The data will be normalized into LoginRadius data format
*/
public class PhotoAPI 
{

	private static final String[] providers = {"facebook", "msn", "renren", "vk", "google"};
    private String albumId;
	public String getAlbumId() {return albumId;}
	public void setAlbumId(String albumId) {this.albumId = albumId;}
	
	/**
	 * Gives user's photos on social providers
	 * @param token token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusPhoto[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("albumId", albumId);
        RestRequest.get(Endpoint.getV2_PHOTO(), params,new AsyncHandler<String>()
        {
			@Override
			public void onSuccess(String response)
			{
			  LoginRadiusPhoto[] photo = JsonDeserializer.deserializeJson(response,LoginRadiusPhoto[].class);
			  handler.onSuccess(photo);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
		});
	}
}
