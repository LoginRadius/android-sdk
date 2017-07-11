package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;

import com.loginradius.androidsdk.response.lrAccessToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by loginradius on 31-May-17.
 */

public class ValidateAccessTokenAPI {
    public void getResponse(lrAccessToken token,String apikey,final AsyncHandler<lrAccessToken> handler) {

        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", apikey);
        params.put("access_token",token.access_token);


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getValidateAccessToken(Endpoint.API_V2_VALIDATE_ACCESS_TOKEN,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<lrAccessToken>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(lrAccessToken response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
