package com.loginradius.sdk;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.userregistration.LoginRadiusUserReg;
import com.loginradius.userregistration.module.Option;
import com.loginradius.userregistration.module.Response;
import com.loginradius.userregistration.resource.StrResource;

import java.util.Timer;
import java.util.TimerTask;


public class NativeWeb extends Activity {
    private WebView webview;
    UserProfileAPI userProfileApI;
    Context co;
    SharedPreferences sharedPreference;
    private ProgressDialog progressBar;
    private String ApplicationActivityIdfb ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String data = getIntent().getExtras().getString("keyName");
        String data1 = getIntent().getExtras().getString("SiteName");
        final String facebooknativeemail = getIntent().getExtras().getString("accesstoken");
        ApplicationActivityIdfb= getIntent().getExtras().getString("ApplicationActivityIdfb");


        String apiKey = data;
        String siteName = data1;
        Option options = new Option();
        options.setAPI_KEY(apiKey);
        options.setfacebooknativetoken(facebooknativeemail);
        options.setAPP_Name(siteName);
        userProfileApI = new UserProfileAPI();
        final Context context = this;




        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            final LoginRadiusUserReg lrraas = new LoginRadiusUserReg(this, options, new LoginRadiusUserReg.Callback() {
                @Override
                public void onSuccess(Response resp) {
                    Response.Action responseAction = resp.getActionReponse();
                    if (responseAction.equals(Response.Action.login)) {
                        Toast.makeText(getApplicationContext(), "User Successfully logged in", Toast.LENGTH_LONG).show();
                        String accessToken = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("lrtoken", "");
                        String userID = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("lraccountid", "");
                        Log.d("accessToken", accessToken);
                        sendAccessToken(accessToken);
                        Log.d("UserID", userID);

                    }
                    // Log.v("response", resp.toString());
                }

                @Override
                public void onError(Response resp) {
                    Toast.makeText(getApplicationContext(), resp.toString(), Toast.LENGTH_LONG).show();
                }
                @Override
                public boolean onNativeLogin(Response.Provider provider){

                    return false;
                }

            }) {
            };


            setContentView(R.layout.activity_native_web);
            webview  =(WebView) findViewById(R.id.web1);
            webview.addView(lrraas);


            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getStringExtra("action");
                switch (action) {

                    case "FB":

                        progressBar = ProgressDialog.show(NativeWeb.this, "Connecting", "Please wait for a few seconds...");
                        long delayInMillis5 = 5000;
                        Timer timer5 = new Timer();
                        timer5.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                progressBar.dismiss();
                            }
                        }, delayInMillis5);
                        lrraas.loadFacebooknative_login();

                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }



    }


    public void sendAccessToken(String accessToken) {
        Intent intent = new Intent(ApplicationActivityIdfb);
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("provider", "facebook");
        startActivity(intent);
    }
}