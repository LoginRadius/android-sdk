package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;

import java.io.IOException;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class TraditionalInterfaceAPI {

	public void getResponse(LoginParams value,final AsyncHandler<List<UserRegisteration>> handler) {
		ApiInterface apiService = RestRequest.getClientCdn().create(ApiInterface.class);
		apiService.getTraditionalInterface(Endpoint.API_V2_RAASINTERFACE_URL+value.apikey+".json").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
			.subscribe(new DisposableObserver<List<UserRegisteration>>() {
		@Override
		public void onComplete() {
		}

		@Override
		public void onError(Throwable e) {
			ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
			handler.onFailure(exceptionResponse.t, exceptionResponse.message);
		}

		@Override
		public void onNext(List<UserRegisteration> response) {
			handler.onSuccess(response);
		}

	});
}
}
