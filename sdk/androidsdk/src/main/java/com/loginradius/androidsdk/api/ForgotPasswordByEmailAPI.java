package com.loginradius.androidsdk.api;



import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.password.ForgotResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ForgotPasswordByEmailAPI {
    public void getResponse(LoginParams value, ForgotPasswordData data , final AsyncHandler<ForgotResponse> handler)
    {
        String resetPasswordUrl = (value.getResetPasswordUrl()!=null) ? value.getResetPasswordUrl() : "";
        String emailTemplate = (value.getEmailTemplate()!=null) ? value.getEmailTemplate() : "";
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.apikey);
        params.put("resetPasswordUrl", resetPasswordUrl);
        params.put("emailTemplate", emailTemplate);


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getForgotPasswordByEmail(Endpoint.API_V2_FORGOTPASSWORD_EMAIL,params,data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ForgotResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(ForgotResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }


}
