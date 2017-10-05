package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.checkin.LoginRadiusCheckIn;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
*The Check In API is used to get check-ins data from the user's social account. 
*The data will be normalized into LoginRadius data format.
*/

public class CheckInAPI {

	private static final String[] providers = {"vk", "foursquare", "facebook"};

	/**
	 * Get check in data by user
	 *
	 * @param token   Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token, final AsyncHandler<LoginRadiusCheckIn[]> handler) {
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getCheckin(Endpoint.API_V2_CHECKIN,token.access_token,null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusCheckIn[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);

					}

					@Override
					public void onNext(LoginRadiusCheckIn[] response) {
						handler.onSuccess(response);
					}

				});
	}

	/**
	 * Get check in data by user
	 *
	 * @param token   Authentication token from LoginRadius
	 * @param fields  Projection of fields
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token, String fields[], final AsyncHandler<LoginRadiusCheckIn[]> handler) {
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
		apiService.getCheckin(Endpoint.API_V2_CHECKIN,token.access_token,strFields).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusCheckIn[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);

					}

					@Override
					public void onNext(LoginRadiusCheckIn[] response) {
						handler.onSuccess(response);
					}

				});
	}
}