package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.CheckAvailability;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.phone.PhoneResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by loginradius on 8/26/2016.
 */
public class PhoneNumberAvailabilityAPI {

    public void getResponse(LoginParams value, final AsyncHandler<CheckAvailability> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("phone",value.phone);

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getPhoneNumberAvailability(Endpoint.API_V2_UPDATE_PHONE,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckAvailability>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(CheckAvailability response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
