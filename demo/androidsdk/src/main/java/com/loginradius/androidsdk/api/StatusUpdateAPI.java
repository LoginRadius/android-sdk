package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.PostAPIResponse;
import com.loginradius.androidsdk.response.lrAccessToken;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 *The StatusUpdate API is used to update the status on the user's wall. 
 *The data will be normalized into LoginRadius data format.
 */

/**
 * Post status updates on the user's wall.
 * Data will be normalized into LoginRadius data format
 *
 */
public class StatusUpdateAPI 
{
	public static final String[] providers = {"facebook", "twitter", "linkedin", "vk", "renren"};
	private String title;
	private String url;
	private String imageurl;
	private String caption;
	private String status;
	private String description;
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getImageurl() {return imageurl;}
	public void setImageurl(String imageurl) {this.imageurl = imageurl;}
	public String getCaption() {return caption;}
	public void setCaption(String caption) {this.caption = caption;}
	public String getStatus() {return status;}
	public void setStatus(String status) {this.status = status;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	/**
	 * Post the status
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<PostAPIResponse> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("title", title);
		params.put("url", url);
		params.put("imageurl", imageurl);
		params.put("status", status);
		params.put("caption", caption);
		params.put("description", description);

		
		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getStatusUpdate(Endpoint.API_V2_STATUS,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<PostAPIResponse>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						if (e instanceof HttpException) {
							try {
								Throwable t = new Throwable(((HttpException) e).response().errorBody().string(), e);
								handler.onFailure(t, "lr_SERVER");
							} catch (Exception t) {
								t.printStackTrace();
							}

						}

					}

					@Override
					public void onNext(PostAPIResponse response) {
						handler.onSuccess(response);
					}

				});
	}

}
