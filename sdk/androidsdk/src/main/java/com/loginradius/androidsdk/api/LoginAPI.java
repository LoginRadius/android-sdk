package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class LoginAPI {
    public void getResponse(LoginParams value, final AsyncHandler<LoginData> handler) {
        String verificationUrl = (value.getVerificationUrl()!=null) ? value.getVerificationUrl() : "";
        String emailTemplate=(value.getEmailTemplate()!=null) ? value.getEmailTemplate() : "";
        String smstemplate=(value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";
        String loginurl=(value.getLoginUrl()!=null) ? value.getLoginUrl() : "";

        JsonObject object = new JsonObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            //params.put("phone", value.getPhone());
            object.addProperty("phone",value.getPhone());
            params.put("loginurl",loginurl);
            params.put("smstemplate",smstemplate);
        }else if(value.username!=null){
            //params.put("username", value.getUsername());
            object.addProperty("username",value.getUsername());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }else {
            //params.put("email", value.getEmail());
            object.addProperty("email",value.getEmail());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }
            //params.put("password", value.getPassword());
            object.addProperty("password",value.getPassword());

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN,params,object).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void getResponse(LoginParams value,String fields[],final AsyncHandler<LoginData> handler) {
        String verificationUrl = (value.getVerificationUrl()!=null) ? value.getVerificationUrl() : "";
        String emailTemplate=(value.getEmailTemplate()!=null) ? value.getEmailTemplate() : "";
        String smstemplate=(value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";
        String loginurl=(value.getLoginUrl()!=null) ? value.getLoginUrl() : "";

        JsonObject object = new JsonObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            //params.put("phone", value.getPhone());
            object.addProperty("phone",value.getPhone());
            params.put("loginurl",loginurl);
            params.put("smstemplate",smstemplate);
        }else if(value.username!=null){
            //params.put("username", value.getUsername());
            object.addProperty("username",value.getUsername());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }else {
            //params.put("email", value.getEmail());
            object.addProperty("email",value.getEmail());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }
        //params.put("password", value.getPassword());
        object.addProperty("password",value.getPassword());

        String strFields = null;
        if(fields!=null && fields.length>0){
            strFields = "";
            for(int i=0;i<fields.length;i++){
                if(i == (fields.length-1)){
                    strFields = strFields + fields[i];
                }else{
                    strFields = strFields + fields[i] + ",";
                }
            }
        }

        if(strFields!=null){
            params.put("fields",strFields);
        }

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN,params,object).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void getResponse(LoginParams value,JsonObject securityAnswer,final AsyncHandler<LoginData> handler){
        String verificationUrl = (value.getVerificationUrl()!=null) ? value.getVerificationUrl() : "";
        String emailTemplate=(value.getEmailTemplate()!=null) ? value.getEmailTemplate() : "";
        String smstemplate=(value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";
        String loginurl=(value.getLoginUrl()!=null) ? value.getLoginUrl() : "";

        JsonObject object = new JsonObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            //params.put("phone", value.getPhone());
            object.addProperty("phone",value.getPhone());
            params.put("loginurl",loginurl);
            params.put("smstemplate",smstemplate);
        }else if(value.username!=null){
            //params.put("username", value.getUsername());
            object.addProperty("username",value.getUsername());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }else {
            //params.put("email", value.getEmail());
            object.addProperty("email",value.getEmail());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }
        //params.put("password", value.getPassword());
        object.addProperty("password",value.getPassword());

        object.add("securityanswer",securityAnswer);

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN,params,object).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void getResponse(LoginParams value,JsonObject securityAnswer,String fields[],final AsyncHandler<LoginData> handler){
        String verificationUrl = (value.getVerificationUrl()!=null) ? value.getVerificationUrl() : "";
        String emailTemplate=(value.getEmailTemplate()!=null) ? value.getEmailTemplate() : "";
        String smstemplate=(value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";
        String loginurl=(value.getLoginUrl()!=null) ? value.getLoginUrl() : "";

        JsonObject object = new JsonObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        if (value.phone!=null) {
            //params.put("phone", value.getPhone());
            object.addProperty("phone",value.getPhone());
            params.put("loginurl",loginurl);
            params.put("smstemplate",smstemplate);
        }else if(value.username!=null){
            //params.put("username", value.getUsername());
            object.addProperty("username",value.getUsername());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }else {
            //params.put("email", value.getEmail());
            object.addProperty("email",value.getEmail());
            params.put("verificationUrl", verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }
        //params.put("password", value.getPassword());
        object.addProperty("password",value.getPassword());

        object.add("securityanswer",securityAnswer);

        String strFields = null;
        if(fields!=null && fields.length>0){
            strFields = "";
            for(int i=0;i<fields.length;i++){
                if(i == (fields.length-1)){
                    strFields = strFields + fields[i];
                }else{
                    strFields = strFields + fields[i] + ",";
                }
            }
        }

        if(strFields!=null){
            params.put("fields",strFields);
        }

        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN,params,object).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
