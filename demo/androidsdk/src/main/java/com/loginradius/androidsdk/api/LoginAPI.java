package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class LoginAPI {
    public void getResponse(LoginParams value, final AsyncHandler<LoginData> handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            params.put("phone", value.getPhone());
            params.put("loginurl",value.getLoginUrl());
            params.put("smstemplate",value.getSmsTemplate());
        }else if(value.username!=null){
            params.put("username", value.getUsername());
            params.put("verificationUrl", value.getVerificationUrl());
            params.put("emailTemplate", value.getEmailTemplate());
        }else {
            params.put("email", value.getEmail());
            params.put("verificationUrl", value.getVerificationUrl());
            params.put("emailTemplate", value.getEmailTemplate());
        }
            params.put("password", value.getPassword());

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginData>() {
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
                    public void onNext(LoginData response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
