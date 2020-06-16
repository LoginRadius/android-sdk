package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
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
import com.loginradius.androidsdk.response.phonesendotp.PhoneSendOtpData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 10/16/2017.
 */

public class PasswordlessLoginAPI {
    private ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);

    public PasswordlessLoginAPI() {
        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void loginByEmail(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        apiService.getPasswordlessLogin(Endpoint.API_V2_PASSWORDLESSLOGIN, QueryMapHelper.getMapPasswordlessLoginByEmail(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void loginByPhone(QueryParams queryParams, JsonObject securityanswer, final AsyncHandler<LoginData> handler){
        JsonObject data = new JsonObject();
        data.addProperty("phone",queryParams.getPhone());
        data.addProperty("otp",queryParams.getOtp());
        if(securityanswer!=null){
            data.add("securityanswer",securityanswer);
        }
        apiService.getPasswordlessLoginByPhone(Endpoint.API_V2_PASSWORDLESSVERIFYOTP, QueryMapHelper.getMapPasswordlessLoginByPhone(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void loginByUsername(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        apiService.getPasswordlessLogin(Endpoint.API_V2_PASSWORDLESSLOGIN, QueryMapHelper.getMapPasswordlessLoginByUsername(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void sendOtpToPhone(QueryParams queryParams, final AsyncHandler<PhoneSendOtpData> handler){
        apiService.getPhoneSendOtp(Endpoint.API_V2_PASSWORDLESSSENDOTP, QueryMapHelper.getMapPhoneSendOtp(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneSendOtpData>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(PhoneSendOtpData response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void verifyLink(QueryParams queryParams, final AsyncHandler<LoginData> handler){
        apiService.getPasswordlessLoginVerify(Endpoint.API_V2_PASSWORDLESSVERIFY, QueryMapHelper.getMapPasswordlessLoginVerify(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
