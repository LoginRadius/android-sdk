package com.loginradius.androidsdk.api;



import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class OtpVerificationAPI {

    public void getResponse(LoginParams value, JsonObject data , final AsyncHandler<LoginData> handler)
    {
        String url=null;
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", value.getApikey());
        if (value.otp!=null) {
            params.put("otp",value.getOtp());
           url= Endpoint.API_V2_VERIFY_OTP;
        }else {
            url= Endpoint.API_V2_FORGOTPASSWORD_PHONE;
        }

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getOtpVerification(url,params,data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
