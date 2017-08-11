package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.phone.PhoneDataResponse;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 27-Jul-17.
 */

public class SimplifiedRegistrationByPhoneAPI {

    public void getResponse(LoginParams value, final AsyncHandler<PhoneDataResponse> handler) {
        String name = (value.getName()!=null) ? value.getName() : "";
        String smstemplate = (value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("phone", value.getPhone());
        params.put("name", name);
        params.put("smstemplate", smstemplate);


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getSimplifiedRegistrationByPhone(Endpoint.API_V2_SIMPLIFIED_REGISTRATION+"/phone",params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneDataResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(PhoneDataResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
