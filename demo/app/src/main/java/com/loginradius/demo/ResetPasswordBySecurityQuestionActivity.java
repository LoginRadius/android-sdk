package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.register.RegisterResponse;

public class ResetPasswordBySecurityQuestionActivity extends AppCompatActivity implements OnClickListener{

    private EditText etNewPassword,etConfirmPassword;
    private Button btnSubmit;
    private ProgressDialog pDialog;
    private String[] arrQuestionId,arrAnswer;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_by_security_question);
        init();
    }

    private void init() {
        etNewPassword = (EditText)findViewById(R.id.new_password);
        etConfirmPassword = (EditText)findViewById(R.id.confpassword);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        btnSubmit.setOnClickListener(this);
        arrQuestionId = getIntent().getStringArrayExtra("questionIds");
        arrAnswer = getIntent().getStringArrayExtra("answers");
        value = getIntent().getStringExtra("value");
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
                    NotifyToastUtil.showNotify(this,"Password must be at least 6 characters.");
                }else if(!newPassword.equals(confirmPassword)){
                    NotifyToastUtil.showNotify(this,"Password mismatch.");
                }else{
                    resetPasswordBySecurityQuestions(newPassword,confirmPassword);
                }
                break;
        }
    }

    private void resetPasswordBySecurityQuestions(String newPassword,String confirmPassword) {
        showProgressDialog();
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams params = new QueryParams();
        if(value.matches(Patterns.EMAIL_ADDRESS.pattern())){
            params.setEmail(value);
        }else if(value.matches(Patterns.PHONE.pattern())){
            params.setPhone(value);
        }else {
            params.setUsername(value);
        }
        params.setPassword(newPassword);
        params.setConfirmPassword(confirmPassword);
        JsonObject jsonObject = new JsonObject();
        JsonObject qaJson = new JsonObject();
        for(int i = 0;i<arrQuestionId.length;i++){
            qaJson.addProperty(arrQuestionId[i],arrAnswer[i]);
        }
        jsonObject.add("securityanswer",qaJson);
        api.resetPasswordBySecurityQuestions(params,jsonObject, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse data) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ResetPasswordBySecurityQuestionActivity.this,"Password Changed Successfully.");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ResetPasswordBySecurityQuestionActivity.this,LoginActivity.class));
                        finish();
                    }
                },1500);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ResetPasswordBySecurityQuestionActivity.this,error.getMessage());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SecurityQuestionsActivity.class);
        intent.putExtra("value",value);
        startActivity(intent);
        finish();
    }
}
