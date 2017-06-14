package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.http.Header;


public class SocailInterfaceAPI {


	public void getResponse(LoginParams value, final AsyncHandler<SocialInterface> handler) {

		ApiInterface apiService = RestRequest.getClientCdn().create(ApiInterface.class);
		apiService.getSocailProviderInterface(Endpoint.API_V2_SOCIALINTERFACE_URL+value.apikey+".json").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<SocialInterface>() {
					@Override
					public void onComplete() {
					}

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
					public void onNext(SocialInterface response) {
					//	response=response.replace("loginRadiusAppJsonLoaded(","");
					//response=response.substring(0,response.length()-1);
					//	SocialInterface socialInterface = JsonDeserializer.deserializeJson(response.toString(),SocialInterface.class);
						handler.onSuccess(response);
					}

				});
	}
}