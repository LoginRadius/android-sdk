package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;

import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;

import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.company.LoginRadiusCompany;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.io.IOException;
import java.util.Arrays;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 *The Company API is used to get the followed company's data in the user's social account. 
 *The data will be normalized into LoginRadius data format.
 * 
 */

public class CompanyAPI 
{
	private static final String[] providers = {"linkedin", "facebook"};

	/**
	 * Get company names followed by user
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusCompany[]> handler) {
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getCompany(Endpoint.API_V2_COMPANY,token.access_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusCompany[]>() {
					@Override
					public void onComplete() {
					}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusCompany[] response) {
						handler.onSuccess(response);
					}

				});
	}
}