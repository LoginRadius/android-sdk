package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.DeviceRegisterResponse;
import com.loginradius.androidsdk.response.PostResponse;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MFAPushAPI {


    private ApiInterface apiService = RestRequest.getClient ( ).create ( ApiInterface.class );

    public MFAPushAPI () {
        if (!LoginRadiusSDK.validate ( )) {
            throw new LoginRadiusSDK.InitializeException ( );
        }
    }


    public void registerDevice ( String Endpoint, JsonObject jsonData, final AsyncHandler<DeviceRegisterResponse> handler ) {
        Map<String, String> headers = new HashMap<> ( );
        headers.put ( "Content-Type", "application/json" );
        apiService.getRegisterDevice ( Endpoint, headers, jsonData ).subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) )
                .subscribe ( new DisposableObserver<DeviceRegisterResponse> ( ) {
                    @Override
                    public void onNext ( DeviceRegisterResponse deviceRegisterResponse ) {
                        handler.onSuccess ( deviceRegisterResponse );
                    }

                    @Override
                    public void onError ( Throwable e ) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException ( e );
                        handler.onFailure ( exceptionResponse.t, exceptionResponse.message );
                    }

                    @Override
                    public void onComplete () {

                    }
                } );
    }


    public void MfaPushVerification ( JsonObject data, QueryParams queryParams, final AsyncHandler<PostResponse> handler ) {

        Map<String, String> headers = new HashMap<> ( );
        headers.put ( "Content-Type", "application/json" );
        apiService.getMFAVerification ( Endpoint.API_V2_MFA_VERIFICATION, headers, QueryMapHelper.getMFAPushVerificationMAp ( queryParams ), data ).subscribeOn ( Schedulers.io ( ) ).observeOn ( AndroidSchedulers.mainThread ( ) )
                .subscribe ( new DisposableObserver<PostResponse> ( ) {
                    @Override
                    public void onComplete () {
                    }

                    @Override
                    public void onError ( Throwable e ) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException ( e );
                        handler.onFailure ( exceptionResponse.t, exceptionResponse.message );

                    }

                    @Override
                    public void onNext ( PostResponse response ) {
                        handler.onSuccess ( response );
                    }

                } );
    }


}
