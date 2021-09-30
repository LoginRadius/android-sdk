package com.loginradius.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.VerifyEmailResponse;
import com.loginradius.androidsdk.response.login.LoginData;

public class DeepLinkVerifyActivity extends AppCompatActivity {

    private String vtoken,apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link_verify);
        init();
    }

    private void init() {
        vtoken = getIntent().getData().getQueryParameter("vtoken");
        apikey = getIntent().getData().getQueryParameter("apikey");
        if(vtoken == null || apikey == null){
            finish();
        }else{
            startVerificationProcess();
        }
    }

    private void startVerificationProcess() {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setVtoken(vtoken);
        api.verifyEmail(queryParams, new AsyncHandler<VerifyEmailResponse>() {
            @Override
            public void onSuccess(VerifyEmailResponse data) {
                LoginData loginData = data.getData();
                final String access_token = loginData.getAccessToken();
                NotifyToastUtil.showNotify(DeepLinkVerifyActivity.this,"Email verified successfully");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DeepLinkVerifyActivity.this,ProfileActivity.class);
                        intent.putExtra("apikey",apikey);
                        intent.putExtra("accesstoken",access_token);
                        startActivity(intent);
                        finish();
                    }
                },1500);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(DeepLinkVerifyActivity.this,error.getMessage());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1500);
            }
        });
    }
}
