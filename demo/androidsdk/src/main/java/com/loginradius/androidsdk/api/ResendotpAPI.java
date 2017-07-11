package com.loginradius.androidsdk.api;


import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;

import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ResendotpAPI {
    public void getResponse(LoginParams value, JsonObject data , final AsyncHandler<RegisterResponse> handler)
    {

        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getResendotp(Endpoint.API_V2_VERIFY_OTP,params,data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RegisterResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(RegisterResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
