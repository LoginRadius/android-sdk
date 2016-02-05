package com.loginradius.registrationsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.userregistration.LoginRadiusUserReg;
import com.loginradius.userregistration.module.Option;
import com.loginradius.userregistration.module.Response;
import com.loginradius.userregistration.resource.StrResource;

public class WebviewActivity extends Activity {

    UserProfileAPI userProfileApI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String apiKey= getString(R.string.loginradius_api_key);
        String siteName= getString(R.string.loginradius_site_name);
        Option options = new Option();
        options.setAPI_KEY(apiKey);
        options.setAPP_Name(siteName);
        userProfileApI = new UserProfileAPI();
        final Context context = this;
        /**
         * To initialize the LoginRadiusUserReg Object with callback function
         */
        final LoginRadiusUserReg lrraas = new LoginRadiusUserReg(this, options, new LoginRadiusUserReg.Callback() {
            @Override
            public void onSuccess(Response resp) {
                Toast.makeText(getApplicationContext(), resp.toString(), Toast.LENGTH_LONG).show();
                Response.Action responseAction = resp.getActionReponse();
                if(responseAction.equals(Response.Action.login)){
                    /**
                     * Retrieve the access token and userID from Preference
                     */
                    String accessToken = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY,MODE_PRIVATE).getString("lrtoken","");
                    String userID = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY,MODE_PRIVATE).getString("lraccountid","");
                    Log.d("accessToken",accessToken);
                    Log.d("UserID", userID);

                }else if(responseAction.equals(Response.Action.registration)){
                    Log.d("registration","callback");
                    Intent intent = new Intent(getApplication(),WebviewActivity.class);
                    intent.putExtra("action", "LOGIN");
                    startActivity(intent);
                }else if(responseAction.equals(Response.Action.forget)){
                    Log.d("forget","callback");
                }
                Log.v("response", resp.toString());
            }

            @Override
            public void onError(Response resp) {
                Toast.makeText(getApplicationContext(),resp.toString(),Toast.LENGTH_LONG).show();
            }
            @Override
            public boolean onNativeLogin(Response.Provider provider){

                return false;
            }
        }) {
        };
        setContentView(R.layout.activity_webview);
        RelativeLayout relativeLayout =(RelativeLayout) findViewById(R.id.webviewcontainer);
        relativeLayout.addView(lrraas);
        Intent intent = getIntent();
        if(intent != null) {
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
                case "FORGET":
                    lrraas.loadForgetPasswordPage();
                    break;
            }
        }
    }



}
