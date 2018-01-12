package com.loginradius.androidsdk.api;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.activity.RequiredFieldsActivity;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.LoginDataHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.CheckAvailability;
import com.loginradius.androidsdk.response.DeleteAccountResponse;
import com.loginradius.androidsdk.response.securityquestions.SecurityQuestionsResponse;
import com.loginradius.androidsdk.response.UpdateResponse;
import com.loginradius.androidsdk.response.VerifyEmailResponse;
import com.loginradius.androidsdk.response.config.ConfigResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.password.ForgotPasswordResponse;
import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;
import com.loginradius.androidsdk.response.phone.PhoneResponse;
import com.loginradius.androidsdk.response.phonesendotp.PhoneSendOtpData;
import com.loginradius.androidsdk.response.register.DeleteResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.register.RegistrationData;
import com.loginradius.androidsdk.response.securityquestions.UpdateSecurityQuestionsResponse;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loginradius on 13-Oct-17.
 */

public class AuthenticationAPI {
    private ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
    private boolean askRequiredFieldsOnTraditionalLogin = true;

    public AuthenticationAPI() {
        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void addEmail(QueryParams queryParams, JsonObject update, final AsyncHandler<RegisterResponse> handler){
        apiService.getAddEmail(Endpoint.API_V2_ADD_EMAIL, QueryMapHelper.getMapAddEmail(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void changePassword(QueryParams queryParams, JsonObject change, final AsyncHandler<RegisterResponse> handler){
        String confirmPassword = change.get("ConfirmPassword").getAsString().trim();
        String newPassword = change.get("NewPassword").getAsString().trim();
        if(!confirmPassword.equals(newPassword)){
            throw new IllegalArgumentException("Passwords don't match");
        }
        apiService.getChangePassword(Endpoint.API_V2_FORGOTPASSWORD_EMAIL, QueryMapHelper.getMapChangePassword(queryParams),change).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void deleteAccount(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        apiService.getDeleteAccount(Endpoint.API_V2_DELETE_ACCOUNT, QueryMapHelper.getMapDeleteAccount(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onNext(UpdateResponse value) {
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

    public void deleteAccountByConfirmEmail(QueryParams queryParams, final AsyncHandler<DeleteAccountResponse> handler){
        apiService.getDeleteAccountByConfirmEmail(Endpoint.API_V2_USERPROFILE, QueryMapHelper.getMapDeleteAccountByConfirmEmail(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DeleteAccountResponse>() {
                    @Override
                    public void onNext(DeleteAccountResponse value) {
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

    public void checkEmailAvailability(QueryParams queryParams, final AsyncHandler<CheckAvailability> handler){
        apiService.getEmailAvailability(Endpoint.API_V2_VERIFY_EMAIL, QueryMapHelper.getMapEmailAvailability(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckAvailability>() {
                    @Override
                    public void onNext(CheckAvailability value) {
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

    public void forgotPasswordByEmail(QueryParams queryParams,final AsyncHandler<ForgotPasswordResponse> handler){
        JsonObject data = new JsonObject();
        data.addProperty("email",queryParams.getEmail());
        apiService.getForgotPasswordByEmail(Endpoint.API_V2_FORGOTPASSWORD_EMAIL, QueryMapHelper.getMapForgotPasswordByEmail(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(ForgotPasswordResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void forgotPasswordByPhone(QueryParams queryParams,final AsyncHandler<PhoneForgotPasswordResponse> handler){
        JsonObject data = new JsonObject();
        data.addProperty("phone",queryParams.getPhone());
        apiService.getForgotPasswordByPhone(Endpoint.API_V2_FORGOTPASSWORD_PHONE, QueryMapHelper.getMapForgotPasswordByPhone(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneForgotPasswordResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(PhoneForgotPasswordResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void getSecurityQuestions(QueryParams queryParams, final AsyncHandler<SecurityQuestionsResponse[]> handler){
        String urlEndpoint="";
        if (queryParams.getPhone()!=null) {
            urlEndpoint="phone";
        }else if(queryParams.getUsername()!=null){
            urlEndpoint="username";
        }else if (queryParams.getEmail()!=null){
            urlEndpoint="email";
        }else {
            urlEndpoint="accesstoken";
        }
        apiService.getSecurityQuestions(Endpoint.API_V2_GET_SECURITY_QUESTIONS+"/"+urlEndpoint, QueryMapHelper.getMapSecurityQuestions(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SecurityQuestionsResponse[]>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(SecurityQuestionsResponse[] response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void getSocialProfile(QueryParams queryParams, final AsyncHandler<LoginRadiusUltimateUserProfile> handler){
        apiService.getSocialProfile(Endpoint.API_V2_SOCIALIDENTITIES, QueryMapHelper.getMapSocialProfile(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusUltimateUserProfile>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(LoginRadiusUltimateUserProfile response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void invalidateAccessToken(QueryParams queryParams, final AsyncHandler<RegisterResponse> handler){
        apiService.getInvalidateAccessToken(Endpoint.API_V2_INVALIDATE_ACCESS_TOKEN, QueryMapHelper.getMapInvalidateAccessToken(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void linkAccount(QueryParams queryParams, JsonObject change, final AsyncHandler<RegisterResponse> handler){
        apiService.getLinking(Endpoint.API_V2_SOCIALIDENTITIES, QueryMapHelper.getMapLink(queryParams),change).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void loginWithSecurityQuestion(final Context context, final QueryParams queryParams, JsonObject securityAnswer, final AsyncHandler<LoginData> handler){
        JsonObject object = new JsonObject();
        if(queryParams.getPhone()!=null){
            object.addProperty("phone",queryParams.getPhone());
        }else if(queryParams.getUsername()!=null){
            object.addProperty("username",queryParams.getUsername());
        }else {
            object.addProperty("email",queryParams.getEmail());
        }
        object.addProperty("password",queryParams.getPassword());
        if(securityAnswer!=null){
            object.add("securityanswer",securityAnswer);
        }
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN, QueryMapHelper.getMapLogin(queryParams),object).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                        validateLoginConfiguration(context,queryParams,response,handler);
                    }
                });
    }

    private void validateLoginConfiguration(final Context context, final QueryParams queryParams, final LoginData response, final AsyncHandler<LoginData> handler) {
        if(askRequiredFieldsOnTraditionalLogin){
            ConfigurationAPI api = new ConfigurationAPI();
            api.getResponse(new AsyncHandler<ConfigResponse>() {
                @Override
                public void onSuccess(ConfigResponse data) {
                    if(data.getAskRequiredFieldsOnTraditionalLogin()){
                        Intent intent = new Intent(context, RequiredFieldsActivity.class);
                        intent.putExtra("apikey",LoginRadiusSDK.getApiKey());
                        intent.putExtra("verificationurl",LoginRadiusSDK.getVerificationUrl());
                        intent.putExtra("fieldsColor",queryParams.getFieldsColor());
                        LoginDataHandler loginDataHandler = LoginDataHandler.getInstance();
                        loginDataHandler.setResponse(response);
                        loginDataHandler.setHandler(handler);
                        context.startActivity(intent);
                    }else{
                        handler.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable error, String errorcode) {
                    handler.onFailure(error,errorcode);
                }
            });
        }else{
            handler.onSuccess(response);
        }
    }

    public void login(final Context context, final QueryParams queryParams, final AsyncHandler<LoginData> handler){
        loginWithSecurityQuestion(context,queryParams,null,handler);
    }

    public void verifyOtp(QueryParams queryParams, JsonObject data, final AsyncHandler<LoginData> handler){
        String url = Endpoint.API_V2_VERIFY_OTP;
        apiService.getOtpVerification(url, QueryMapHelper.getMapOtpVerification(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void verifyOtpForgotPassword(JsonObject data, final AsyncHandler<RegisterResponse> handler){
        String newPassword = data.get("password").getAsString();
        String confirmPassword = data.get("confirmpassword").getAsString();
        if(!confirmPassword.equals(newPassword)){
            throw new IllegalArgumentException("Passwords don't match");
        }
        String url = Endpoint.API_V2_FORGOTPASSWORD_PHONE;
        apiService.getResetPasswordByOtp(url, QueryMapHelper.getMapOtpVerifyForgotPassword(),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void loginWithPhoneUsingOtp(QueryParams queryParams, final AsyncHandler<LoginData> handler){
        apiService.getTraditionalLogin(Endpoint.API_V2_LOGIN, QueryMapHelper.getMapPhoneLoginUsingOtp(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void checkPhoneAvailability(QueryParams queryParams, final AsyncHandler<CheckAvailability> handler){
        apiService.getPhoneNumberAvailability(Endpoint.API_V2_UPDATE_PHONE, QueryMapHelper.getMapPhoneAvailability(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckAvailability>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(CheckAvailability response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void sendOtpToPhone(QueryParams queryParams, final AsyncHandler<PhoneSendOtpData> handler){
        apiService.getphonesendOtp(Endpoint.API_V2_PHONESENDOTPAPI, QueryMapHelper.getMapPhoneSendOtp(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneSendOtpData>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(PhoneSendOtpData response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void register(QueryParams queryParams, RegistrationData registrationData, final AsyncHandler<RegisterResponse> handler){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-LoginRadius-Sott",LoginRadiusSDK.getSott());
        apiService.getTraditionalRegister(Endpoint.API_V2_REGISTER,headers, QueryMapHelper.getMapRegistration(queryParams),registrationData).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void register(QueryParams queryParams, JsonObject data, final AsyncHandler<RegisterResponse> handler){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-LoginRadius-Sott",LoginRadiusSDK.getSott());
        apiService.getTraditionalRegister(Endpoint.API_V2_REGISTER,headers, QueryMapHelper.getMapRegistration(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void removeEmail(QueryParams queryParams, JsonObject delete, final AsyncHandler<DeleteResponse> handler){
        apiService.getRemoveEmail(Endpoint.API_V2_ADD_EMAIL, QueryMapHelper.getMapRemoveEmail(queryParams),delete).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void resendEmailVerification(QueryParams queryParams, JsonObject update, final AsyncHandler<RegisterResponse> handler){
        apiService.getResendEmailVerification(Endpoint.API_V2_REGISTER, QueryMapHelper.getMapResendEmailVerification(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void resendOtp(QueryParams params, JsonObject data, final AsyncHandler<RegisterResponse> handler){
        apiService.getResendotp(Endpoint.API_V2_VERIFY_OTP, QueryMapHelper.getMapResendOtp(params),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void resendOtpByToken(QueryParams queryParams, JsonObject data, final AsyncHandler<PhoneResponse> handler){
        apiService.getResendotpbytoken(Endpoint.API_V2_VERIFY_OTP, QueryMapHelper.getMapResendOtpByToken(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(PhoneResponse  response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void resetPasswordByResetToken(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        String newPassword = queryParams.getPassword();
        String confirmPassword = queryParams.getConfirmPassword();
        if(!confirmPassword.equals(newPassword)){
            throw new IllegalArgumentException("Passwords don't match");
        }
        JsonObject update = new JsonObject();
        update.addProperty("resettoken",queryParams.getResetToken());
        update.addProperty("password",queryParams.getPassword());
        update.addProperty("welcomeemailtemplate",(queryParams.getWelcomeEmailTemplate()!=null ? queryParams.getWelcomeEmailTemplate():""));
        update.addProperty("resetpasswordemailtemplate",(queryParams.getResetPasswordEmailTemplate()!=null ? queryParams.getResetPasswordEmailTemplate():""));
        apiService.getResetPasswordByResetToken(Endpoint.API_V2_RESET_PASSWORD, QueryMapHelper.getMapResetPasswordToken(),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onNext(UpdateResponse value) {
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

    public void resetPasswordBySecurityQuestions(QueryParams queryParams, JsonObject update, final AsyncHandler<RegisterResponse> handler){
        String newPassword = queryParams.getPassword();
        String confirmPassword = queryParams.getConfirmPassword();
        if(!confirmPassword.equals(newPassword)){
            throw new IllegalArgumentException("Passwords don't match");
        }
        if(queryParams.getEmail()!=null){
            update.addProperty("email",queryParams.getEmail());
        }else if(queryParams.getPhone()!=null){
            update.addProperty("phone",queryParams.getPhone());
        }else if(queryParams.getUsername()!=null){
            update.addProperty("username",queryParams.getUsername());
        }
        update.addProperty("password",queryParams.getPassword());
        update.addProperty("resetpasswordemailtemplate",(queryParams.getResetPasswordEmailTemplate()!=null?queryParams.getResetPasswordEmailTemplate():""));
        apiService.getResetPasswordbySecurityQuestion(Endpoint.API_V2_RESET_PASSWORD_BY_SECURITY_QUESTION, QueryMapHelper.getMapResetPasswordSecurityQuestion(),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void readAllUserProfile(QueryParams queryParams, final AsyncHandler<LoginRadiusUltimateUserProfile> handler){
        apiService.getReadAllUserProfile(Endpoint.API_V2_USERPROFILE, QueryMapHelper.getMapReadAllUserProfile(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusUltimateUserProfile>() {
                    @Override
                    public void onNext(LoginRadiusUltimateUserProfile userProfile) {
                        handler.onSuccess(userProfile);
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

    public void unlinkAccount(QueryParams queryParams, JsonObject json, final AsyncHandler<DeleteResponse> handler){
        apiService.getUnlinking(Endpoint.API_V2_SOCIALIDENTITIES, QueryMapHelper.getMapUnlink(queryParams),json).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void updatePhone(QueryParams queryParams, JsonObject update, final AsyncHandler<PhoneResponse> handler){
        apiService.getUpdatephone(Endpoint.API_V2_UPDATE_PHONE, QueryMapHelper.getMapUpdatePhone(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(PhoneResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void updateProfile(QueryParams queryParams, JsonObject data, final AsyncHandler<RegisterResponse> handler){
        apiService.getUpdateprofile(Endpoint.API_V2_UPDATE_PROFILE, QueryMapHelper.getMapUpdateProfile(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void updateProfile(QueryParams queryParams, RegistrationData data, final AsyncHandler<RegisterResponse> handler){
        apiService.getUpdateprofile(Endpoint.API_V2_UPDATE_PROFILE, QueryMapHelper.getMapUpdateProfile(queryParams),data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void updateSecurityQuestionByAccessToken(QueryParams queryParams, JsonObject update , final AsyncHandler<UpdateSecurityQuestionsResponse> handler){
        apiService.getUpdateSecurityQuestionByAccessToken(Endpoint.API_V2_USERPROFILE, QueryMapHelper.getMapUpdateSecurityQuestionToken(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateSecurityQuestionsResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(UpdateSecurityQuestionsResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void updateUsername(QueryParams queryParams, final AsyncHandler<UpdateResponse> handler){
        JsonObject update = new JsonObject();
        update.addProperty("username",queryParams.getUsername());
        apiService.getUpdateUsername(Endpoint.API_V2_VERIFY_USERNAME, QueryMapHelper.getMapUpdateUsername(queryParams),update).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateResponse>() {
                    @Override
                    public void onNext(UpdateResponse value) {
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

    public void checkUsernameAvailability(QueryParams queryParams, final AsyncHandler<CheckAvailability> handler){
        apiService.getUsernameAvailability(Endpoint.API_V2_VERIFY_USERNAME, QueryMapHelper.getMapCheckUsername(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckAvailability>() {
                    @Override
                    public void onNext(CheckAvailability value) {
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

    public void validateAccessToken(QueryParams queryParams, final AsyncHandler<AccessTokenResponse> handler){
        apiService.getValidateAccessToken(Endpoint.API_V2_VALIDATE_ACCESS_TOKEN, QueryMapHelper.getMapValidateAccessToken(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AccessTokenResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(AccessTokenResponse response) {
                        handler.onSuccess(response);
                    }

                });
    }

    public void verifyEmail(QueryParams queryParams, final AsyncHandler<VerifyEmailResponse> handler){
        apiService.getVerifyEmail(Endpoint.API_V2_VERIFY_EMAIL, QueryMapHelper.getMapVerifyEmail(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<VerifyEmailResponse>(){
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }

                    @Override
                    public void onNext(VerifyEmailResponse value) {
                        handler.onSuccess(value);
                    }
                });
    }

    public void verifyOtpByToken(QueryParams queryParams, final AsyncHandler<RegisterResponse> handler){
        apiService.getVerifyOtp(Endpoint.API_V2_VERIFY_OTP, QueryMapHelper.getMapVerifyOtpByToken(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void setAskRequiredFieldsOnTraditionalLogin(boolean askRequiredFieldsOnTraditionalLogin) {
        this.askRequiredFieldsOnTraditionalLogin = askRequiredFieldsOnTraditionalLogin;
    }
}
