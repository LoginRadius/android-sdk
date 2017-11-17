package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.UpdateResponse;
import com.loginradius.androidsdk.response.login.LoginData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 10/16/2017.
 */

public class InstantLoginAPI {
    private ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);

    public InstantLoginAPI() {
        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void instantLoginByEmail(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        apiService.getInstantLogin(Endpoint.API_V2_ONECLICKSIGNIN, QueryMapHelper.getMapInstantLoginByEmail(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onNext(UpdateResponse value) {
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

    public void instantLoginByUsername(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        apiService.getInstantLogin(Endpoint.API_V2_ONECLICKSIGNIN, QueryMapHelper.getMapInstantLoginByUsername(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onNext(UpdateResponse value) {
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

    public void instantLoginLinkVerify(QueryParams queryParams, final AsyncHandler<LoginData> handler){
        apiService.getInstantLoginVerify(Endpoint.API_V2_ONECLICKVERIFY, QueryMapHelper.getMapInstantLoginVerify(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginData>() {
                    @Override
                    public void onNext(LoginData value) {
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
