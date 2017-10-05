package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Used to retrieve social profile data from the user's social account after authentication.
 * The social profile will be retrieved via oAuth and OpenID protocols.
 * The data is normalized into LoginRadius' standard data format.
 *
 */
public class UserProfileAPI {

	/**
	 * Retrieves User Profile details
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusUltimateUserProfile> handler) {

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getUserProfile(Endpoint.API_V2_USERPROFILE,token.access_token,token.apikey,null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusUltimateUserProfile>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusUltimateUserProfile response) {
						handler.onSuccess(response);
					}

				});
	}

   /**
    * Retrieves User Profile details
    * @param token Authentication token from LoginRadius
	* @param fields Projection of fields
    * @param handler Used to handle the success and failure events
    */
	public void getResponse(lrAccessToken token,String fields[],final AsyncHandler<LoginRadiusUltimateUserProfile> handler) {

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
		apiService.getUserProfile(Endpoint.API_V2_USERPROFILE,token.access_token,token.apikey,strFields).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusUltimateUserProfile>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusUltimateUserProfile response) {
						handler.onSuccess(response);
					}

				});
	}
}
