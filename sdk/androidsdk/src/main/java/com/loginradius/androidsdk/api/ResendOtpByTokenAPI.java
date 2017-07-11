package com.loginradius.androidsdk.api;


import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.phone.PhoneResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ResendOtpByTokenAPI {
    public void getResponse(lrAccessToken token,String apikey, final AsyncHandler<PhoneResponse> handler)
    {

        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", apikey);
        params.put("access_token", token.access_token);

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getResendotpbytoken(Endpoint.API_V2_VERIFY_OTP,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(PhoneResponse  response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
