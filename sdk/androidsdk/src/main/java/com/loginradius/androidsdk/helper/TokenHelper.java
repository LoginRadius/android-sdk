package com.loginradius.androidsdk.helper;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;

import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Manages communication involving Facebook and Google keys for native login
 *
 */
public class TokenHelper 
{

	private String AKey = lrLoginManager.AKey;
	
	/**
	 * Send Facebook token to LR server
	 * @param fbToken Token from facebook
	 * @param handler callback handler
	 */
	public void getResponseFb(String fbToken, final AsyncHandler<lrAccessToken> handler)
	 {
		 	Map<String, String> params = new HashMap<String, String>();
		 	params.put("key",AKey);
		 	params.put("fb_access_token",fbToken);
		 	providerHandler(Endpoint.API_V2_ACCESS_TOKEN_FB, params, handler);
	 }
	/**
	 * Send Google token to LR server
	 * @param googleToken Token from google
	 * @param handler callback handler
	 */
	public void getResponseGoogle(String googleToken, final AsyncHandler<lrAccessToken> handler)
	  {
		 	Map<String, String> params = new HashMap<String, String>();
		 	params.put("key",AKey);
		 	params.put("google_access_token",googleToken);
		 	providerHandler(Endpoint.API_V2_ACCESS_TOKEN_GOOGLE, params, handler);
	  }


	/**
	 * Generic request
	 * @param uri Url for sending request
	 * @param params query parameters to be used
	 * @param handler callback handler
	 */
	public void providerHandler(String uri, Map<String,String> params, final AsyncHandler<lrAccessToken> handler) {



		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getNativeLogin(uri,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<lrAccessToken>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(lrAccessToken response) {
						handler.onSuccess(response);
					}

				});
	}
}
