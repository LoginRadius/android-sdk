package com.loginradius.androidsdk.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.loginradius.androidsdk.activity.FacebookNativeActivity;
import com.loginradius.androidsdk.activity.GoogleNativeActivity;
import com.loginradius.androidsdk.activity.VkontakteNativeActivity;
import com.loginradius.androidsdk.activity.WebViewActivity;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.SocialProviderConstant;

import java.util.Arrays;
import java.util.Collection;

/**
 * Updated by LoginRadius on 08/09/2020.
 */

public class LoginRadiusSDK {

    private LoginRadiusSDK() {}

    public static class Initialize{
        private static String apiKey,siteName,verificationUrl,resetPasswordUrl;

        public void setApiKey(String apiKey) {
            Initialize.apiKey = apiKey;
        }

        public void setSiteName(String siteName) {
            Initialize.siteName = siteName;
        }

        public void setVerificationUrl(String verificationUrl) {
            Initialize.verificationUrl = verificationUrl;
        }

        public void setResetPasswordUrl(String resetPasswordUrl) {
            Initialize.resetPasswordUrl = resetPasswordUrl;
        }
    }

    public static class WebLogin{
        private String provider;
        private boolean isRequired = true;
        private int fieldsColor;
        private boolean reloadOnError;
        private boolean customScopeEnabled;

        public WebLogin() {
            if(!LoginRadiusSDK.validate()){
                throw new LoginRadiusSDK.InitializeException();
            }
        }

        public WebLogin setProvider(SocialProviderConstant provider) {
            this.provider = provider.value;
            return this;
        }

        public WebLogin setProvider(String provider){
            this.provider = provider;
            return this;
        }

        public WebLogin setRequired(boolean required) {
            isRequired = required;
            return this;
        }

        public WebLogin setFieldsColor(int fieldsColor) {
            this.fieldsColor = fieldsColor;
            return this;
        }

        public WebLogin setReloadOnError(boolean reloadOnError) {
            this.reloadOnError = reloadOnError;
            return this;
        }

        public void setCustomScopeEnabled(boolean customScopeEnabled) {
            this.customScopeEnabled = customScopeEnabled;
        }

        public void startWebLogin(Activity activity, int requestCode){
            if(provider == null){
                throw new IllegalArgumentException("Social provider cannot be null");
            }
            Intent intent = new Intent(activity, WebViewActivity.class);
            intent.putExtra("provider",provider);
            intent.putExtra("isRequired",isRequired);
            intent.putExtra("fieldsColor",fieldsColor);
            intent.putExtra("reloadOnError",reloadOnError);
            intent.putExtra("customScopeEnabled",customScopeEnabled);
            activity.startActivityForResult(intent,requestCode);
        }
    }

    public static class NativeLogin{
        private boolean isRequired = true;
        private int fieldsColor;
        private Intent intent;
        private static Collection<String> facebookPermissions;
        private static Scope[] googleScopes;
        private static  String googleServerClientID,socialAppName;

        public NativeLogin() {

            this.googleScopes = new Scope[]{new Scope(Scopes.PROFILE),new Scope(Scopes.EMAIL)};
            this.facebookPermissions = Arrays.asList("public_profile","email");

            if(!LoginRadiusSDK.validate()){
                throw new LoginRadiusSDK.InitializeException();
            }
        }

        public NativeLogin setRequired(boolean required) {
            isRequired = required;
            return this;
        }

        public NativeLogin setFieldsColor(int fieldsColor) {
            this.fieldsColor = fieldsColor;
            return this;
        }


        public void setGoogleServerClientID(String googleServerClientID) {
            NativeLogin.googleServerClientID = googleServerClientID;

        }

        public void setSocialAppName(String socialAppName){
            NativeLogin.socialAppName =socialAppName;
        }


        /**
         * Change the scope to request on the user login. Use any of the permissions defined in https://developers.facebook.com/docs/facebook-login/android/permissions. Must be called before start().
         * The permission "public_profile" and "email" is requested by default.
         *
         * @param permissions the permissions to add to the request
         */

        public void setPermissions(@NonNull Collection<String> permissions) {
            this.facebookPermissions = permissions;
        }

        /**
            * Change the scopes to request on the user login. Use any of the scopes defined in the com.google.android.gms.common.Scopes class. Must be called before start().
            * The scope Scopes.PLUG_LOGIN and  Scopes.PROFILE is requested by default.
            *
            * @param scope the scope to add to the request
        */
        public void setGoogleScopes(@NonNull Scope... scope) {
            this.googleScopes = scope;
        }

        private void startNativeLogin(Activity activity, int requestCode){
            intent.putExtra("isRequired",isRequired);
            intent.putExtra("fieldsColor",fieldsColor);
            activity.startActivityForResult(intent,requestCode);
        }

        public void startFacebookNativeLogin(Activity activity, int requestCode){
            intent = new Intent(activity, FacebookNativeActivity.class);
            startNativeLogin(activity, requestCode);
        }

        public void startGoogleNativeLogin(Activity activity, int requestCode){
            intent = new Intent(activity, GoogleNativeActivity.class);
            startNativeLogin(activity,requestCode);
        }

        public void startVkontakteNativeLogin(Activity activity, int appId, int requestCode){
            intent = new Intent(activity, VkontakteNativeActivity.class);
            intent.putExtra("appId",appId);
            startNativeLogin(activity,requestCode);
        }
    }

    public static boolean validate(){
        if(Initialize.apiKey == null || Initialize.apiKey.length() == 0){
            return false;
        }else if(Initialize.siteName == null || Initialize.siteName.length() == 0){
            return false;
        }
        return true;
    }

    public static String getApiKey() {
        return Initialize.apiKey;
    }

    public static String getSiteName() {
        return Initialize.siteName;
    }


    public static String getVerificationUrl() {
        if(Initialize.verificationUrl!=null && Initialize.verificationUrl.length() > 0){
            return Initialize.verificationUrl;
        }else{
            return Endpoint.API_V2_VERIFY_URL;
        }
    }

    public static String getResetPasswordUrl() {
        if(Initialize.resetPasswordUrl!=null && Initialize.resetPasswordUrl.length() > 0){
            return Initialize.resetPasswordUrl;
        }else{
            return Endpoint.API_V2_VERIFY_URL;
        }
    }

    public static Collection<String> getFaceBookPermissions() {
        return NativeLogin.facebookPermissions;
    }


    public static String getGoogleServerClientID() {
        return NativeLogin.googleServerClientID;
    }

    public static Scope[] getGoogleScopes() {
        return NativeLogin.googleScopes;
    }

    public static String getSocialAppName() {
        return NativeLogin.socialAppName;
    }

    public static class InitializeException extends RuntimeException{
        public InitializeException() {
            super("LoginRadius SDK not initialized properly");
        }
    }
}
