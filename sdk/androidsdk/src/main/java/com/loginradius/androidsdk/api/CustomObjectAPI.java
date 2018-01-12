package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.customobject.CreateCustomObject;
import com.loginradius.androidsdk.response.customobject.ReadCustomObject;
import com.loginradius.androidsdk.response.register.DeleteResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 13-Oct-17.
 */

public class CustomObjectAPI {
    ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
    public CustomObjectAPI() {
        if (!LoginRadiusSDK.validate()) {
            throw new LoginRadiusSDK.InitializeException();
        }
    }
    public void createCustomObject(QueryParams queryParams, JsonObject update , final AsyncHandler<CreateCustomObject> handler) {
        apiService.getCreateCustomObject(Endpoint.API_V2_CUSTOMOBJECT, QueryMapHelper.getMapCreateCustomObject(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CreateCustomObject>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(CreateCustomObject response) {
                        handler.onSuccess(response);
                    }

                });}

    public void deleteCustomObject(QueryParams queryParams, final AsyncHandler<DeleteResponse> handler) {
        String url= Endpoint.API_V2_CUSTOMOBJECT+"/"+queryParams.getObjectRecordId();
        apiService.getDeleteCustomObject(url, QueryMapHelper.getMapDeleteCustomObject(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DeleteResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(DeleteResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }



    public void readCustomObjectById(QueryParams queryParams, final AsyncHandler<CreateCustomObject> handler) {
        String url= Endpoint.API_V2_CUSTOMOBJECT+"/"+queryParams.getObjectRecordId();
        apiService.getReadCustomobjectById(url, QueryMapHelper.getMapReadCustomObjectbyId(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CreateCustomObject>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(CreateCustomObject response) {
                        handler.onSuccess(response);
                    }

                });}

    public void readCustomObjectByToken(QueryParams queryParams, final AsyncHandler<ReadCustomObject> handler) {
        apiService.getReadCustomobjectByToken(Endpoint.API_V2_CUSTOMOBJECT, QueryMapHelper.getMapReadCustomobjectByToken(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ReadCustomObject>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(ReadCustomObject response) {
                        handler.onSuccess(response);
                    }

                });
    }



    public void updateCustomObject(QueryParams queryParams, JsonObject update , final AsyncHandler<CreateCustomObject> handler) {
        String url = Endpoint.API_V2_CUSTOMOBJECT+"/"+queryParams.getObjectRecordId();
        apiService.getUpdateCustomObject(url, QueryMapHelper.getMapUpdateCustomObject(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CreateCustomObject>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(CreateCustomObject response) {
                        handler.onSuccess(response);
                    }

                });
    }

}
