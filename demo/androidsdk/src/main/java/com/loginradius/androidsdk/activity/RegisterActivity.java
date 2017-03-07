package com.loginradius.androidsdk.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import com.loginradius.androidsdk.api.TraditionalInterfaceAPI;
import com.loginradius.androidsdk.helper.CountryCodes;
import com.loginradius.androidsdk.helper.Iso2Phone;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.response.register.Country;
import com.loginradius.androidsdk.response.register.Email;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.register.RegistrationData;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.api.SocailInterfaceAPI;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;


public class RegisterActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSignUp,btnforgotpassword,btnlogin;
    String promptPasswordOnSocialLogin,gender,custem1,custem2,custem3,custem4,custem5,userlogin;
    String lrapi,sott,verificationUrl,emailTemplate,lrsitename,google_native,facebook_native,resetPasswordUrl,smsTemplate,isMobile;
    EditText edit0,edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10;
    EditText edit11,edit12,edit13,edit14,edit15,edit16,edit17,edit18;
    private TextInputLayout inputname,inputlastname,inputusername,inputemailid,inputphonenumber,inputpassword,inputconfirmpassword,inputbirthdate;
    private TextInputLayout inputprefix,inputsuffix,inputcity,inputstate,inputcountry,inputPostalCode,inputcustem1,inputcustem2,inputcustem3,inputcustem4,inputcustem5;
    private int year;
    private List<TextInputLayout> requiredEditTexts = new ArrayList<>();
    private int month;
    private int day;
    Spinner spinner;
    static final int DATE_PICKER_ID = 1111;
    private List<Provider> providers;
    private LinearLayout layout;
    private ProgressDialog pDialog;
    private String CustomizeLanguage;
    private Spinner country;
    ArrayAdapter<String> a;
    RelativeLayout relativeLayout;
    CustomizeLanguageResponse customizeLanguageResponse;
    private CoordinatorLayout coordinatorLayout;
    private int internet=0;
    String facebook,google,UsernameLogin,promptPassword,Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("keyname")) {
                lrapi = extras.getString("keyname").replaceAll("\\s+","");
                if (lrapi.contains("yourloginradiusapikey")){
                    Toast.makeText(RegisterActivity.this, "Please Set Your ApiKey", Toast.LENGTH_LONG).show();
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

        relativeLayout = (RelativeLayout) findViewById(R.id.phoneparent);
        DefaultLanguage defaultlang = new DefaultLanguage();
        if (CustomizeLanguage == null || CustomizeLanguage.equals("")) {
            customizeLanguageResponse = JsonDeserializer.deserializeJson(defaultlang.getLanguage(), CustomizeLanguageResponse.class);
        } else {
            customizeLanguageResponse = JsonDeserializer.deserializeJson(CustomizeLanguage, CustomizeLanguageResponse.class);
        }
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        edit0 = (EditText) findViewById(R.id.registeredittext0);
        edit1 = (EditText) findViewById(R.id.registeredittext1);
        edit2 = (EditText) findViewById(R.id.registeredittext2);
        edit3 = (EditText) findViewById(R.id.registeredittext3);
        edit4 = (EditText) findViewById(R.id.registeredittext4);
        edit5 = (EditText) findViewById(R.id.registeredittext5);
        edit6 = (EditText) findViewById(R.id.registeredittext6);
        edit7 = (EditText) findViewById(R.id.registeredittext7);
        edit8 = (EditText) findViewById(R.id.registeredittext8);
        edit9 = (EditText) findViewById(R.id.registeredittext9);
        edit10 = (EditText) findViewById(R.id.registeredittext10);
        edit11 = (EditText) findViewById(R.id.registeredittext11);
        edit12 = (EditText) findViewById(R.id.registeredittext12);
        edit13 = (EditText) findViewById(R.id.registeredittext13);
        edit14 = (EditText) findViewById(R.id.registeredittext14);
        edit15 = (EditText) findViewById(R.id.registeredittext15);
        edit16 = (EditText) findViewById(R.id.registeredittext16);
        edit17 = (EditText) findViewById(R.id.registeredittext17);
        edit18 = (EditText) findViewById(R.id.registeredittext18);
        btnforgotpassword = (Button) findViewById(R.id.forgetpassword);
        btnlogin = (Button) findViewById(R.id.login);
        inputname = (TextInputLayout) findViewById(R.id.input_layout_registeredittext0);
        inputlastname = (TextInputLayout) findViewById(R.id.input_layout_registeredittext1);
        inputusername = (TextInputLayout) findViewById(R.id.input_layout_registeredittext2);
        inputemailid = (TextInputLayout) findViewById(R.id.input_layout_registeredittext3);
        inputphonenumber = (TextInputLayout) findViewById(R.id.input_layout_registeredittext4);
        inputpassword = (TextInputLayout) findViewById(R.id.input_layout_registeredittext5);
        inputconfirmpassword = (TextInputLayout) findViewById(R.id.input_layout_registeredittext6);
        inputbirthdate = (TextInputLayout) findViewById(R.id.input_layout_registeredittext7);
        inputprefix = (TextInputLayout) findViewById(R.id.input_layout_registeredittext8);
        inputsuffix = (TextInputLayout) findViewById(R.id.input_layout_registeredittext9);
        inputcity = (TextInputLayout) findViewById(R.id.input_layout_registeredittext10);
        inputstate = (TextInputLayout) findViewById(R.id.input_layout_registeredittext11);
        inputcountry = (TextInputLayout) findViewById(R.id.input_layout_registeredittext12);
        inputPostalCode = (TextInputLayout) findViewById(R.id.input_layout_registeredittext13);
        inputcustem1 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext14);
        inputcustem2 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext15);
        inputcustem3 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext16);
        inputcustem4 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext17);
        inputcustem5 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext18);
        edit0.addTextChangedListener(new MyTextWatcher(edit0));
        edit1.addTextChangedListener(new MyTextWatcher(edit1));
        edit2.addTextChangedListener(new MyTextWatcher(edit2));
        edit3.addTextChangedListener(new MyTextWatcher(edit3));
        edit4.addTextChangedListener(new MyTextWatcher(edit4));
        edit5.addTextChangedListener(new MyTextWatcher(edit5));
        edit6.addTextChangedListener(new MyTextWatcher(edit6));
        edit7.addTextChangedListener(new MyTextWatcher(edit7));
        edit8.addTextChangedListener(new MyTextWatcher(edit8));
        edit9.addTextChangedListener(new MyTextWatcher(edit9));
        edit10.addTextChangedListener(new MyTextWatcher(edit10));
        edit11.addTextChangedListener(new MyTextWatcher(edit11));
        edit12.addTextChangedListener(new MyTextWatcher(edit12));
        edit13.addTextChangedListener(new MyTextWatcher(edit13));
        edit14.addTextChangedListener(new MyTextWatcher(edit14));
        edit15.addTextChangedListener(new MyTextWatcher(edit15));
        edit16.addTextChangedListener(new MyTextWatcher(edit16));
        edit17.addTextChangedListener(new MyTextWatcher(edit17));
        edit18.addTextChangedListener(new MyTextWatcher(edit18));
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        relativeLayout = (RelativeLayout) findViewById(R.id.phoneparent);
        if (facebook != null && facebook.equals("true")) {
            facebook_native = facebook;
        } else {
            facebook_native = "false";
        }
        if (Mobile != null && Mobile.equals("true")) {
            isMobile = Mobile;
        } else {
            isMobile = "false";
        }
        if (google != null && google.equals("true")) {
            google_native = google;
        } else {
            google_native = "false";

        }

        if(UsernameLogin!=null &&UsernameLogin.equals("true")){
            isMobile="false";
            userlogin=UsernameLogin;

        }else{
            userlogin="false";
        }
        if (promptPassword != null && promptPassword.equals("true")) {
            promptPasswordOnSocialLogin = promptPassword;
        } else {
            promptPasswordOnSocialLogin = "false";

        }
        btnforgotpassword.setText(customizeLanguageResponse.buttons.get(0).getForgetpasswordbutton());
        btnlogin.setText(customizeLanguageResponse.buttons.get(0).getLoginbutton());
        btnSignUp.setText(customizeLanguageResponse.buttons.get(0).getRegisterbutton());

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
        spinner = (Spinner) findViewById(R.id.spinner);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("--" + customizeLanguageResponse.getGender().get(0).getGender().toString() + "--");
        categories.add(customizeLanguageResponse.gender.get(0).getGender1());
        categories.add(customizeLanguageResponse.gender.get(0).getGender2());


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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
                intent.putExtra("language", CustomizeLanguage);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
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
                intent.putExtra("language", CustomizeLanguage);
                startActivityForResult(intent, 2);
            }
        });

        layout = (LinearLayout) findViewById(R.id.registrinterface);
        if (lrapi.equals("your loginradius api key") || lrapi.equals("")) {
            Toast.makeText(RegisterActivity.this, "Your LoginRadius API Key Not Valid", Toast.LENGTH_LONG).show();
        }else if(isNetworkAvailable()){
            socialLogin();
        }else{
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Check Your Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
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
                                            intent.putExtra("language",CustomizeLanguage);
                                            intent.putExtra("usernamelogin",userlogin);
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
                                            intent.putExtra("language",CustomizeLanguage);
                                            intent.putExtra("usernamelogin",userlogin);
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
                        Toast.makeText(RegisterActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        final TraditionalInterfaceAPI registerinterfaceapi = new TraditionalInterfaceAPI();
        registerinterfaceapi.getResponse(value,new AsyncHandler<List<UserRegisteration>>() {
            @Override
            public void onSuccess(List<UserRegisteration> ragisterInterfacedata) {
                if(ragisterInterfacedata !=null && ragisterInterfacedata.size() >0) {
                    TextInputLayout editTextLayout = null;
                    int customFieldCount=14;
                    for (UserRegisteration data : ragisterInterfacedata) {
                        switch (data.getName()) {
                            case "firstname":
                                if (CustomizeLanguage!=null) {
                                    inputname.setVisibility(View.VISIBLE);
                                    inputname.setHint(customizeLanguageResponse.getFirstname());
                                    editTextLayout = inputname;
                                }else {
                                    inputname.setVisibility(View.VISIBLE);
                                    inputname.setHint(data.getDisplay());
                                    editTextLayout = inputname;
                                }
                                break;
                            case "lastname":
                                if (CustomizeLanguage!=null) {
                                    inputlastname.setVisibility(View.VISIBLE);
                                    inputlastname.setHint(customizeLanguageResponse.getLastname());
                                    editTextLayout = inputlastname;
                                }else {
                                    inputlastname.setVisibility(View.VISIBLE);
                                    inputlastname.setHint(data.getDisplay());
                                    editTextLayout = inputlastname;
                                }
                                break;
                            case "username":
                                if (CustomizeLanguage!=null) {
                                    inputusername.setVisibility(View.VISIBLE);
                                    inputusername.setHint(customizeLanguageResponse.getUsername());
                                    editTextLayout = inputusername;
                                }else {
                                    inputusername.setVisibility(View.VISIBLE);
                                    inputusername.setHint(data.getDisplay());
                                    editTextLayout = inputusername;
                                }
                                break;
                            case "emailid":
                                if (CustomizeLanguage!=null) {
                                    inputemailid.setVisibility(View.VISIBLE);
                                    inputemailid.setHint(customizeLanguageResponse.getEmailid());
                                    editTextLayout = inputemailid;
                                }else {
                                    inputemailid.setVisibility(View.VISIBLE);
                                    inputemailid.setHint(data.getDisplay());
                                    editTextLayout = inputemailid;
                                }
                                break;
                            case "phonenumber":
                                if (CustomizeLanguage!=null) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    inputphonenumber.setHint(customizeLanguageResponse.getPhonenumber());
                                    editTextLayout = inputphonenumber;
                                    phoneNumberCountry();
                                }else {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    inputphonenumber.setHint(data.getDisplay());
                                    editTextLayout = inputphonenumber;
                                    phoneNumberCountry();
                                }
                                break;
                            case "password":
                                if (CustomizeLanguage!=null) {
                                    inputpassword.setVisibility(View.VISIBLE);
                                    inputpassword.setHint(customizeLanguageResponse.getPassword());
                                    editTextLayout = inputpassword;
                                }else {
                                    inputpassword.setVisibility(View.VISIBLE);
                                    inputpassword.setHint(data.getDisplay());
                                    editTextLayout = inputpassword;
                                }
                                break;
                            case "confirmpassword":
                                if (CustomizeLanguage!=null) {
                                    inputconfirmpassword.setVisibility(View.VISIBLE);
                                    inputconfirmpassword.setHint(customizeLanguageResponse.getConfirmpassword());
                                    editTextLayout = inputconfirmpassword;
                                }else {
                                    inputconfirmpassword.setVisibility(View.VISIBLE);
                                    inputconfirmpassword.setHint(data.getDisplay());
                                    editTextLayout = inputconfirmpassword;
                                }
                                break;
                            case "birthdate":
                                if (CustomizeLanguage!=null) {
                                    inputbirthdate.setVisibility(View.VISIBLE);
                                    inputbirthdate.setHint(customizeLanguageResponse.getBirthdate());
                                    editTextLayout = inputbirthdate;
                                    edit7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(DATE_PICKER_ID);
                                        }
                                    });
                                }else {
                                    inputbirthdate.setVisibility(View.VISIBLE);
                                    inputbirthdate.setHint(data.getDisplay());
                                    editTextLayout = inputbirthdate;
                                    edit7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(DATE_PICKER_ID);
                                        }
                                    });
                                }
                                break;
                            case "prefix":
                                if (CustomizeLanguage!=null) {
                                    inputprefix.setVisibility(View.VISIBLE);
                                    inputprefix.setHint(customizeLanguageResponse.getPrefix());
                                    editTextLayout = inputprefix;
                                }else {
                                    inputprefix.setVisibility(View.VISIBLE);
                                    inputprefix.setHint(data.getDisplay());
                                    editTextLayout = inputprefix;
                                }
                                break;
                            case "suffix":
                                if (CustomizeLanguage!=null) {
                                    inputsuffix.setVisibility(View.VISIBLE);
                                    inputsuffix.setHint(customizeLanguageResponse.getSuffix());
                                    editTextLayout = inputsuffix;
                                }else {
                                    inputsuffix.setVisibility(View.VISIBLE);
                                    inputsuffix.setHint(data.getDisplay());
                                    editTextLayout = inputsuffix;
                                }
                                break;
                            case "city":
                                if (CustomizeLanguage!=null) {
                                    inputcity.setVisibility(View.VISIBLE);
                                    inputcity.setHint(customizeLanguageResponse.getCity());
                                    editTextLayout = inputcity;
                                }else {
                                    inputcity.setVisibility(View.VISIBLE);
                                    inputcity.setHint(data.getDisplay());
                                    editTextLayout = inputcity;
                                }
                                break;
                            case "state":
                                if (CustomizeLanguage!=null) {
                                    inputstate.setVisibility(View.VISIBLE);
                                    inputstate.setHint(customizeLanguageResponse.getState());
                                    editTextLayout = inputstate;
                                }else {
                                    inputstate.setVisibility(View.VISIBLE);
                                    inputstate.setHint(data.getDisplay());
                                    editTextLayout = inputstate;
                                }
                                break;
                            case "country":
                                if (CustomizeLanguage!=null) {
                                    inputcountry.setVisibility(View.VISIBLE);
                                    inputcountry.setHint(customizeLanguageResponse.getCountry());
                                    editTextLayout = inputcountry;
                                }else {
                                    inputcountry.setVisibility(View.VISIBLE);
                                    inputcountry.setHint(data.getDisplay());
                                    editTextLayout = inputcountry;
                                }
                                break;
                            case "PostalCode":
                                if (CustomizeLanguage!=null) {
                                    inputPostalCode.setVisibility(View.VISIBLE);
                                    inputPostalCode.setHint(customizeLanguageResponse.getPostalcode());
                                    editTextLayout = inputPostalCode;
                                }else {
                                    inputPostalCode.setVisibility(View.VISIBLE);
                                    inputPostalCode.setHint(data.getDisplay());
                                    editTextLayout = inputPostalCode;
                                }
                                break;
                            case "gender":
                                spinner.setVisibility(View.VISIBLE);
                                break;
                            default:
                                if(data.getName().contains("cf_")&&customFieldCount==14){
                                    if (CustomizeLanguage!=null){
                                        inputcustem1.setVisibility(View.VISIBLE);
                                        inputcustem1.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields1());
                                        custem1 = edit14.getText().toString();
                                        editTextLayout = inputcustem1;
                                        customFieldCount++;
                                    }else {
                                        inputcustem1.setVisibility(View.VISIBLE);
                                        inputcustem1.setHint(data.getDisplay());
                                        custem1 = edit14.getText().toString();
                                        editTextLayout = inputcustem1;
                                        customFieldCount++;
                                    }
                                }else if (data.getName().contains("cf_")&&customFieldCount==15){
                                    if (CustomizeLanguage!=null) {
                                        inputcustem2.setVisibility(View.VISIBLE);
                                        inputcustem2.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields2());
                                        custem2 = edit15.getText().toString();
                                        editTextLayout = inputcustem2;
                                        customFieldCount++;
                                    }else {
                                        inputcustem2.setVisibility(View.VISIBLE);
                                        inputcustem2.setHint(data.getDisplay());
                                        custem2 = edit15.getText().toString();
                                        editTextLayout = inputcustem2;
                                        customFieldCount++;
                                    }
                                }else if (data.getName().contains("cf_")&&customFieldCount==16) {
                                    if (CustomizeLanguage!=null) {
                                        inputcustem3.setVisibility(View.VISIBLE);
                                        inputcustem3.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields3());
                                        custem3 = edit16.getText().toString();
                                        editTextLayout = inputcustem3;
                                        customFieldCount++;
                                    }else {
                                        inputcustem3.setVisibility(View.VISIBLE);
                                        inputcustem3.setHint(data.getDisplay());
                                        custem3 = edit16.getText().toString();
                                        editTextLayout = inputcustem3;
                                        customFieldCount++;
                                    }
                                }else if (data.getName().contains("cf_")&&customFieldCount==17) {
                                    if (CustomizeLanguage!=null) {
                                        inputcustem4.setVisibility(View.VISIBLE);
                                        inputcustem4.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields4());
                                        custem4 = edit17.getText().toString();
                                        editTextLayout = inputcustem4;
                                        customFieldCount++;
                                    }else {
                                        inputcustem4.setVisibility(View.VISIBLE);
                                        inputcustem4.setHint(data.getDisplay());
                                        custem4 = edit17.getText().toString();
                                        editTextLayout = inputcustem4;
                                        customFieldCount++;
                                    }
                                }else if (data.getName().contains("cf_")&&customFieldCount==18) {
                                    if (CustomizeLanguage!=null) {
                                        inputcustem5.setVisibility(View.VISIBLE);
                                        inputcustem5.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields5());
                                        custem5 = edit18.getText().toString();
                                        editTextLayout = inputcustem5;
                                        customFieldCount++;
                                    }else {
                                        inputcustem5.setVisibility(View.VISIBLE);
                                        inputcustem5.setHint(data.getDisplay());
                                        custem5 = edit18.getText().toString();
                                        editTextLayout = inputcustem5;
                                        customFieldCount++;
                                    }
                                }
                        }
                        if(editTextLayout != null && data.getRules() != null && data.getRules().contains("required")){
                            requiredEditTexts.add(editTextLayout);
                        }
                    }

                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(RegisterActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (edit4.getHint()!=null){
            if(!validatePhone()){
                return;
            }}
        if(!validateConfirmpassword()){
            return;
        }
        for (TextInputLayout textInputLayout : requiredEditTexts){
            if(textInputLayout.getEditText() == null || textInputLayout.getEditText().getText() == null || textInputLayout.getEditText().getText().toString().isEmpty()){
                String error= textInputLayout.getHint().toString();
                textInputLayout.setError(error +" "+customizeLanguageResponse.validationMessage.get(0).getRequiredfield());
                textInputLayout.setErrorEnabled(true);
                return;
            }
        }

        sendRequest();
    }
    private void sendRequest(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", lrapi);
        params.put("sott",sott);
        if(isMobile!=null && isMobile.equals("true")){
            params.put("smsTemplate",smsTemplate);
        }else {
            params.put("verificationUrl",verificationUrl);
            params.put("emailTemplate", emailTemplate);
        }
        final RegistrationData registrationData = new RegistrationData();
        if (inputname.getVisibility()==View.VISIBLE) {
            registrationData.setFirstName(edit0.getText().toString());
        }if (inputlastname.getVisibility()==View.VISIBLE) {
            registrationData.setLastName(edit1.getText().toString());
        }if (inputusername.getVisibility()==View.VISIBLE) {
            registrationData.setUserName(edit2.getText().toString());
        }if (inputbirthdate.getVisibility()==View.VISIBLE) {
            registrationData.setBirthDate(edit7.getText().toString());
        }
        registrationData.setPassword(edit5.getText().toString());
        if (inputprefix.getVisibility()==View.VISIBLE) {
            registrationData.setPrefix(edit8.getText().toString());
        }if (inputsuffix.getVisibility()==View.VISIBLE) {
            registrationData.setSuffix(edit9.getText().toString());
        }if (inputcity.getVisibility()==View.VISIBLE) {
            registrationData.setCity(edit10.getText().toString());
        } if (inputstate.getVisibility()==View.VISIBLE) {
            registrationData.setState(edit11.getText().toString());
        } if (spinner.getVisibility()==View.VISIBLE) {
            registrationData.setGender(gender);
        }

        registrationData.setPolitical(edit13.getText().toString());
        if (inputphonenumber.getVisibility()==View.VISIBLE) {
            registrationData.setPhoneId(edit4.getText().toString().trim());
        }if(inputcountry.getVisibility()==View.VISIBLE) {
            StringBuilder b = new StringBuilder(edit12.getText().toString().toLowerCase());
            int i = 0;
            do {
                b.replace(i, i + 1, b.substring(i,i + 1).toUpperCase());
                i =  b.indexOf(" ", i) + 1;
            } while (i > 0 && i < b.length());
            CountryCodes countryCodes=new CountryCodes();
            Country countryObj = new Country();
            countryObj.setCode(countryCodes.getCode(b.toString()));
            countryObj.setName(edit12.getText().toString());
            registrationData.setCountry(countryObj);
        }if (inputemailid.getVisibility()==View.VISIBLE) {
            Email emailObj = new Email();
            emailObj.setType("Primary");
            emailObj.setValue(edit3.getText().toString());
            registrationData.setEmail(new ArrayList<Email>(Arrays.asList(emailObj)));
        }
        JsonObject CustomFields = new JsonObject();
        if (inputcustem1.getHint()!=null){
            CustomFields.addProperty(inputcustem1.getHint().toString(), edit14.getText().toString());
        } if (inputcustem2.getHint()!=null){
            CustomFields.addProperty(inputcustem2.getHint().toString(), edit15.getText().toString());
        } if (inputcustem3.getHint()!=null){
            CustomFields.addProperty(inputcustem3.getHint().toString(), edit16.getText().toString());
        } if (inputcustem4.getHint()!=null){
            CustomFields.addProperty(inputcustem4.getHint().toString(), edit17.getText().toString());
        } if (inputcustem5.getHint()!=null){
            CustomFields.addProperty(inputcustem5.getHint().toString(), edit18.getText().toString());
        }
        registrationData.setCustomFields(CustomFields);



        RestRequest.post(RegisterActivity.this, Endpoint.getRegistrationUrl(),params,new Gson().toJson(registrationData),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                RegisterResponse registerResponse = new Gson().fromJson(response,RegisterResponse.class);
                if(registerResponse.getIsPosted()){
                    if (isMobile!=null && isMobile.equals("true")) {
                        Intent intent = new Intent(getApplication(), OtpVerificationActivity.class);
                        intent.putExtra("phonenumber",registrationData.getPhoneId().toString());
                        startActivityForResult(intent, 2);
                    }else {
                        Toast.makeText(RegisterActivity.this, customizeLanguageResponse.successMessage.get(0).getRegistermessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.putExtra("keyname", lrapi);
                        intent.putExtra("sitename", lrsitename);
                        intent.putExtra("is_mobile", isMobile);
                        intent.putExtra("smsTemplate", smsTemplate);
                        intent.putExtra("verificationUrl", verificationUrl);
                        intent.putExtra("facebook_native", facebook_native);
                        intent.putExtra("google_native", google_native);
                        intent.putExtra("emailTemplate", emailTemplate);
                        intent.putExtra("promptPasswordOnSocialLogin", promptPasswordOnSocialLogin);
                        intent.putExtra("usernamelogin", userlogin);
                        intent.putExtra("sott", sott);
                        intent.putExtra("resetPasswordUrl", resetPasswordUrl);
                        intent.putExtra("Language",CustomizeLanguage);
                        startActivityForResult(intent, 2);
                    }
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
                        Toast.makeText(RegisterActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            edit7.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (item.contains(customizeLanguageResponse.gender.get(0).getGender1())){

            gender=item;
        }else if (item.contains(customizeLanguageResponse.gender.get(0).getGender2())){
            gender=item;
        }else {
            gender=null;
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private boolean validateEmail() {
        String email = edit3.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputemailid.setError(customizeLanguageResponse.validationMessage.get(0).getEmail());
            requestFocus(edit3);
            return false;
        } else {
            inputemailid.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String password=edit5.getText().toString().trim();

        if (password.isEmpty()|| !isvalidPassword(password)) {
            inputpassword.setError(customizeLanguageResponse.validationMessage.get(0).getPassword());
            requestFocus(edit5);
            return false;
        } else {
            inputpassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmpassword() {
        String confermpassword = edit6.getText().toString();
        String password=edit5.getText().toString().trim();
        if (!password.equals(confermpassword)){
            inputconfirmpassword.setError(customizeLanguageResponse.validationMessage.get(0).getConfermpassword());
            requestFocus(edit6);
            return false;
        }else {
            inputconfirmpassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        String phone = edit4.getText().toString().trim();

        if (phone.isEmpty() || !isvalidPhone(phone)) {

            return false;
        } else {

        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isvalidPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public boolean isvalidPassword(String password)
    {
        return  password.length() >= 6;
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
            if (i == R.id.registeredittext3) {
                validateEmail();

            } else if (i == R.id.registeredittext5) {
                validatePassword();

            }else if (i == R.id.registeredittext6) {
                validateConfirmpassword();
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
                edit4.setText("");
                edit4.append("    "+countryCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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


