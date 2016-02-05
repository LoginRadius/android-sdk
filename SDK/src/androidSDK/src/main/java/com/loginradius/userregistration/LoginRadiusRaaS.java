package com.loginradius.userregistration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.loginradius.userregistration.module.Option;
import com.loginradius.userregistration.module.Response;
import com.loginradius.userregistration.resource.StrResource;
import com.loginradius.userregistration.util.URLHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class LoginRadiusRaaS extends WebView {
    private Context context;
    private Option options;
    public  enum action{login,registration,forget}

    public Callback callback;
    /**
     * @param context
     * @param params
     * @id id
     */
    public LoginRadiusRaaS (Context context,ViewGroup.LayoutParams params,int id,Option options,
                            final Callback callback)throws RegistrationException{
        super(context);
        if(options.getAPI_KEY() == null || options.getAPP_Name() == null)
            throw new RegistrationException("API KEY or APP name does not set correctly" );
        this.setLayoutParams(params);
        this.setId(id);
        this.enableJS();
        this.context = context;
        this.setWebChromeClient(new WebChromeClient());

        this.setWebViewClient(new WebClient(context,options) {
            @Override
            public void onSuccess(Response resp) {
                callback.onSuccess(resp);
            }

            @Override
            public void onError(Response resp) {
                callback.onError(resp);
            }
            @Override
            public boolean onNativeLogin(Response.Provider provider) {
            return false;
            }
        });
        this.options = options;
        this.callback = callback;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            this.setWebContentsDebuggingEnabled(true);
        }
        this.addJavascriptInterface(new WebAppInterface(context), "android");
        //this.userprofile = new UserProfile(context);
    }

    /**
     * @param options
     * @param context
     */
    public LoginRadiusRaaS (Context context,Option options,Callback callback)
            throws RegistrationException{

        this(context, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT), 100, options,callback);
    }

    /**
     * To enable JS in webviewer
     */
    private void enableJS(){
        WebSettings ws = this.getSettings();
        ws.setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
            ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setAllowContentAccess(true);
        this.addJavascriptInterface(new WebAppInterface(context), "Android");
        //WebAppInterface webappinterface = new WebAppInterface(context);
    }

    /**
     *
     */
    public void loadLoginPage(){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", options.getAPI_KEY());
        params.put("sitename", options.getAPP_Name());
        params.put("action", "login");
        String encodedURL = URLHelper.URLBuilder(StrResource.BASEPAGE, params);
        Log.d("URL", encodedURL);
        this.loadUrl(encodedURL);
    }
    /**
     * Open the registration page
     */
    public void loadRegistrationPage(){
        Map<String,String> params = new LinkedHashMap<>();
        params.put("apikey",options.getAPI_KEY());
        params.put("sitename", options.getAPP_Name());
        params.put("action", "registration");
        String encodedURL = URLHelper.URLBuilder(StrResource.BASEPAGE, params);
        Log.d("URL",encodedURL);
        this.loadUrl(encodedURL);
    }

    /**
     * Open the forget password page
     */
    public void loadForgetPasswordPage(){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey",options.getAPI_KEY());
        params.put("sitename", options.getAPP_Name());
        params.put("action", "forgotpassword");
        String encodedURL = URLHelper.URLBuilder(StrResource.BASEPAGE, params);
        Log.d("URL",encodedURL);
        this.loadUrl(encodedURL);
    }

    /**
     * Open the reset password page
     */
    public void loadResetPasswordPage(){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey",options.getAPI_KEY());
        params.put("sitename", options.getAPP_Name());
        params.put("action", "resetpassword");
        String encodedURL = URLHelper.URLBuilder(StrResource.BASEPAGE, params);
        Log.d("URL",encodedURL);
        this.loadUrl(encodedURL);
    }
    /**
     * Open the social login page
     */
    public void loadSocialLoginPage(){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey",options.getAPI_KEY());
        params.put("sitename", options.getAPP_Name());
        params.put("action", "social");
        String encodedURL = URLHelper.URLBuilder(StrResource.BASEPAGE, params);
        Log.d("URL",encodedURL);
        this.loadUrl(encodedURL);
    }
    private class WebAppInterface {
        Context mContext;
        String userId;
        String accessToken;
        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void setAccessToken(String accessToken) {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(
                    StrResource.SHAREDPREFERENCEFILEKEY,mContext.MODE_PRIVATE).edit();
            editor.putString("lrtoken", accessToken);
            editor.commit();
        }
        @JavascriptInterface
        public void setUserId(String userId) {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(
                    StrResource.SHAREDPREFERENCEFILEKEY,mContext.MODE_PRIVATE).edit();
            editor.putString("lr-user-uid", userId);
            editor.commit();
        }
        @JavascriptInterface
        public void saveProfileData(String accessToken){
            //userprofile.saveUserProfile(accessToken,options.getAPI_KEY());
        }
        @JavascriptInterface
        public String getAPIKey(){
            return options.getAPI_KEY();
        }
        @JavascriptInterface
        public String getAPPName(){
            return options.getAPP_Name();
        }
    }
    public interface Callback{
        void onSuccess(Response resp);
        void onError(Response resp);
    }
}
