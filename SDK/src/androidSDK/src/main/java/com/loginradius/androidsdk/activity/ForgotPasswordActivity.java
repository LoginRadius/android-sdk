package com.loginradius.androidsdk.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.loginradius.androidsdk.helper.Iso2Phone;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.password.ForgotResponse;
import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;


public class ForgotPasswordActivity extends AppCompatActivity {
    String email,Phone;
    private EditText inputEmail,inputMobile;
    private TextInputLayout inputLayoutEmail,inputLayoutMobile;
    private ProgressDialog pDialog;
    private Button btnforgot;
    private Spinner country;
    ArrayAdapter<String> a;
    CustomizeLanguageResponse customizeLanguageResponse;
    String facebook,google,UsernameLogin,promptPassword,Mobile;
    String lrapi,resetPasswordUrl,emailTemplate,isMobile,smsTemplate,verificationUrl,lrsitename,google_native,facebook_native,CustomizeLanguage,sott,promptPasswordOnSocialLogin,userlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile= (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobile=(EditText) findViewById(R.id.input_mobile);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputMobile.addTextChangedListener(new MyTextWatcher(inputMobile));
        btnforgot = (Button) findViewById(R.id.btn_forgotpassword);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.phoneparent);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("keyname")) {
                lrapi = extras.getString("keyname").replaceAll("\\s+","");
                if (lrapi.contains("yourloginradiusapikey")){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Set Your ApiKey", Toast.LENGTH_LONG).show();
                }
            }else{
                lrapi ="";
            } if (extras.containsKey("sitename")) {
                lrsitename = extras.getString("sitename").replaceAll("\\s+","");
            }else {
                lrsitename="";
            }if (extras.containsKey("sott")) {
                sott = extras.getString("sott").replaceAll("\\s+","");
            }
            if (extras.containsKey("verificationurl")) {
                verificationUrl = extras.getString("verificationurl").replaceAll("\\s+","");
            } if (extras.containsKey("emailtemplate")) {
                emailTemplate = extras.getString("emailtemplate").replaceAll("\\s+","");
            }if (extras.containsKey("ismobile")) {
                Mobile = extras.getString("ismobile").replaceAll("\\s+","");
            }if (extras.containsKey("smstemplate")) {
                smsTemplate = extras.getString("smstemplate").replaceAll("\\s+","");
            }if (extras.containsKey("resetpasswordurl")) {
                resetPasswordUrl = extras.getString("resetpasswordurl").replaceAll("\\s+","");
            }if (extras.containsKey("facebooknative")) {
                facebook = extras.getString("facebooknative").replaceAll("\\s+","");
            }if (extras.containsKey("googlenative")) {
                google = extras.getString("googlenative").replaceAll("\\s+","");
            }if (extras.containsKey("usernamelogin")) {
                UsernameLogin = extras.getString("usernamelogin").replaceAll("\\s+","");
            }if (extras.containsKey("promptpasswordonsociallogin")) {
                promptPassword  = extras.getString("promptpasswordonsociallogin").replaceAll("\\s+","");
            }if (extras.containsKey("language")) {
                CustomizeLanguage  = extras.getString("language").replaceAll("\\s+","");
            }
        }
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        DefaultLanguage defaultlang=new DefaultLanguage();
        if (CustomizeLanguage==null || CustomizeLanguage.equals("")) {
            customizeLanguageResponse = JsonDeserializer.deserializeJson(defaultlang.getLanguage(), CustomizeLanguageResponse.class);
        } else{
            customizeLanguageResponse = JsonDeserializer.deserializeJson(CustomizeLanguage, CustomizeLanguageResponse.class);
        }

        btnforgot.setText(customizeLanguageResponse.buttons.get(0).getForgetpasswordbutton());
        if (promptPassword != null && promptPassword.equals("true")) {
            promptPasswordOnSocialLogin = promptPassword;
        } else {
            promptPasswordOnSocialLogin = "false";

        }
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        if (Mobile !=null && Mobile.equals("true")){
            isMobile = Mobile;
            relativeLayout.setVisibility(View.VISIBLE);
            inputLayoutMobile.setHint(customizeLanguageResponse.getPhonenumber());
            country = (Spinner) findViewById(R.id.s);
            Locale[] locales = Locale.getAvailableLocales();
            ArrayList<String> countries = new ArrayList<String>();
            for (Locale locale : locales) {
                String country = locale.getDisplayCountry();
                if (country.trim().length() > 0 && !countries.contains(country)) {
                    countries.add(country);
                }
            }
            Collections.sort(countries);
            for (String country : countries) {
                System.out.println(country);
            }
            a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
            // set the view for the Drop down list
            // set the ArrayAdapter to the spinner
            country.setAdapter(a);
            country.setSelection(101);
            country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    country.setSelection(parent.getSelectedItemPosition());
                    Map<String, String> countrie8 = new HashMap<>();
                    for (String iso : Locale.getISOCountries()) {
                        Locale l = new Locale("", iso);
                        countrie8.put(l.getDisplayCountry(), iso);
                    }
                    String countrycode=countrie8.get(country.getSelectedItem().toString());
                    Iso2Phone iso2Phone=new Iso2Phone();
                    String countryCode= iso2Phone.getPhone(countrycode);
                    inputMobile.setText("");
                    inputMobile.append("    "+countryCode);

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }else {
            isMobile="false";
            inputLayoutEmail.setVisibility(View.VISIBLE);
            inputLayoutEmail.setHint(customizeLanguageResponse.getEmailid());
        }
        if (facebook != null && facebook.equals("true")) {
            facebook_native = facebook;
        } else {
            facebook_native = "false";

        }
        if (google != null && google.equals("true")) {
            google_native = google;
        } else {
            google_native = "false";

        }
        SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
        editor.putString("apikey", lrapi);
        editor.putString("sitename", lrsitename);
        editor.putString("facebooknative", facebook_native);
        editor.putString("googlenative", google_native);
        editor.putString("verificationurl", verificationUrl);
        editor.putString("emailtemplate", emailTemplate);
        editor.putString("smstemplate", smsTemplate);
        editor.putString("ismobile", isMobile);
        editor.putString("sott", sott);
        editor.putString("resetpasswordurl", resetPasswordUrl);
        editor.putString("language", CustomizeLanguage);
        editor.putString("promptpasswordonsociallogin", promptPasswordOnSocialLogin);
        editor.putString("usernamelogin", userlogin);
        editor.commit();
    }



    private void submitForm() {
        if (isMobile.equals("false")) {
            if (!validateEmail()) {
                return;
            }
            email = inputEmail.getText().toString();
            forgotPasswordEmail();
        }else{

            if (!validateMobile()) {
                return;
            }
            Phone = inputMobile.getText().toString().trim();
            forgotPasswordMobile();
        }
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
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }



    private boolean validateMobile() {
        String mobile = inputMobile.getText().toString().trim();

        if (mobile.isEmpty() || !isValidMobile(mobile)) {
            inputLayoutMobile.setError("Please Enter Valid Mobile Number");
            requestFocus(inputMobile);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
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
            }else if (i == R.id.input_mobile) {
                validateMobile();
            }
        }
    }
    private void forgotPasswordEmail(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", lrapi);
        params.put("resetPasswordUrl", resetPasswordUrl);
        params.put("emailTemplate", emailTemplate);
        ForgotPasswordData forgotPassworddata = new ForgotPasswordData();
        forgotPassworddata.setEmail(email);
        RestRequest.post(ForgotPasswordActivity.this, Endpoint.getForgotPasswordUrlEmail(),params,new Gson().toJson(forgotPassworddata),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                ForgotResponse forgotResponse = new Gson().fromJson(response, ForgotResponse.class);
                hideProgressDialog();
                if(forgotResponse.getIsPosted()){
                    Toast.makeText(ForgotPasswordActivity.this,customizeLanguageResponse.successMessage.get(0).getForgotpasswordmessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ForgotPasswordActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void forgotPasswordMobile(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", lrapi);
        params.put("smsTemplate",smsTemplate);
        ForgotPasswordData forgotPassworddata = new ForgotPasswordData();
        forgotPassworddata.setPhone(Phone);
        RestRequest.post(ForgotPasswordActivity.this, Endpoint.getForgotPasswordUrlMobile(),params,new Gson().toJson(forgotPassworddata),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                PhoneForgotPasswordResponse forgotResponse = new Gson().fromJson(response, PhoneForgotPasswordResponse.class);
                hideProgressDialog();
                if(forgotResponse.getIsPosted()){
                    Intent intent = new Intent(getApplication(), OtpVerificationActivity.class);
                    intent.putExtra("resetphonepassword",inputMobile.getText().toString());
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
                        Toast.makeText(ForgotPasswordActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2) {
            if (data!=null) {
                String token = data.getStringExtra("accesstoken");
                String provider=data.getStringExtra("provider");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                setResult(2, intent);
                finish();//finishing activity
            }
        }
    }

}

