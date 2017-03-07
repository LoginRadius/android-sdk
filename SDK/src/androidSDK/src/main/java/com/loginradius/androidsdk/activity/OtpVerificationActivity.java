package com.loginradius.androidsdk.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;




public class OtpVerificationActivity extends AppCompatActivity {
    static EditText OtpNumber;
    String phonenumber,lrapikey,lrsitename,facebook_native,google_native,verificationUrl,emailTemplate,loginUrl,resetpassword,userlogin;
    private Button verifyotp,resendotp;
    private ProgressDialog pDialog;
    private EditText inputMobilereset;
    private TextInputLayout inputLayoutMobilereset;
    String VerifyotpUrl,promptPasswordOnSocialLogin,CustomizeLanguage,resetPasswordUrl,sott,isMobile,smsTemplate;
    CustomizeLanguageResponse customizeLanguageResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        phonenumber = getIntent().getExtras().getString("phonenumber");
        String resetphonepassword= getIntent().getExtras().getString("resetphonepassword");
        OtpNumber= (EditText) findViewById(R.id.otp);
        verifyotp = (Button) findViewById(R.id.btn_otp);
        resendotp = (Button) findViewById(R.id.resendotp);
        TextView textverification=(TextView)findViewById(R.id.textverification);
        TextView otpalertmessage=(TextView)findViewById(R.id.textotp);
        inputLayoutMobilereset= (TextInputLayout) findViewById(R.id.input_layout_mobilereset);
        inputMobilereset=(EditText) findViewById(R.id.input_mobilereset);
        lrapikey= getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("apikey", "");
        lrsitename = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("sitename", "");
        facebook_native= getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("facebooknative", "");
        google_native = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("googlenative", "");
        verificationUrl= getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("verificationurl", "");
        emailTemplate = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("emailtemplate", "");
        loginUrl = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("loginurl", "");
        promptPasswordOnSocialLogin = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("promptpasswordonsociallogin", "");
        CustomizeLanguage= getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("language", "");
        resetPasswordUrl = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("resetpasswordurl", "");
        sott = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("sott", "");
        isMobile = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("ismobile", "");
        smsTemplate = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("smstemplate", "");
        userlogin = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("usernamelogin", "");
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        DefaultLanguage defaultlang=new DefaultLanguage();
        if (CustomizeLanguage==null || CustomizeLanguage.equals("")) {
            customizeLanguageResponse = JsonDeserializer.deserializeJson(defaultlang.getLanguage(), CustomizeLanguageResponse.class);
        } else{
            customizeLanguageResponse = JsonDeserializer.deserializeJson(CustomizeLanguage, CustomizeLanguageResponse.class);
        }
        OtpNumber.setHint(customizeLanguageResponse.getOtpedittext());
        otpalertmessage.setText(customizeLanguageResponse.getOtpalertmessage());
        verifyotp.setText(customizeLanguageResponse.getOtpbutton());
        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
        if (resetphonepassword != null) {
            resetpassword = resetphonepassword;
        } else {
            resetpassword = "false";
        }
        if (resetpassword=="false"){
            resendotp.setVisibility(View.VISIBLE);
            textverification.setText(customizeLanguageResponse.getVerificationheader());
            resendotp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resendRequest();
                }
            });
        }else {
            resendotp.setVisibility(View.GONE);
            inputLayoutMobilereset.setVisibility(View.VISIBLE);
            textverification.setText(customizeLanguageResponse.getResetheader());
        }
    }
    public void recivedSms(String message) {
        try
        {
            OtpNumber.setText(message);
        }
        catch (Exception e)
        {
        }
    }

    private void sendRequest(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        JsonObject register = new JsonObject();
        params.put("apikey", lrapikey);
        if (resetpassword.equals("false")) {
            params.put("otp", OtpNumber.getText().toString());
            register.addProperty("Phone", phonenumber);
            VerifyotpUrl= Endpoint.getVerifyotpUrl();
        }else {
            register.addProperty("Phone", resetpassword);
            register.addProperty("otp",OtpNumber.getText().toString());
            register.addProperty("password",inputMobilereset.getText().toString());
            VerifyotpUrl= Endpoint.getForgotPasswordUrlMobile();

        }


        RestRequest.put(OtpVerificationActivity.this,VerifyotpUrl,params,new Gson().toJson(register),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                LoginData loginData = new Gson().fromJson(response,LoginData.class);
                List<String> result = new ArrayList<String>();

                if(loginData.getAccessToken()!=null && resetpassword=="false"){
                    hideProgressDialog();
                    Intent intent = new Intent();
                    intent.putExtra("accesstoken", loginData.getAccessToken());
                    intent.putExtra("provider", loginData.getProfile().getProvider().toLowerCase());
                    setResult(2, intent);
                    finish();//finishing activity
                  }else if (loginData.getIsPosted() && resetpassword!="false"){
                    hideProgressDialog();
                    Toast.makeText(OtpVerificationActivity.this, "You have successfully Reset your Password", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    intent.putExtra("keyname", lrapikey);
                    intent.putExtra("sitename", lrsitename);
                    intent.putExtra("ismobile", isMobile);
                    intent.putExtra("smstemplate", smsTemplate);
                    intent.putExtra("verificationurl", verificationUrl);
                    intent.putExtra("facebooknative", facebook_native);
                    intent.putExtra("googlenative", google_native);
                    intent.putExtra("emailtemplate", emailTemplate);
                    intent.putExtra("promptpasswordonsociallogin", promptPasswordOnSocialLogin);
                    intent.putExtra("usernamelogin", userlogin);
                    intent.putExtra("sott", sott);
                    intent.putExtra("resetpasswordurl", resetPasswordUrl);
                    intent.putExtra("language",CustomizeLanguage);
                    startActivityForResult(intent, 2);
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(OtpVerificationActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OtpVerificationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void resendRequest(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", lrapikey);
        JsonObject register = new JsonObject();
        register.addProperty("Phone", phonenumber);
        RestRequest.post(OtpVerificationActivity.this, Endpoint.getVerifyotpUrl(),params,new Gson().toJson(register),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                RegisterResponse registerResponse = new Gson().fromJson(response,RegisterResponse.class);
                if(registerResponse.getIsPosted()){
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(OtpVerificationActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OtpVerificationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (data != null) {
                String token = data.getStringExtra("accesstoken");
                String provider = data.getStringExtra("provider");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                setResult(2, intent);
                finish();//finishing activity
            }
        }
    }
}

