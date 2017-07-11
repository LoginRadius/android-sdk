package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;


import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class SocialInterfaceAPI {


	public void getResponse(LoginParams value, final AsyncHandler<SocialInterface> handler) {

		ApiInterface apiService = RestRequest.getClientCdn().create(ApiInterface.class);
		apiService.getSocialProviderInterface(Endpoint.API_V2_SOCIALINTERFACE_URL+value.apikey+".json").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<SocialInterface>() {
					@Override
					public void onComplete() {
					}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);

					}

					@Override
					public void onNext(SocialInterface response) {
					//	response=response.replace("loginRadiusAppJsonLoaded(","");
					//response=response.substring(0,response.length()-1);
					//	SocialInterface socialInterface = JsonDeserializer.deserializeJson(response.toString(),SocialInterface.class);
						handler.onSuccess(response);
					}

				});
	}
}