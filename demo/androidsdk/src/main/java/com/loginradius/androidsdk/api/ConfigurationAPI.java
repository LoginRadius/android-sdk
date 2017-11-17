package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.config.ConfigResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 10/23/2017.
 */

public class ConfigurationAPI {
    public ConfigurationAPI() {
        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void getResponse(final AsyncHandler<ConfigResponse> handler){
        ApiInterface apiService = RestRequest.getClientCloud().create(ApiInterface.class);
        apiService.getConfiguration(Endpoint.API_V2_CONFIG,LoginRadiusSDK.getApiKey()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ConfigResponse>() {
                    @Override
                    public void onNext(ConfigResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
