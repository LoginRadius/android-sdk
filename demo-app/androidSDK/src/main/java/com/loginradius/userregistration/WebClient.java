package com.loginradius.userregistration;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.loginradius.userregistration.module.Option;
import com.loginradius.userregistration.module.Response;
import com.loginradius.userregistration.resource.StrResource;
import com.loginradius.userregistration.util.URLHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class WebClient extends WebViewClient {
    Context co;
    SharedPreferences sharedPreference;
    Option option;
    public WebClient(Context co, Option option) {
        this.co = co;
        this.sharedPreference = co.getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, co.MODE_PRIVATE);
        this.option = option;
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(co, "Oh no! " + description, Toast.LENGTH_SHORT).show();
    }
    private boolean testURL(URL url) throws MalformedURLException{
        Pattern p = Pattern.compile("\\.hub\\.loginradius.com");
        Matcher m = p.matcher(url.getHost());
        return m.find() || (new URL(StrResource.BASEPAGE).getHost().equals(url.getHost()));
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("URL-------------------", url);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDatabaseEnabled(true);
        view.getSettings().setUserAgentString("silly_that_i_have_to_do_this");
        String action = "";
        Map parameters = new LinkedHashMap();
        try {
            URL request = new URL(url);
            //check the scope of url
            if (!testURL(request)) {
                Log.e("Compared",request.getHost());
                view.loadUrl(url);
                return false;
            }
            parameters = URLHelper.splitQuery(request);
            // check the parameters
            String provider = (String) parameters.get("provider");
            if (provider != null){
                switch (provider){
                    case "facebook":
                        Log.d("provider","facebook");
                        if(onNativeLogin(Response.Provider.facebook))
                            return true;
                        break;
                    case "google":
                        if(onNativeLogin(Response.Provider.google))
                            return true;
                        break;
                    default:
                        Log.d("provider",provider);
                }
            }
            action = (String) parameters.get("action");
            if (action == null) {
                view.loadUrl(url);
                return true;
            }
            Log.e("URL-------------------", action);
        } catch (Exception ex) {
            ex.printStackTrace();
            view.loadUrl(url);
            return true;
        }
        switch (action) {
            case "registration":
                String status = (String) parameters.get("status");
                if (status != null) {
                    if (status.equals("true")) {
                        onSuccess(new Response(Response.Action.registration));
                    }
                }
                Log.e("-------registration", url);
                break;
            case "login":
                String LRTokenKey = (String) parameters.get("lrtoken");


                 // get access token
                if (LRTokenKey != null) {
                    String lraccountid = (String) parameters.get("lraccountid"); // get the account id
                    if (lraccountid != null) {
                        Log.d("Login", "Success");
                        SharedPreferences.Editor editor = co.getSharedPreferences(
                                StrResource.SHAREDPREFERENCEFILEKEY,co.MODE_PRIVATE).edit();
                        editor.putString("lrtoken", LRTokenKey);
                        editor.putString("lraccountid", lraccountid);
                        editor.commit();
                        onSuccess(new Response(Response.Action.login));
                    }

                }
                Log.e("-------login", url);
                break;
            case "forgotpassword":
                Log.e("-------", url);
                status = (String) parameters.get("status");
                if (status != null) {
                    onSuccess(new Response(Response.Action.forget));
                }
                Log.e("-------forgotpassword", url);
                break;

            case "socialregistration":
                Log.e("-------", url);
                status = (String) parameters.get("status");
                if (status != null) {
                    onSuccess(new Response(Response.Action.socialregistration));
                }
                Log.e("-------socialregistrati", url);
                break;
            case "resetpassword":
                Log.e("-------", url);
                status = (String) parameters.get("status");
                if (status != null) {
                    onSuccess(new Response(Response.Action.forget));
                }
                Log.e("-------resetpassword", url);
                break;
            case "sociallogin":
               String LRTokenSKey = (String) parameters.get("lrtoken"); // get access token
                if (LRTokenSKey ==null){
                    onSuccess(new Response(Response.Action.tokennull));
                }
                if (LRTokenSKey != null) {
                    String lraccountid = (String) parameters.get("lraccountid"); // get the account id
                    if (lraccountid != null) {
                        Log.d("Login", "Success");
                        SharedPreferences.Editor editor = co.getSharedPreferences(
                                StrResource.SHAREDPREFERENCEFILEKEY,co.MODE_PRIVATE).edit();
                        editor.putString("lrtoken", LRTokenSKey);
                        editor.putString("lraccountid", lraccountid);

                        editor.commit();
                        onSuccess(new Response(Response.Action.login));
                    }
                }
                Log.e("-------social login", url);
                break;




            default:
                Log.d("action", action);
                view.loadUrl(url);
                return false;
        }
        view.loadUrl(url);
        return false;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        Log.e("111111", url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.e("LoginRadius",url);
    }

    public abstract void onSuccess(Response resp);

    public abstract void onError(Response resp);

    public abstract boolean onNativeLogin(Response.Provider resp);

}
