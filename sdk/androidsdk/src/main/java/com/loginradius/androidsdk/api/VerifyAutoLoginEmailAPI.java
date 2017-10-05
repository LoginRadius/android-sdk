package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.VerifyAutoLoginResponse;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 8/28/2017.
 */

public class VerifyAutoLoginEmailAPI {

    public void getResponse(LoginParams value, final AsyncHandler<VerifyAutoLoginResponse> handler){
        String welcomeEmailTemplate = (value.getWelcomeEmailTemplate()!=null) ? value.getWelcomeEmailTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("vtoken",value.getVtoken());
        params.put("welcomeEmailTemplate", welcomeEmailTemplate);

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getVerifyAutoLoginEmail(Endpoint.API_V2_VERIFY_AUTO_LOGIN_EMAIL,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
