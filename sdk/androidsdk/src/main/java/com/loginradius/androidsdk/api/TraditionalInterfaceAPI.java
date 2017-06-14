package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
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
		public void onNext(List<UserRegisteration> response) {

		//	Type listType = new TypeToken<ArrayList<UserRegisteration>>(){}.getType();
		//	List<UserRegisteration> ragisterInterfacedata = new Gson().fromJson(response, listType);
			handler.onSuccess(response);
		}

	});
}
}
