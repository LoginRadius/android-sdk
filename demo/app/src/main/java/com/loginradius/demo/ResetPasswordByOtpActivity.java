package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.OtpVerificationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

public class ResetPasswordByOtpActivity extends AppCompatActivity implements OnClickListener{

    private String phoneId,otp;
    private EditText etNewPassword,etConfirmPassword;
    private Button btnSubmit;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_by_otp);
        init();
    }

    private void init() {
        phoneId = getIntent().getStringExtra("phoneId");
        otp = getIntent().getStringExtra("otp");
        etNewPassword = (EditText)findViewById(R.id.new_password);
        etConfirmPassword = (EditText)findViewById(R.id.confpassword);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,OTPActivity.class)
                        .putExtra("isForgotPassword",true)
                        .putExtra("phoneId",phoneId));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                if(newPassword.length() == 0){
                    etNewPassword.setError("Required");
                }else if(confirmPassword.length() == 0){
                    etConfirmPassword.setError("Required");
                }else if(newPassword.length()<6){
                    NotifyToastUtil.showNotify(this,"Password must be at least 6 characters");
                }else if(!newPassword.equals(confirmPassword)){
                    NotifyToastUtil.showNotify(this,"Password mismatch");
                }else{
                    submitOtp(otp,newPassword);
                }
                break;
        }
    }

    private void submitOtp(String otp, String newPassword) {
        showProgressDialog();
        LoginParams params = new LoginParams();
        params.apikey = getString(R.string.api_key);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", phoneId);
        jsonObject.addProperty("otp",otp);
        jsonObject.addProperty("password",newPassword);
        OtpVerificationAPI api = new OtpVerificationAPI();
        api.getResponse(params, jsonObject, new AsyncHandler<LoginData>() {

            @Override
            public void onSuccess(LoginData data) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ResetPasswordByOtpActivity.this,"Password changed successfully");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ResetPasswordByOtpActivity.this,LoginActivity.class));
                        finish();
                    }
                },1500);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ResetPasswordByOtpActivity.this,error.getMessage());
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
