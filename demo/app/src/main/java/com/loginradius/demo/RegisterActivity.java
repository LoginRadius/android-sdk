package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.loginradius.androidsdk.api.RegistrationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.register.Country;
import com.loginradius.androidsdk.response.register.Email;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.register.RegistrationData;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    boolean phoneLogin;
    EditText firstname,lastname,country,countrycode,email,mobile,username,password,confirmpassword;
    private TextInputLayout inputfirstname,inputlastname,inputcountry,inputcountrycode,inputemail,inputpassword,inputconfirmpassword;
    private ProgressDialog pDialog;
    String apikey ,verificationUrl,emailTemplate;
    String sott="put your sott";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apikey=getString(R.string.api_key);
        verificationUrl=getString(R.string.verification_url);
        emailTemplate=getString(R.string.email_template);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        country = (EditText) findViewById(R.id.country);
        countrycode = (EditText) findViewById(R.id.countrycode);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.mobile);
        username = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confpassword);
        inputfirstname = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        inputlastname = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        inputcountry = (TextInputLayout) findViewById(R.id.input_layout_country);
        inputcountrycode = (TextInputLayout) findViewById(R.id.input_layout_countrycode);
        inputemail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputpassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputconfirmpassword = (TextInputLayout) findViewById(R.id.input_layout_confpassword);
        firstname.addTextChangedListener(new MyTextWatcher(firstname));
        lastname.addTextChangedListener(new MyTextWatcher(lastname));
        country.addTextChangedListener(new MyTextWatcher(country));
        //email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
        confirmpassword.addTextChangedListener(new MyTextWatcher(confirmpassword));
        Button btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }


    private void submitForm() {
        String confermpassword = confirmpassword.getText().toString().trim();
        String password1= password.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();
        String mobileNumber = mobile.getText().toString().trim();
        if (!password1.equals(confermpassword)){
            NotifyToastUtil.showNotify(RegisterActivity.this,"The Confirm Password field does not match the Password field");
        }else if(emailAddress.length() == 0){
            NotifyToastUtil.showNotify(RegisterActivity.this,"Please fill email");
        }else if(!emailAddress.matches(Patterns.EMAIL_ADDRESS.pattern())){
            NotifyToastUtil.showNotify(RegisterActivity.this,"Email address is not correct");
        }else if(username.getText().toString().trim().length() == 0){
            NotifyToastUtil.showNotify(RegisterActivity.this,"Please fill username");
        }else if(mobileNumber.length() == 0){
            NotifyToastUtil.showNotify(RegisterActivity.this,"Please fill mobile number");
        }else if(!mobileNumber.matches(Patterns.PHONE.pattern())){
            NotifyToastUtil.showNotify(RegisterActivity.this,"Mobile number is not correct");
        }else{
            doRegistration();
        }
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

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2 ) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            int i = view.getId();

        }
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

    private void doRegistration() {
        showProgressDialog();
        LoginParams value = new LoginParams();
        value.apikey = apikey;//put loginradius your apikey(required)
        value.sott=sott;//put your sott(required)
        value.verificationUrl=verificationUrl; // put your verificationUrl(required)
        value.emailTemplate=emailTemplate;  //put your emailTemplate(optional)
        final RegistrationData data = new RegistrationData();
        data.setFirstName(firstname.getText().toString());
        data.setLastName(lastname.getText().toString());
        data.setPassword(password.getText().toString());
        Country countryObj = new Country();
        countryObj.setCode(countrycode.getText().toString());
        countryObj.setName(country.getText().toString());

        final String inputString = email.getText().toString();

        Email emailObj = new Email();
        emailObj.setType("Primary");
        emailObj.setValue(email.getText().toString());
        data.setEmail(new ArrayList<Email>(Arrays.asList(emailObj)));
        data.setPhoneId(mobile.getText().toString().trim());
        data.setUserName(username.getText().toString().trim());

        /*if(inputString.matches(Patterns.EMAIL_ADDRESS.pattern())){

        }else if(inputString.matches(Patterns.PHONE.pattern())){
            data.setPhoneId(inputString);
            phoneLogin = true;
        }else{
            data.setUserName(inputString);
        }*/

        RegistrationAPI registrationAPI = new RegistrationAPI();
        registrationAPI.getResponse(value,data,new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                hideProgressDialog();
                Log.e("data",registerResponse.getIsPosted().toString());
                NotifyToastUtil.showNotify(RegisterActivity.this,"OTP successfully sent to your phone");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(RegisterActivity.this,OTPActivity.class).putExtra("phoneId",data.getPhoneId().toString()));
                        finish();
                    }
                },1500);
                /*if(phoneLogin){

                }else{
                    NotifyToastUtil.showNotify(RegisterActivity.this,"Please Verify Your Email");
                }*/
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(RegisterActivity.this,error.getMessage());
            }
        });
    }

}
