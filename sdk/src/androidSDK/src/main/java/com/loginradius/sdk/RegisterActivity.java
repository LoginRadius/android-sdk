package com.loginradius.sdk;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.loginradius.userregistration.resource.StrResource;

public class RegisterActivity extends AppCompatActivity {
    Button ok;
    Context co;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        final String apikey = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("api", "");
        final String sitename = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("sitename", "");
        final String promptpassword = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("promptpassword", "");
        final String facebook_native = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("facebook_native", "");
        final String google_native = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("google_native", "");
        final String toast_message_for_login= getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("toast_message_for_login", "");
        final String toast_message_for_ForgotPassword= getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("toast_message_for_ForgotPassword", "");
        final String ApplicationActivityId= getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("ApplicationActivityId", "");
        ok =(Button)findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this,WebviewActivity.class);
                intent.putExtra("action", "LOGIN");
                intent.putExtra("keyname", apikey);
                intent.putExtra("sitename", sitename);
                intent.putExtra("promptpassowrd",promptpassword);
                intent.putExtra("facebook_native",facebook_native);
                intent.putExtra("google_native",google_native);
                intent.putExtra("toast_message_for_login",toast_message_for_login);
                intent.putExtra("toast_message_for_ForgotPassword", toast_message_for_ForgotPassword);
                intent.putExtra("ApplicationActivityId",ApplicationActivityId);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(RegisterActivity.this,WebviewActivity.class);
        intent.putExtra("action", "SIGNIN");
        startActivity(intent);
    }

}
