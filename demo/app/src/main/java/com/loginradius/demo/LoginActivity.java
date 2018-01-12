package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.api.ConfigurationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.helper.LoginRadiusSDK.WebLogin;
import com.loginradius.androidsdk.resource.LoginUtil;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.config.ConfigResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.socialinterface.Provider;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String verificationUrl, emailTemplate;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private ProgressBar pbLoad;
    private LinearLayout linearSocial,linearOr;
    private List<Provider> providers;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificationUrl = getString(R.string.verification_url);
        emailTemplate = getString(R.string.email_template);
        inflater = LayoutInflater.from(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        pbLoad = (ProgressBar)findViewById(R.id.pbLoad);
        linearSocial = (LinearLayout)findViewById(R.id.linearSocial);
        linearOr = (LinearLayout)findViewById(R.id.or);
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        Button login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                doLogin();
            }
        });

        getSocialProviders();
    }

    private void getSocialProviders() {
        ConfigurationAPI api = new ConfigurationAPI();
        api.getResponse(new AsyncHandler<ConfigResponse>() {
            @Override
            public void onSuccess(ConfigResponse data) {
                providers = data.getSocialSchema().getProviders();
                for (final Provider provider : providers) {
                    SocialLoginButton socialLoginButton = (SocialLoginButton)inflater.inflate(R.layout.layout_social_button,null);
                    socialLoginButton.setContent(provider.getName());
                    socialLoginButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LoginRadiusSDK.WebLogin webLogin = new WebLogin();
                            webLogin.setProvider(provider.getName().toLowerCase());
                            webLogin.startWebLogin(LoginActivity.this,2);
                        }
                    });
                    linearSocial.addView(socialLoginButton);
                }
                if(linearSocial.getChildCount()>0){
                    linearSocial.setVisibility(View.VISIBLE);
                    linearOr.setVisibility(View.VISIBLE);
                }else{
                    NotifyToastUtil.showNotify(LoginActivity.this,"Please configure social providers in user account");
                }
                pbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                pbLoad.setVisibility(View.GONE);
                NotifyToastUtil.showNotify(LoginActivity.this,error.getMessage());
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private void doLogin() {
        showProgressDialog();
        AuthenticationAPI api = new AuthenticationAPI();

        QueryParams queryParams = new QueryParams();
        final String inputString = inputEmail.getText().toString();
        if(inputString.matches(Patterns.EMAIL_ADDRESS.pattern())){
            queryParams.setEmail(inputString);
        }else if(inputString.matches(Patterns.PHONE.pattern())){
            queryParams.setPhone(inputString);
        }else {
            queryParams.setUsername(inputString);
        }
        queryParams.setPassword(inputPassword.getText().toString());
        queryParams.setEmailTemplate(emailTemplate);//put your emailTemplate(optional)
        queryParams.setRequired(true);
        queryParams.setFieldsColor(Color.parseColor("#000000"));
        api.login(LoginActivity.this, queryParams, new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData logindata) {
                hideProgressDialog();
                try {
                    LoginUtil loginUtil = new LoginUtil(LoginActivity.this);
                    loginUtil.setLogin(logindata.getAccessToken());
                    Intent intent = new Intent(getApplication(), ProfileActivity.class);
                    intent.putExtra("provider", logindata.getProfile().getProvider().toLowerCase());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(LoginActivity.this,error.getMessage());
                ErrorResponse errorResponse = JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                if (errorResponse.getErrorCode() == 1066){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this,OTPActivity.class).putExtra("phoneId",inputString));
                            finish();
                        }
                    },1500);
                }else if(errorResponse.getErrorCode() == 1148){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this,SecurityQuestionsActivity.class);
                            intent.putExtra("value",inputString);
                            startActivity(intent);
                            finish();
                        }
                    },1500);
                }
            }
        });
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("please type valid email");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError("please type valid password");
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
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

            } else if (i == R.id.input_password) {
                validatePassword();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (data != null &&data.toString().contains("extras")) {
                String token = data.getStringExtra("accesstoken");
                String provider = data.getStringExtra("provider");
                LoginUtil loginUtil = new LoginUtil(LoginActivity.this);
                loginUtil.setLogin(token);
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                intent.putExtra("provider", provider);
                startActivity(intent);
            }
        }
    }

}
