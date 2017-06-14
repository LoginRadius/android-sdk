package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;


import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ForgotPasswordByPhoneNumberAPI {
    public void getResponse(LoginParams value, ForgotPasswordData data , final AsyncHandler<PhoneForgotPasswordResponse> handler)
    {
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());
        if (value.smsTemplate!=null) {
            params.put("smsTemplate",value.getSmsTemplate());
        }


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getForgotPasswordByPhone(Endpoint.API_V2_FORGOTPASSWORD_PHONE,params,data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneForgotPasswordResponse>() {
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
                    public void onNext(PhoneForgotPasswordResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
