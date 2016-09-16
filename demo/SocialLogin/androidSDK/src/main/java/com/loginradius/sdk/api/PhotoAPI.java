package com.loginradius.sdk.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.photo.LoginRadiusPhoto;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

/**
The photo API is used to get photo data from the user's social account. 
The data will be normalized into LoginRadius data format
*/
public class PhotoAPI 
{
	private static final String API_V2_PHOTO = "/api/v2/photo";
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
        RestRequest.get(API_V2_PHOTO, params,new AsyncHandler<String>() 
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
