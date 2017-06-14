package com.loginradius.androidsdk.api;


import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;

import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.register.DeleteResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by loginradius on 8/29/2016.
 */
public class RemoveEmailAPI {


    public void getResponse(LoginParams value, lrAccessToken token,  JsonObject delete , final AsyncHandler<DeleteResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("access_token",token.access_token);

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getRemoveEmail(Endpoint.API_V2_ADD_EMAIL,params,delete).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DeleteResponse>() {
                    @Override
                    public void onComplete() {}

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
                    public void onNext(DeleteResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
