package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by loginradius on 11-May-17.
 */

public class EmailPromptAutoLoginPingAPI {
    public void getResponse(LoginParams value, final AsyncHandler<LoginData> handler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("clientGuid", value.getClientGuid());

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getEmailPromptAutoLoginPing(Endpoint.API_V2_EMAIL_PROMPT_AUTO_LOGIN+"/ping",params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
}
