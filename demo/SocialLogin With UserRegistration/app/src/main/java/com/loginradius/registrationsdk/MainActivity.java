package com.loginradius.registrationsdk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.loginradius.sdk.WebviewActivity;

public class MainActivity extends ActionBarActivity {

    Button loginBt, signinBt, socialBt, forgetBt;
    Button.OnClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        /*   Apikey ,Sitename and applicationid is necessary for implementing Loginradius SDK
         *  */
        final String apiKey = getString(R.string.loginradius_api_key);
        final String siteName = getString(R.string.loginradius_site_name);


        /*  if you want native facebook login then pass app_id in string */
        final String facebook_native = getString(R.string.facebook_native);
        final String google_native = getString(R.string.google_native);

        /*
           optional for user recaptchakey,promptpassword,messagelogin,messageforgotpass
         */
        final String V2RecaptchaSiteKey = getString(R.string.V2RecaptchaSiteKey);
        final String promptpassword = getString(R.string.promptPasswordOnSocialLogin);
        final String messagelogin = getString(R.string.Toast_message_for_login);
        final String messageforgotpass = getString(R.string.Toast_message_for_ForgotPassword);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }




        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WebviewActivity.class);
                intent.putExtra("V2RecaptchaSiteKey", V2RecaptchaSiteKey);
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("promptpassowrd", promptpassword);
                intent.putExtra("facebook_native", facebook_native);
                intent.putExtra("google_native", google_native);
                intent.putExtra("toast_message_for_login", messagelogin);
                intent.putExtra("toast_message_for_ForgotPassword", messageforgotpass);


                switch (v.getId()) {
                    case R.id.login_bt:
                        intent.putExtra("action", "LOGIN");
                        break;
                    case R.id.signin_bt:
                        intent.putExtra("action", "SIGNIN");
                        // do stuff;
                        break;
                    case R.id.social_bt:
                        intent.putExtra("action", "SOCIAL");
                        break;
                    case R.id.forget_bt:
                        intent.putExtra("action", "FORGOT");
                        break;
                    default:
                        return;
                }
                startActivityForResult(intent, 2);
            }
        };
        initWidget();

    }

    private void initWidget() {
        //initialize button
        loginBt = (Button) findViewById(R.id.login_bt);
        signinBt = (Button) findViewById(R.id.signin_bt);
        socialBt = (Button) findViewById(R.id.social_bt);
        forgetBt = (Button) findViewById(R.id.forget_bt);
        //set on Click listener
        loginBt.setOnClickListener(listener);
        signinBt.setOnClickListener(listener);
        socialBt.setOnClickListener(listener);
        forgetBt.setOnClickListener(listener);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2){
        if(data!=null){
            String accessToken=data.getStringExtra("accesstoken");
            String provider=data.getStringExtra("provider");
            Intent intent = new Intent(getApplication(), UserProfileActivity.class);
            intent.putExtra("accesstoken", accessToken);
            intent.putExtra("provider", provider);
            startActivity(intent);
         }
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
