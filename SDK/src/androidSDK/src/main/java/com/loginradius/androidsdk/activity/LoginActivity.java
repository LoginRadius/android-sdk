package com.loginradius.androidsdk.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.internal.LinkedTreeMap;



import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import com.loginradius.androidsdk.api.TraditionalUserLoginAPI;
import com.loginradius.androidsdk.helper.Iso2Phone;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.api.SocailInterfaceAPI;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;



public class LoginActivity extends ActionBarActivity {
    private EditText inputEmail, inputPassword,inputMobile,inputUserName;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword,inputLayoutMobile,inputLayoutUserName;
    private Button btnSignUp,btnforgotpassword,btnregister;
    private ProgressDialog pDialog;
    private List<String> info;
    private List<Provider> providers;
    private LinearLayout layout;
    private Spinner country;
    private int internet=0;
    ArrayAdapter<String> a;
    private CoordinatorLayout coordinatorLayout;
    CustomizeLanguageResponse customizeLanguageResponse;
    String  promptPasswordOnSocialLogin,sott,lrapi,verificationUrl,emailTemplate,lrsitename,google_native,facebook_native,resetPasswordUrl,smsTemplate,isMobile,CustomizeLanguage,userlogin;
    String facebook,google,UsernameLogin,promptPassword,Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("keyname")) {
                lrapi = extras.getString("keyname").replaceAll("\\s+","");
                if (lrapi.contains("yourloginradiusapikey")){
                    Toast.makeText(LoginActivity.this, "Please Set Your ApiKey", Toast.LENGTH_LONG).show();
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


        CustomizeLanguage = getIntent().getExtras().getString("language");
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutUserName = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobile = (EditText) findViewById(R.id.input_mobile);
        inputUserName=(EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputMobile.addTextChangedListener(new MyTextWatcher(inputMobile));
        inputUserName.addTextChangedListener(new MyTextWatcher(inputUserName));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnforgotpassword = (Button) findViewById(R.id.forgetpassword);
        btnregister = (Button) findViewById(R.id.register);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.phoneparent);
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
        if (Mobile !=null && Mobile.equals("true")){
            isMobile = Mobile;
            userlogin="false";
            relativeLayout.setVisibility(View.VISIBLE);
            inputLayoutMobile.setHint(customizeLanguageResponse.getPhonenumber());
            phoneNumberCountry();
        }else if(UsernameLogin!=null &&UsernameLogin.equals("true")){
            isMobile="false";
            userlogin=UsernameLogin;
            inputLayoutUserName.setVisibility(View.VISIBLE);
            inputLayoutUserName.setHint(customizeLanguageResponse.getUsername());
        }else {
            isMobile="false";
            userlogin="false";
            inputLayoutEmail.setVisibility(View.VISIBLE);
            inputLayoutEmail.setHint(customizeLanguageResponse.getEmailid());
        }
        inputLayoutPassword.setVisibility(View.VISIBLE);
        inputLayoutPassword.setHint(customizeLanguageResponse.getPassword());

        if (facebook != null && facebook.equals("true")) {
            facebook_native = facebook;
        } else {
            facebook_native = "false";
        }
        if (google != null && google.equals("true")) {
            google_native = google;
        } else {
            google_native = "false";

        }if (promptPassword != null && promptPassword.equals("true")) {
            promptPasswordOnSocialLogin = promptPassword;
        } else {
            promptPasswordOnSocialLogin = "false";

        }

        btnSignUp.setText(customizeLanguageResponse.buttons.get(0).getLoginbutton());
        btnforgotpassword.setText(customizeLanguageResponse.buttons.get(0).getForgetpasswordbutton());
        btnregister.setText(customizeLanguageResponse.buttons.get(0).getRegisterbutton());
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        btnforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ForgotPasswordActivity.class);
                intent.putExtra("keyname", lrapi);
                intent.putExtra("resetpasswordurl", resetPasswordUrl);
                intent.putExtra("sitename", lrsitename);
                intent.putExtra("emailtemplate", emailTemplate);
                intent.putExtra("facebooknative", facebook_native);
                intent.putExtra("googlenative", google_native);
                intent.putExtra("ismobile",isMobile);
                intent.putExtra("smstemplate",smsTemplate);
                intent.putExtra("verificationurl", verificationUrl);
                intent.putExtra("language",CustomizeLanguage);
                intent.putExtra("usernamelogin",userlogin);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                intent.putExtra("keyname", lrapi);
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
                startActivityForResult(intent,2);
            }
        });
        layout = (LinearLayout)findViewById(R.id.socialinterface);
        if (lrapi.equals("your loginradius api key")||lrapi.equals("")){
            Toast.makeText(LoginActivity.this, "Your LoginRadius API Key Not Valid", Toast.LENGTH_LONG).show();
        }else if(isNetworkAvailable()){
            socialLogin();
        }else{
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Check Your Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }}

    private void socialLogin(){
        LoginParams value = new LoginParams();
        value.apikey=lrapi;
        final SocailInterfaceAPI socialapi = new SocailInterfaceAPI();
        showProgressDialog();

        socialapi.getResponse(value,new AsyncHandler<SocialInterface>() {
            @Override
            public void onSuccess(SocialInterface socialInterface) {
                providers = socialInterface.getProviders();
                if(providers !=null && providers.size() >0 ) {
                    for (Provider provider : providers) {
                        switch (provider.getName()){
                            case "Facebook":
                                addButton(provider.getName(), R.drawable.facebook, R.color.facebookcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (facebook_native!=null && facebook_native.equals("true")) {
                                            Intent intent = new Intent(getApplication(), FacebookNativeActivity.class);
                                            intent.putExtra("keyName",lrapi);
                                            intent.putExtra("sitename",lrsitename);
                                            intent.putExtra("verificationurl",verificationUrl);
                                            intent.putExtra("emailtemplate",emailTemplate);
                                            intent.putExtra("ismobile",isMobile);
                                            intent.putExtra("smstemplate",smsTemplate);
                                            intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
                                            intent.putExtra("usernamelogin",userlogin);
                                            intent.putExtra("language",CustomizeLanguage);
                                            startActivityForResult(intent, 2);
                                        }else {
                                            startActivityForResult(intentValue().putExtra("action", "Facebook"), 2);
                                        }
                                    }
                                });
                                break;
                            case "Google":
                                addButton(provider.getName(), R.drawable.google, R.color.googlecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(google_native!=null && google_native.equals("true") &&checkPlayServices()){
                                            Intent intent = new Intent(getApplication(), GoogleNativeActivity.class);
                                            intent.putExtra("keyName",lrapi);
                                            intent.putExtra("sitename",lrsitename);
                                            intent.putExtra("verificationurl",verificationUrl);
                                            intent.putExtra("emailtemplate",emailTemplate);
                                            intent.putExtra("ismobile",isMobile);
                                            intent.putExtra("smstemplate",smsTemplate);
                                            intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
                                            intent.putExtra("usernamelogin",userlogin);
                                            intent.putExtra("language",CustomizeLanguage);
                                            startActivityForResult(intent, 2);
                                        }else {
                                            startActivityForResult(intentValue().putExtra("action", "Google"), 2);
                                        }
                                    }
                                });
                                break;
                            case "Twitter":
                                addButton(provider.getName(), R.drawable.twitter, R.color.twittercolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Twitter"), 2);
                                    }
                                });
                                break;
                            case "LinkedIn":
                                addButton(provider.getName(), R.drawable.linkedin,R.color.linkedincolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "LinkedIn"), 2);
                                    }
                                });
                                break;
                            case "Yahoo":
                                addButton(provider.getName(), R.drawable.yahoo,R.color.yahoocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Yahoo"), 2);
                                    }
                                });
                                break;

                            case "Instagram":
                                addButton(provider.getName(), R.drawable.instagram,R.color.instagramcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Instagram"), 2);
                                    }
                                });
                                break;
                            case "Amazon":
                                addButton(provider.getName(), R.drawable.amazon,R.color.amazoncolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Amazon"), 2);
                                    }
                                });
                                break;
                            case "Live":
                                addButton(provider.getName(), R.drawable.live,R.color.livecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Live"), 2);
                                    }
                                });
                                break;
                            case "Vkontakte":
                                addButton(provider.getName(), R.drawable.vkontakte,R.color.vkontaktecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Vkontakte"), 2);
                                    }
                                });
                                break;
                            case "Disqus":
                                addButton(provider.getName(), R.drawable.disqus,R.color.disquscolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Disqus"), 2);
                                    }
                                });
                                break;
                            case "AOL":
                                addButton(provider.getName(), R.drawable.aol,R.color.aolcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "AOL"), 2);
                                    }
                                });
                                break;
                            case "Pinterest":
                                addButton(provider.getName(), R.drawable.pinterest, R.color.pinterestcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Pinterest"), 2);
                                    }
                                });
                                break;
                            case "Mixi":
                                addButton(provider.getName(), R.drawable.mixi,R.color.mixicolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Mixi"), 2);
                                    }
                                });
                                break;
                            case "Steamcommunity":
                                addButton(provider.getName(), R.drawable.steamcommunity,R.color.steamcommunitycolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Steamcommunity"), 2);
                                    }
                                });
                                break;
                            case "Hyves":
                                addButton(provider.getName(), R.drawable.hyves, R.color.hyvescolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Hyves"), 2);
                                    }
                                });
                                break;
                            case "LiveJournal":
                                addButton(provider.getName(), R.drawable.livejournal,R.color.livejournalcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "LiveJournal"), 2);
                                    }
                                });
                                break;
                            case "Verisign":
                                addButton(provider.getName(), R.drawable.verisign,R.color.verisigncolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Verisign"), 2);
                                    }
                                });
                                break;
                            case "Virgilio":
                                addButton(provider.getName(), R.drawable.virgilio,R.color.virgiliocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Virgilio"), 2);
                                    }
                                });
                                break;
                            case "FourSquare":
                                addButton(provider.getName(), R.drawable.foursquare, R.color.foursquarecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "FourSquare"), 2);
                                    }
                                });
                                break;
                            case "GitHub":
                                addButton(provider.getName(), R.drawable.github, R.color.githubcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "GitHub"), 2);
                                    }
                                });
                                break;
                            case "OpenID":
                                addButton(provider.getName(), R.drawable.openid,R.color.openidcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "OpenID"), 2);
                                    }
                                });
                                break;
                            case "Renren":
                                addButton(provider.getName(), R.drawable.renren,R.color.renrencolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Renren"), 2);
                                    }
                                });
                                break;
                            case "Kaixin":
                                addButton(provider.getName(), R.drawable.kaixin, R.color.kaixincolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Kaixin"), 2);
                                    }
                                });
                                break;
                            case "Qq":
                                addButton(provider.getName(), R.drawable.qq,R.color.qqcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Qq"), 2);
                                    }
                                });
                                break;
                            case "Stackexchange":
                                addButton(provider.getName(), R.drawable.stackexchange,R.color.stackexchangecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Stackexchange"), 2);
                                    }
                                });
                                break;
                            case "Salesforce":
                                addButton(provider.getName(), R.drawable.salesforce,R.color.salesforcecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Salesforce"), 2);
                                    }
                                });
                                break;
                            case "Odnoklassniki":
                                addButton(provider.getName(), R.drawable.odnoklassniki,R.color.odnoklassnikicolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Odnoklassniki"), 2);
                                    }
                                });
                                break;
                            case "Paypal":
                                addButton(provider.getName(), R.drawable.paypal,R.color.paypalcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Paypal"), 2);
                                    }
                                });
                                break;
                            case "Sinaweibo":
                                addButton(provider.getName(), R.drawable.sinaweibo,R.color.sinaweibocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Sinaweibo"), 2);
                                    }
                                });
                                break;
                            case "Wordpress":
                                addButton(provider.getName(), R.drawable.wordpress,R.color.wordpresscolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Wordpress"), 2);
                                    }
                                });
                                break;
                            case "mailru":
                                addButton(provider.getName(), R.drawable.mailru,R.color.mailrucolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "mailru"), 2);
                                    }
                                });
                                break;
                            case "Xing":
                                addButton(provider.getName(), R.drawable.xing,R.color.xingcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Xing"), 2);
                                    }
                                });
                                break;
                            case "Line":
                                addButton(provider.getName(), R.drawable.line,R.color.linecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Line"), 2);
                                    }
                                });
                                break;
                        }
                        hideProgressDialog();
                        internet=1;
                    }
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                internet=1;
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(LoginActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        long delayInMillis = 15000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (internet!=1) {
                    hideProgressDialog();
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Your Internet Connection Is Too Slow", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{

                }
            }
        }, delayInMillis);

    }

    private void submitForm() {
        if (userlogin.equals("false")&& isMobile.equals("false")) {
            if (!validateEmail()) {
                return;
            }
        }
        if (!validatePassword()) {
            return;
        }
        LoginParams value = new LoginParams();
        if (isMobile!=null && isMobile.equals("true")){
            value.phone=inputMobile.getText().toString().trim();
        }else if(userlogin!=null &&userlogin.equals("true")){
            value.username = inputUserName.getText().toString();
        }else {
            value.email = inputEmail.getText().toString();
        }
        value.password=inputPassword.getText().toString();
        value.verificationUrl=verificationUrl;
        value.apikey=lrapi;
        value.emailTemplate=emailTemplate;
        login(value);
    }



    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(customizeLanguageResponse.validationMessage.get(0).getEmail());
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(customizeLanguageResponse.validationMessage.get(0).getPassword());
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
    private void addButton(String name, int imageIcon,int color, View.OnClickListener onClickListener) {
        View child = getLayoutInflater().inflate(R.layout.socialbuttons, null);
        LinearLayout button = (LinearLayout) child.findViewById(R.id.button);
        ImageView image=(ImageView)child.findViewById(R.id.socialimg);
        TextView text=(TextView)child.findViewById(R.id.socialtext);
        button.setOnClickListener(onClickListener);
        image.setBackgroundDrawable(getResources().getDrawable(imageIcon));
        button.setBackgroundColor(getResources().getColor(color));
        text.setText(customizeLanguageResponse.getSocialprovider()+" "+name);
        layout.addView(child);
    }
    private void login(final LoginParams value) {
        showProgressDialog();
        TraditionalUserLoginAPI userAPI = new TraditionalUserLoginAPI();
        userAPI.getResponse(value,new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData logindata) {
                List<String> result = new ArrayList<String>();
                try {
                    for (Field field : logindata.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = field.get(logindata);
                        hideProgressDialog();
                        Intent intent = new Intent();
                        intent.putExtra("accesstoken", logindata.getAccessToken());
                        intent.putExtra("provider", logindata.getProfile().getProvider().toLowerCase());
                        setResult(2, intent);
                        finish();//finishing activity
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                if(errorResponse.getErrorCode().toString().contains("1066")){
                    Intent intent = new Intent(getApplication(), OtpVerificationActivity.class);
                    intent.putExtra("phonenumber",value.getPhone().toString());
                    startActivityForResult(intent, 2);
                }else {
                    Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                    for (Object i : ad) {
                        LinkedTreeMap customerror = (LinkedTreeMap) i;
                        if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                            Toast.makeText(LoginActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }


    private void phoneNumberCountry(){

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
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
            } else {
                //  Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    private Intent intentValue(){
        Intent intent = new Intent(getApplication(), WebViewActivity.class);
        intent.putExtra("keyName",lrapi);
        intent.putExtra("sitename",lrsitename);
        intent.putExtra("verificationurl",verificationUrl);
        intent.putExtra("emailtemplate",emailTemplate);
        intent.putExtra("ismobile",isMobile);
        intent.putExtra("smstemplate",smsTemplate);
        intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
        intent.putExtra("language",CustomizeLanguage);
        intent.putExtra("usernamelogin",userlogin);
        return intent;
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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

