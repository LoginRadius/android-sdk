package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.group.LoginRadiusGroup;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 The Group API is used to get group data from the user's social account. 
 The data will be normalized into LoginRadius' data format.
 */
public class GroupAPI 
{
	private static final String[] providers = {"linkedin", "facebook", "vk"};
	/**
	 * Gives user's groups on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusGroup[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getGroup(Endpoint.API_V2_GROUP,token.access_token,null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusGroup[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);

					}

					@Override
					public void onNext(LoginRadiusGroup[] response) {
						handler.onSuccess(response);
					}

				});
	}

	/**
	 * Gives user's groups on social providers
	 * @param token Authentication token from LoginRadius
	 * @param fields Projection of fields
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,String fields[],final AsyncHandler<LoginRadiusGroup[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}

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

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getGroup(Endpoint.API_V2_GROUP,token.access_token,strFields).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusGroup[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);

					}

					@Override
					public void onNext(LoginRadiusGroup[] response) {
						handler.onSuccess(response);
					}

				});
	}
}
