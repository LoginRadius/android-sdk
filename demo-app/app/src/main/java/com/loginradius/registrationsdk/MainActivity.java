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


        /*   Apikey ,Sitename and applicationid is necessary for implementing Loginradius SDK
         *  */
        final String apiKey = getString(R.string.loginradius_api_key);
        final String siteName = getString(R.string.loginradius_site_name);
        final String ApplicationActivityId = getString(R.string.ApplicationActivityId);

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


        setTitle("LoginRadius Android Demo");

        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), WebviewActivity.class);

            intent.putExtra("V2RecaptchaSiteKey", V2RecaptchaSiteKey);
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("promptpassowrd", promptpassword);
              intent.putExtra("facebook_native", facebook_native);
                intent.putExtra("google_native", google_native);
                intent.putExtra("toast_message_for_login", messagelogin);
                intent.putExtra("toast_message_for_ForgotPassword", messageforgotpass);
                intent.putExtra("ApplicationActivityId", ApplicationActivityId);

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
                startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
