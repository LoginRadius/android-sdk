package com.loginradius.demo;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.loginradius.androidsdk.activity.ForgotPasswordActivity;
import com.loginradius.androidsdk.activity.LoginActivity;
import com.loginradius.androidsdk.activity.RegisterActivity;
import com.loginradius.androidsdk.activity.SocialLoginActivity;
import com.loginradius.androidsdk.helper.CountryCodes;


public class MainActivity extends AppCompatActivity {

    String sott = "put your sott";
    private Button btnlogin,btnregister,btnforgotpassword,btnsocial;
    Button.OnClickListener listener;
    String apiKey,siteName,resetPasswordUrl,verificationUrl,emailTemplate,facebooknNative,googleNative,isMobile,smsTemplate,promptPasswordOnSocialLogin,usernameLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKey = getString(R.string.api_key);
        siteName = getString(R.string.site_name);
        resetPasswordUrl = getString(R.string.reset_password_url);
        verificationUrl = getString(R.string.verification_url);
        emailTemplate = getString(R.string.email_template);
        facebooknNative = getString(R.string.facebook_native);
        googleNative = getString(R.string.google_native);
        promptPasswordOnSocialLogin = getString(R.string.prompt_password_on_social_login);
        usernameLogin = getString(R.string.username_login);
        isMobile = getString(R.string.is_mobile);
        smsTemplate = getString(R.string.sms_template);
        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.login_bt:
                        intent = new Intent(getApplication(), LoginActivity.class);
                        break;
                    case R.id.signup_bt:
                        intent = new Intent(getApplication(), RegisterActivity.class);
                        // do stuff;
                        break;
                    case R.id.social_bt:
                        intent = new Intent(getApplication(), SocialLoginActivity.class);
                        break;
                    case R.id.forgot_bt:
                        intent = new Intent(getApplication(), ForgotPasswordActivity.class);
                        break;
                    default:
                        return;
                }
                CustomizeLanguage customizeLanguage = new CustomizeLanguage();
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("ismobile", isMobile);
                intent.putExtra("smstemplate", smsTemplate);
                intent.putExtra("verificationurl", verificationUrl);
                intent.putExtra("facebooknative", facebooknNative);
                intent.putExtra("googlenative", googleNative);
                intent.putExtra("emailtemplate", emailTemplate);
                intent.putExtra("promptpasswordonsociallogin", promptPasswordOnSocialLogin);
                intent.putExtra("usernamelogin", usernameLogin);
                intent.putExtra("sott", sott);
                intent.putExtra("resetpasswordurl", resetPasswordUrl);
                //     intent.putExtra("language",customizeLanguage.getLanguage());
                startActivityForResult(intent, 2);
            }
        };
        initWidget();



    }
    private void initWidget() {
        //initialize button
        btnlogin = (Button) findViewById(R.id.login_bt);
        btnregister = (Button) findViewById(R.id.signup_bt);
        btnsocial = (Button) findViewById(R.id.social_bt);
        btnforgotpassword = (Button) findViewById(R.id.forgot_bt);
        //set on Click listener
        btnlogin.setOnClickListener(listener);
        btnregister.setOnClickListener(listener);
        btnsocial.setOnClickListener(listener);
        btnforgotpassword.setOnClickListener(listener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2) {
            if (data != null){
                String token = data.getStringExtra("accesstoken");
                String provider=data.getStringExtra("provider");
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                intent.putExtra("apikey",apiKey);
                startActivity(intent);
            }  }
    }
}