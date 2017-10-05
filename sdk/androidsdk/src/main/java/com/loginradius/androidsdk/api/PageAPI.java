package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.page.LoginRadiusPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
The Page API is used to get the page data from the user's social account. 
The data will be normalized into LoginRadius data format.
*/

public class PageAPI 
{
	private static final String[] providers = {"facebook", "vk", "linkedin"};

	
	/**
	 * Gives page information of the page
	 * @param token Authentication token from LoginRadius
	 * @param pageName Name of the page
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,String pageName, final AsyncHandler<LoginRadiusPage> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
    	Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("pagename", pageName);
		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getPage(Endpoint.API_V2_PAGE,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusPage>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusPage response) {
						handler.onSuccess(response);
					}

				});
	}

	/**
	 * Gives page information of the page
	 * @param token Authentication token from LoginRadius
	 * @param pageName Name of the page
	 * @param fields Projection of fields
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,String pageName,String fields[],final AsyncHandler<LoginRadiusPage> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("pagename", pageName);

		String strFields = null;
		if(fields!=null && fields.length>0){
			strFields = "";
			for(int i=0;i<fields.length;i++){
				if(i == (fields.length-1)){
					strFields = strFields + fields[i];
				}else{
					strFields = strFields + fields[i] + ",";
				}
			}
		}

		if(strFields!=null){
			params.put("fields",strFields);
		}

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getPage(Endpoint.API_V2_PAGE,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusPage>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusPage response) {
						handler.onSuccess(response);
					}

				});
	}
}
