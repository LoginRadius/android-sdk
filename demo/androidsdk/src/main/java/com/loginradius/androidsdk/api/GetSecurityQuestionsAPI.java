package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.GetSecurityQuestionsResponse;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 02-Aug-17.
 */

public class GetSecurityQuestionsAPI {

    public void getResponse(LoginParams value, final AsyncHandler<GetSecurityQuestionsResponse[]> handler)
    {
        String access_token = (value.getAccess_token()!=null) ? value.getAccess_token() : "";
        String urlEndpoint="";

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            params.put("phone", value.getPhone());
            urlEndpoint="phone";
        }else if(value.username!=null){
            params.put("username", value.getUsername());
            urlEndpoint="username";
        }else if (value.email!=null){
            params.put("email", value.getEmail());
            urlEndpoint="email";
        }else {
            params.put("access_token", access_token);
            urlEndpoint="accesstoken";
        }

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.GetSecurityQuestions(Endpoint.API_V2_GET_SECURITY_QUESTIONS+"/"+urlEndpoint,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GetSecurityQuestionsResponse[]>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(GetSecurityQuestionsResponse[] response) {
                        handler.onSuccess(response);
                    }

                });
    }
}

