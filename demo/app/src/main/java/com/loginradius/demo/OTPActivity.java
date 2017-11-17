package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.resource.LoginUtil;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.register.RegisterResponse;

public class OTPActivity extends AppCompatActivity implements OnClickListener{

    private EditText etOTP;
    private Button btnSubmit,btnResend;
    private String phoneId;
    private ProgressDialog pDialog;
    private boolean isForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
    }

    private void init() {
        etOTP = (EditText)findViewById(R.id.otp);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        btnResend = (Button)findViewById(R.id.btn_resend);
        btnSubmit.setOnClickListener(this);
        btnResend.setOnClickListener(this);
        phoneId = getIntent().getStringExtra("phoneId");
        isForgotPassword = getIntent().getBooleanExtra("isForgotPassword",false);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                String otp = etOTP.getText().toString().trim();
                if(otp.length() == 0){
                    NotifyToastUtil.showNotify(this,"Please provide OTP value");
                }else{
                    if(!isForgotPassword){
                        submitOTP(otp);
                    }else{
                        startActivity(new Intent(this,ResetPasswordByOtpActivity.class)
                                        .putExtra("otp",otp)
                                        .putExtra("phoneId",phoneId));
                        finish();
                    }
                }
                break;
            case R.id.btn_resend:
                resendOTP();
                break;
        }
    }

    private void resendOTP() {
        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", phoneId);
        AuthenticationAPI api = new AuthenticationAPI();
        api.resendOtp(null,jsonObject, new AsyncHandler<RegisterResponse>() {

            @Override
            public void onSuccess(RegisterResponse data) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(OTPActivity.this,"OTP sent successfully to your phone");
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(OTPActivity.this,error.getMessage());
            }
        });
    }

    private void submitOTP(String otp) {
        showProgressDialog();
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", phoneId);
        queryParams.setOtp(otp);
        api.verifyOtp(queryParams, jsonObject, new AsyncHandler<LoginData>() {

            @Override
            public void onSuccess(LoginData data) {
                hideProgressDialog();
                LoginUtil loginUtil = new LoginUtil(OTPActivity.this);
                loginUtil.setLogin(data.getAccessToken());
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                intent.putExtra("provider", data.getProfile().getProvider().toLowerCase());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(OTPActivity.this,error.getMessage());
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if(pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
