package com.loginradius.sdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.userregistration.LoginRadiusUserReg;
import com.loginradius.userregistration.module.Option;
import com.loginradius.userregistration.module.Response;
import com.loginradius.userregistration.resource.StrResource;

import java.util.Timer;
import java.util.TimerTask;


public class WebviewActivity extends Activity {
    UserProfileAPI userProfileApI;
    Context co;
    SharedPreferences sharedPreference;
    private WebView webview;
    private ProgressDialog progressBar;
    private String facebook_native;
    private String google_native;
    private String promptpassword;
    private String lrapi;
    private String lrsitename;

    // private EditText textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lrapi = getIntent().getExtras().getString("keyname");
        lrsitename = getIntent().getExtras().getString("sitename");

        String V2RecaptchaSiteKey = getIntent().getExtras().getString("V2RecaptchaSiteKey");
        final String facebook = getIntent().getExtras().getString("facebook_native");
        final String google = getIntent().getExtras().getString("google_native");
        final String password = getIntent().getExtras().getString("promptpassowrd");
        final String Toast1 = getIntent().getExtras().getString("toast_message_for_login");
        final String Toast3 = getIntent().getExtras().getString("toast_message_for_ForgotPassword");


        if (facebook != null && facebook.equals("true")) {
            facebook_native = facebook;
        } else {
            facebook_native = "false";

        }
        if (google != null && google.equals("true")) {
            google_native = google;
        } else {
            google_native = "false";

        }
        if (password != null && password.equals("true")) {
            promptpassword = password;
        } else {
            promptpassword = "false";

        }


        String apiKey = lrapi;
        String siteName = lrsitename;
        String Promptpassword = promptpassword;
        Option options = new Option();
        options.setAPI_KEY(apiKey);
        options.setPrompt_password(Promptpassword);
        options.setrecaptchakey(V2RecaptchaSiteKey);
        options.setAPP_Name(siteName);
        userProfileApI = new UserProfileAPI();
        final Context context = this;
        /**
         * To initialize the LoginRadiusUserReg Object with callback function
         *
         *
         *
         *
         */

        SharedPreferences.Editor editor = getSharedPreferences(
                StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
        editor.putString("api", lrapi);
        editor.putString("sitename", lrsitename);
        editor.putString("promptpassword", promptpassword);
        editor.putString("facebook_native", facebook_native);
        editor.putString("google_native", google_native);
        editor.putString("toast_message_for_login", Toast1);
        editor.putString("toast_message_for_ForgotPassword", Toast3);

        editor.commit();

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
                        if (Toast1!=null) {
                            Toast.makeText(getApplicationContext(), Toast1, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "User Successfully logged in", Toast.LENGTH_LONG).show();
                        }
                        /**
                         * Retrieve the access token and userID from Preference
                         */
                        String accessToken = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("lrtoken", "");
                        String userID = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("lraccountid", "");
                        Log.d("accessToken", accessToken);
                        sendAccessToken(accessToken);
                        Log.d("UserID", userID);


                    } else if (responseAction.equals(Response.Action.registration)) {
                        Log.d("registration", "callback");
                        Toast.makeText(getApplicationContext(), "Please check your Email", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplication(), RegisterActivity.class);
                        intent.putExtra("registration", "registration");
                        startActivity(intent);


                    } else if (responseAction.equals(Response.Action.socialregistration)) {
                        Log.d("socialregistration", "callback");
                        Toast.makeText(getApplicationContext(), "Please check your Email", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplication(), RegisterActivity.class);
                        intent.putExtra("registration", "registration");
                        startActivity(intent);

                    } else if (responseAction.equals(Response.Action.tokennull)) {
                        Log.d("tokennull", "callback");
                        final Toast toast = Toast.makeText(getApplicationContext(), "Login Failiure !! Plese Try Again", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 500000);

                    } else if (responseAction.equals(Response.Action.forget)) {
                        if (Toast3 !=null) {
                            Toast.makeText(getApplicationContext(), Toast3, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your email", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onError(Response resp) {
                    Toast.makeText(getApplicationContext(), resp.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public boolean onNativeLogin(Response.Provider provider) {
                    Log.d("login", "google");
                    Intent intent = new Intent();
                    if ((google_native.equals("true")) && provider.equals(Response.Provider.google)) {
                        provider.equals(Response.Provider.google);
                        if (checkPlayServices()) {
                            intent = new Intent(getApplication(), GoogleNativeActivity.class);
                        }
                    } else if ((facebook_native.equals("true")) && provider.equals(Response.Provider.facebook)) {
                        intent = new Intent(getApplication(), FacebookNativeActivity.class);
                    }
                    intent.putExtra("keyName", lrapi);
                    startActivityForResult(intent, 2);
                    return true;
                }
            }) {
            };


            setContentView(R.layout.activity_webview_activity);

            webview = (WebView) findViewById(R.id.web);

            webview.addView(lrraas);


            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getStringExtra("action");
                switch (action) {
                    case "LOGIN":
                        lrraas.loadLoginPage();
                        break;
                    case "SIGNIN":
                        lrraas.loadRegistrationPage();
                        break;
                    case "SOCIAL":
                        lrraas.loadSocialLoginPage();
                        break;
                    case "FORGOT":
                        lrraas.loadForgetPasswordPage();

                }
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2){
         if(data!=null){
            String accessToken=data.getStringExtra("accesstoken");
            sendAccessToken(accessToken);
         }
        }
    }



    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
            } else {
                //  Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



    public void sendAccessToken(String accessToken) {
        Intent intent=new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("provider", "facebook");
        setResult(2,intent);
        finish();//finishing activity

    }
}