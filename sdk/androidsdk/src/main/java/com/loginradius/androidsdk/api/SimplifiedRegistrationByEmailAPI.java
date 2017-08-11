package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 27-Jul-17.
 */

public class SimplifiedRegistrationByEmailAPI {

    public void getResponse(LoginParams value, final AsyncHandler<RegisterResponse> handler) {
        String name = (value.getName()!=null) ? value.getName() : "";
        String redirecturl = (value.getRedirecturl()!=null) ? value.getRedirecturl() : "";
        String noregistrationemailtemplate=(value.getNoregistrationemailtemplate()!=null) ? value.getNoregistrationemailtemplate() : "";
        String welcomeemailtemplate=(value.getWelcomeEmailTemplate()!=null) ? value.getWelcomeEmailTemplate() : "";


        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("email", value.getEmail());
        params.put("clientguid", value.getClientGuid());
        params.put("name", name);
        params.put("redirecturl", redirecturl);
        params.put("noregistrationemailtemplate", noregistrationemailtemplate);
        params.put("welcomeemailtemplate", welcomeemailtemplate);


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getSimplifiedRegistrationByEmail(Endpoint.API_V2_SIMPLIFIED_REGISTRATION+"/email",params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

                });
    }
}
