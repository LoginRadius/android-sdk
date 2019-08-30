package com.loginradius.androidsdk.api;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.model.ChangePINModel;
import com.loginradius.androidsdk.model.PINRequiredModel;
import com.loginradius.androidsdk.model.ResetPINByResetToken;
import com.loginradius.androidsdk.model.ResetPINByEmailModel;
import com.loginradius.androidsdk.model.ResetPINByPhoneModel;
import com.loginradius.androidsdk.model.ResetPINByUserNameModel;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.PostResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.phone.PhoneResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PinAuthenticationAPI {
    private ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);


    public PinAuthenticationAPI() {
        if (!LoginRadiusSDK.validate()) {
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void loginByPIN(QueryParams queryParams, PINRequiredModel data, final AsyncHandler<LoginData> handler){
        apiService.getLoginByPin(Endpoint.API_V2_PIN_LOGIN, QueryMapHelper.getMapLoginByPin(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginData>() {
                    @Override
                    public void onNext(LoginData value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setPINByPinAuthToken(String pinAuthToken , PINRequiredModel data, final AsyncHandler<LoginData> handler){
        apiService.getPINByPinAuthToken(Endpoint.API_V2_PIN_BY_PINAuth, QueryMapHelper.getMapSetPINByPinAuthToken(pinAuthToken),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginData>() {
                    @Override
                    public void onNext(LoginData value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void forgotPINByEmail(QueryParams queryParams , String email , final AsyncHandler<PostResponse> handler){
        JsonObject data = new JsonObject();
        data.addProperty("email",email);
        apiService.getForgotPINByEmail(Endpoint.API_V2_PIN_FORGOT_BY_EMAIL, QueryMapHelper.getMapForgotPINByEmail(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void forgotPINByUserName(QueryParams queryParams , String userName , final AsyncHandler<PostResponse> handler){
        JsonObject data = new JsonObject();
        data.addProperty("username",userName);
        apiService.getForgotPINByUserName(Endpoint.API_V2_PIN_FORGOT_BY_USERNAME, QueryMapHelper.getMapForgotPINByEmail(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void forgotPINByPhone(String phone, String smsTemplate , final AsyncHandler<PhoneResponse> handler){
        JsonObject data = new JsonObject();
        data.addProperty("phone",phone);
        apiService.getForgotPINByPhone(Endpoint.API_V2_PIN_FORGOT_BY_PHONE, QueryMapHelper.getMapForgotPINByPhone(smsTemplate),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneResponse>() {
                    @Override
                    public void onNext(PhoneResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void invalidatePINSessionToken(String sessionToken , final AsyncHandler<PostResponse> handler){
        apiService.getInvalidatePINSessionToken(Endpoint.API_V2_PIN_INVALIDATE_SESSION_TOKEN, QueryMapHelper.getinvalidatePINSessionTokenMap(sessionToken)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void resetPINByEmailAndOTP(ResetPINByEmailModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByEmail(Endpoint.API_V2_PIN_RESET_EMAIL_OTP, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void resetPINByPhoneAndOTP(ResetPINByPhoneModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByPhone(Endpoint.API_V2_PIN_RESET_PHONE_OTP, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void resetPINByUserNameAndOTP(ResetPINByUserNameModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByUserName(Endpoint.API_V2_PIN_RESET_USERNAME_OTP, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void resetPINByResetToken(ResetPINByResetToken data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByResetToken(Endpoint.API_V2_PIN_RESET_BY_RESET_TOKEN, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void resetPINByEmailAndSecurityQuestion(ResetPINByEmailModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByEmail(Endpoint.API_V2_PIN_RESET_EMAIL_SECURITY_QUESTION, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void resetPINByPhoneAndSecurityQuestion(ResetPINByPhoneModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByPhone(Endpoint.API_V2_PIN_RESET_PHONE_SECURITY_QUESTION, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void resetPINByUserNameAndSecurityQuestion(ResetPINByUserNameModel data, final AsyncHandler<PostResponse> handler){
        apiService.getResetPINByUserName(Endpoint.API_V2_PIN_RESET_USERNAME_SECURITY_QUESTION, QueryMapHelper.getMapPINReset(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void changePINByAccessToken(String accessToken, ChangePINModel data,  final AsyncHandler<PostResponse> handler){
        apiService.getChangePINByAccessToken(Endpoint.API_V2_PIN_CHANGE_BY_ACCESS_TOKEN, QueryMapHelper.getMapChangePINByAccessToken(accessToken),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostResponse>() {
                    @Override
                    public void onNext(PostResponse value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
