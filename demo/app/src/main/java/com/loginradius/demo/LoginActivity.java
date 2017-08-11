package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import android.text.TextWatcher;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loginradius.androidsdk.activity.FacebookNativeActivity;
import com.loginradius.androidsdk.activity.GoogleNativeActivity;
import com.loginradius.androidsdk.activity.WebViewActivity;

import com.loginradius.androidsdk.api.LoginAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String apikey, sitename, verificationUrl, emailTemplate;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificationUrl = getString(R.string.verification_url);
        emailTemplate = getString(R.string.email_template);
        apikey = getString(R.string.api_key);
        sitename = getString(R.string.site_name);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        ImageButton facebook = (ImageButton) findViewById(R.id.facebook);
        ImageButton google = (ImageButton) findViewById(R.id.google);
        Button login = (Button) findViewById(R.id.btn_login);
        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), WebViewActivity.class);
                intent.putExtra("apikey", apikey);
                intent.putExtra("sitename", sitename);
                intent.putExtra("provider", "facebook");
                startActivityForResult(intent, 2);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplication(), GoogleNativeActivity.class);
                intent.putExtra("apikey", apikey);
                startActivityForResult(intent, 2);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                doLogin();
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
        LoginParams value = new LoginParams();
        value.apikey = apikey; //put loginradius your apikey (required)
        value.email = inputEmail.getText().toString();
        value.password = inputPassword.getText().toString();
        value.verificationUrl = verificationUrl;// put your verificationUrl(required)
        value.emailTemplate = emailTemplate;//put your emailTemplate(optional)
        LoginAPI userAPI = new LoginAPI();
        userAPI.getResponse(value, new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData logindata) {
                hideProgressDialog();
                List<String> result = new ArrayList<String>();
                try {
                    for (Field field : logindata.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = field.get(logindata);
                        Intent intent = new Intent(getApplication(), ProfileActivity.class);
                        intent.putExtra("accesstoken", logindata.getAccessToken());
                        intent.putExtra("provider", logindata.getProfile().getProvider().toLowerCase());
                        intent.putExtra("apikey", getString(R.string.api_key));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                intent.putExtra("apikey", getString(R.string.api_key));
                startActivity(intent);
            }
        }
    }

}
