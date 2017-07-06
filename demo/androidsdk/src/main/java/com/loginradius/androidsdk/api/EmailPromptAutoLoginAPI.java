package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by loginradius on 11-May-17.
 */

public class EmailPromptAutoLoginAPI {

    public void getResponse(LoginParams value, final AsyncHandler<RegisterResponse> handler) {
        String autoLoginEmailTemplate = (value.getAutoLoginEmailTemplate()!=null) ? value.getAutoLoginEmailTemplate() : "";
        String welcomeEmailTemplate = (value.getWelcomeEmailTemplate()!=null) ? value.getWelcomeEmailTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.getUsername()!=null){
            params.put("username",value.getUsername());
        }else {params.put("email", value.getEmail());}

        params.put("clientGuid", value.getClientGuid());
        params.put("autoLoginEmailTemplate", autoLoginEmailTemplate);
        params.put("welcomeEmailTemplate", welcomeEmailTemplate);




        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getEmailPromptAutoLogin(Endpoint.API_V2_EMAIL_PROMPT_AUTO_LOGIN,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RegisterResponse>() {
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
                    public void onNext(RegisterResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
