package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;

import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;

import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.audio.LoginRadiusAudio;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.io.IOException;
import java.util.Arrays;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * The AudioAPI is used to get audio files data from the user's social account. 
 * The data will be normalized into LoginRadius data format.
 */

public class AudioAPI 
{

	private static final String[] providers = {"vk", "msn"};
	
	/**
	 * Gives user's audio data on social providers
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token, final AsyncHandler<LoginRadiusAudio[]> handler) {
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getAudio(Endpoint.API_V2_AUDIO,token.access_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusAudio[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusAudio[] response) {
						handler.onSuccess(response);
					}

				});
	}
}
