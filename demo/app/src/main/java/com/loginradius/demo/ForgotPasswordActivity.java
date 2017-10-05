package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.loginradius.androidsdk.api.ForgotPasswordByEmailAPI;
import com.loginradius.androidsdk.api.ForgotPasswordByPhoneNumberAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.password.ForgotResponse;
import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText inputEmail;
    private TextInputLayout inputLayoutEmail;
    private ProgressDialog pDialog;
    String apikey,sitename,resetPasswordUrl,emailTemplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetPasswordUrl=getString(R.string.reset_password_url);
        emailTemplate=getString(R.string.email_template);
        apikey=getString(R.string.api_key);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputEmail = (EditText) findViewById(R.id.input_email);
        //inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        Button  btnforgot = (Button) findViewById(R.id.btn_forgotpassword);
        Button btnreset = (Button)findViewById(R.id.btn_resetpassword);
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btnreset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputString = inputEmail.getText().toString();
                if(inputString.length() == 0){
                    inputEmail.setError("Required");
                }else if(inputString.matches(Patterns.EMAIL_ADDRESS.pattern())){
                    startActivity(new Intent(ForgotPasswordActivity.this,SecurityQuestionsActivity.class).putExtra("value",inputString).putExtra("isForgotPassword",true));
                    finish();
                }else if(inputString.matches(Patterns.PHONE.pattern())){
                    startActivity(new Intent(ForgotPasswordActivity.this,SecurityQuestionsActivity.class).putExtra("value",inputString).putExtra("isForgotPassword",true));
                    finish();
                }else{
                    inputEmail.setError("Invalid");
                }
            }
        });
    }


    private void submitForm() {

            /*if (!validateEmail()) {
                return;
            }*/
        doForgotPasswordByEmail();
    }


    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Your Email Address is Wrong !");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }



    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }





    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            int i = view.getId();
            if (i == R.id.input_email) {
                validateEmail();
            }
        }
    }


    private void doForgotPasswordByEmail() {
        showProgressDialog();
        LoginParams value = new LoginParams();
        value.apikey = apikey; //put loginradius your apikey (required)
        value.resetPasswordUrl=resetPasswordUrl;// put your resetPasswordUrl(required)
        value.emailTemplate=emailTemplate;//put your emailTemplate(optional)

        ForgotPasswordData forgotPassworddata = new ForgotPasswordData();

        final String inputString = inputEmail.getText().toString();

        if(inputString.length() == 0){
            hideProgressDialog();
            inputEmail.setError("Required");
        }else if(inputString.matches(Patterns.EMAIL_ADDRESS.pattern())){
            forgotPassworddata.setEmail(inputString);
            startForgotPasswordByEmailAPI(value,forgotPassworddata);
        }else if(inputString.matches(Patterns.PHONE.pattern())){
            forgotPassworddata.setPhone(inputString);
            startForgotPasswordByPhoneAPI(value,forgotPassworddata);
        }else{
            hideProgressDialog();
            inputEmail.setError("Invalid");
        }


    }

    private void startForgotPasswordByEmailAPI(LoginParams value,ForgotPasswordData forgotPassworddata){
        ForgotPasswordByEmailAPI forgotPasswordByEmailAPI = new ForgotPasswordByEmailAPI();
        forgotPasswordByEmailAPI.getResponse(value,forgotPassworddata,new AsyncHandler<ForgotResponse>() {
            @Override
            public void onSuccess(ForgotResponse response) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ForgotPasswordActivity.this,"Please Check Your Email");
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ForgotPasswordActivity.this,error.getMessage());

            }
        });
    }

    private void startForgotPasswordByPhoneAPI(final LoginParams value, final ForgotPasswordData forgotPasswordData){
        ForgotPasswordByPhoneNumberAPI api = new ForgotPasswordByPhoneNumberAPI();
        api.getResponse(value, forgotPasswordData, new AsyncHandler<PhoneForgotPasswordResponse>() {
            @Override
            public void onSuccess(PhoneForgotPasswordResponse data) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ForgotPasswordActivity.this,"OTP sent successfully to your phone");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ForgotPasswordActivity.this,OTPActivity.class)
                                .putExtra("isForgotPassword",true)
                                .putExtra("phoneId",forgotPasswordData.getPhone()));
                        finish();
                    }
                },1500);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(ForgotPasswordActivity.this,error.getMessage());
            }
        });
    }
}
