package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.VerifyAutoLoginResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 13-Oct-17.
 */

public class SmartLoginAPI {

    ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
    public SmartLoginAPI() {
        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void login(QueryParams queryParams, final AsyncHandler<RegisterResponse> handler) {
        apiService.getSmartLogin(Endpoint.API_V2_SMART_LOGIN, QueryMapHelper.getMapSmartLoginByEmail(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

                });}

    public void ping(QueryParams queryParams, final AsyncHandler<LoginData> handler) {
        apiService.getSmartLoginPing(Endpoint.API_V2_SMART_LOGIN +"/ping", QueryMapHelper.getMapSmartLoginPing(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginData>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(LoginData response) {
                        handler.onSuccess(response);
                    }

                });
    }



    public void verifyToken(QueryParams queryParams, final AsyncHandler<VerifyAutoLoginResponse> handler){
        apiService.getSmartLoginVerifyToken(Endpoint.API_V2_VERIFY_SMART_LOGIN, QueryMapHelper.getMapSmartLoginVerifyToken(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<VerifyAutoLoginResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(VerifyAutoLoginResponse value) {
                        handler.onSuccess(value);
                    }
                });
    }
}
