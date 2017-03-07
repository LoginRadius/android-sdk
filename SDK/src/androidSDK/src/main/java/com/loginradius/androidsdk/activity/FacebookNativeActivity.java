package com.loginradius.androidsdk.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.api.TraditionalInterfaceAPI;
import com.loginradius.androidsdk.api.UserProfileAPI;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.CountryCodes;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.helper.Iso2Phone;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.AppInformation;
import com.loginradius.androidsdk.response.Provider;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.helper.FB_Permission;
import com.loginradius.androidsdk.helper.ProviderPermissions;
import com.loginradius.androidsdk.helper.lrLoginManager;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FacebookNativeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CallbackManager callManager;
    String lrapikey,sitename,CustomizeLanguage,spinview,userlogin;
    private Button btnSignUp;
    String verificationUrl,emailTemplate,smsTemplate,isMobile,promptPasswordOnSocialLogin;
    String gender,custem1,custem2,custem3,custem4,custem5;
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
    private ProgressDialog pDialog;
    private String profile;
    CustomizeLanguageResponse customizeLanguageResponse;
    private Spinner country;
    ArrayAdapter<String> a;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_native);
        lrapikey = getIntent().getExtras().getString("keyName");
        sitename = getIntent().getExtras().getString("sitename");
        verificationUrl = getIntent().getExtras().getString("verificationurl");
        emailTemplate = getIntent().getExtras().getString("emailtemplate");
        String Mobile= getIntent().getExtras().getString("ismobile");
        smsTemplate=getIntent().getExtras().getString("smstemplate");
        promptPasswordOnSocialLogin=getIntent().getExtras().getString("promptpasswordonsociallogin");
        CustomizeLanguage = getIntent().getExtras().getString("language");
        String UsernameLogin = getIntent().getExtras().getString("usernamelogin");
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        lrLoginManager.nativeLogin = true;
        callManager= CallbackManager.Factory.create();
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);
        ProviderPermissions.addFbPermission(FB_Permission.USER_EMAIL);
        final String dataapi = getIntent().getExtras().getString("keyName");
        String apiKey= dataapi;
        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {
                        for (Provider p : appInfo.Providers) {
                            if (p.Name.equalsIgnoreCase("facebook")) {
                                showdialog(p);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {
                    }
                });

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        edit0=(EditText)findViewById(R.id.registeredittext0);
        edit1=(EditText)findViewById(R.id.registeredittext1);
        edit2=(EditText)findViewById(R.id.registeredittext2);
        edit3=(EditText)findViewById(R.id.registeredittext3);
        edit4=(EditText)findViewById(R.id.registeredittext4);
        edit5=(EditText)findViewById(R.id.registeredittext5);
        edit6=(EditText)findViewById(R.id.registeredittext6);
        edit7=(EditText)findViewById(R.id.registeredittext7);
        edit8=(EditText)findViewById(R.id.registeredittext8);
        edit9=(EditText)findViewById(R.id.registeredittext9);
        edit10=(EditText)findViewById(R.id.registeredittext10);
        edit11=(EditText)findViewById(R.id.registeredittext11);
        edit12=(EditText)findViewById(R.id.registeredittext12);
        edit13=(EditText)findViewById(R.id.registeredittext13);
        edit14=(EditText)findViewById(R.id.registeredittext14);
        edit15=(EditText)findViewById(R.id.registeredittext15);
        edit16=(EditText)findViewById(R.id.registeredittext16);
        edit17=(EditText)findViewById(R.id.registeredittext17);
        edit18=(EditText)findViewById(R.id.registeredittext18);
        inputname = (TextInputLayout) findViewById(R.id.input_layout_registeredittext0);
        inputlastname = (TextInputLayout) findViewById(R.id.input_layout_registeredittext1);
        inputusername=(TextInputLayout) findViewById(R.id.input_layout_registeredittext2);
        inputemailid = (TextInputLayout) findViewById(R.id.input_layout_registeredittext3);
        inputphonenumber = (TextInputLayout) findViewById(R.id.input_layout_registeredittext4);
        inputpassword= (TextInputLayout) findViewById(R.id.input_layout_registeredittext5);
        inputconfirmpassword = (TextInputLayout) findViewById(R.id.input_layout_registeredittext6);
        inputbirthdate= (TextInputLayout) findViewById(R.id.input_layout_registeredittext7);
        inputprefix = (TextInputLayout) findViewById(R.id.input_layout_registeredittext8);
        inputsuffix = (TextInputLayout) findViewById(R.id.input_layout_registeredittext9);
        inputcity = (TextInputLayout) findViewById(R.id.input_layout_registeredittext10);
        inputstate= (TextInputLayout) findViewById(R.id.input_layout_registeredittext11);
        inputcountry = (TextInputLayout) findViewById(R.id.input_layout_registeredittext12);
        inputPostalCode = (TextInputLayout) findViewById(R.id.input_layout_registeredittext13);
        inputcustem1 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext14);
        inputcustem2 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext15);
        inputcustem3= (TextInputLayout) findViewById(R.id.input_layout_registeredittext16);
        inputcustem4 = (TextInputLayout) findViewById(R.id.input_layout_registeredittext17);
        inputcustem5= (TextInputLayout) findViewById(R.id.input_layout_registeredittext18);
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
        pDialog.setCancelable(false);
        spinner= (Spinner) findViewById(R.id.spinner);
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        spinner.setOnItemSelectedListener(this);
        relativeLayout=(RelativeLayout)findViewById(R.id.phoneparent);
        DefaultLanguage defaultlang=new DefaultLanguage();
        if (CustomizeLanguage==null || CustomizeLanguage.equals("")) {
            customizeLanguageResponse = JsonDeserializer.deserializeJson(defaultlang.getLanguage(), CustomizeLanguageResponse.class);
        } else{
            customizeLanguageResponse = JsonDeserializer.deserializeJson(CustomizeLanguage, CustomizeLanguageResponse.class);
        }
        if (Mobile!=null&&Mobile.equals("true")){
            isMobile=Mobile;
        }else {
            isMobile="false";
        }
        if(UsernameLogin!=null &&UsernameLogin.equals("true")){
            isMobile="false";
            userlogin=UsernameLogin;

        }else{
            userlogin="false";
        }
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("--"+customizeLanguageResponse.getGender().get(0).getGender().toString()+"--");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
        }else {
            callManager.onActivityResult(requestCode, resultCode, data);
        }

    }
    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(FacebookNativeActivity.this, p, new AsyncHandler<lrAccessToken>() {
            @Override
            public void onSuccess(final lrAccessToken data) {
                showProgressDialog();
                final lrAccessToken accessToken = new lrAccessToken();
                accessToken.access_token = data.access_token;
                accessToken.provider = "facebook";
                accessToken.apikey=lrapikey;
                final UserProfileAPI userAPI = new UserProfileAPI();
                userAPI.getResponse(accessToken, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
                    @Override
                    public void onSuccess(final LoginRadiusUltimateUserProfile userProfile) {
                        List<String> result = new ArrayList<String>();
                        try {
                            for (Field field : userProfile.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                Object value = field.get(userProfile);
                                SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
                                editor.putString("webtoken", data.access_token);
                                editor.putString("webprovider","google");
                                editor.apply();
                                mapSchema(userProfile,accessToken);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Throwable error, String errorcode) {
                        hideProgressDialog();
                        ErrorResponse errorResponse= JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                        if (errorcode.equals("lr_API_NOT_SUPPORTED")){
                            Toast.makeText(FacebookNativeActivity.this, errorcode, Toast.LENGTH_LONG).show();
                        }else {
                            Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                            for (Object i : ad) {
                                LinkedTreeMap customerror = (LinkedTreeMap) i;
                                if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                                    Toast.makeText(FacebookNativeActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(FacebookNativeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {

            }
        });
    }

    private void mapSchema(final LoginRadiusUltimateUserProfile userProfile, final lrAccessToken token) {
        LoginParams value = new LoginParams();
        value.apikey=lrapikey;
        final TraditionalInterfaceAPI registerinterfaceapi = new TraditionalInterfaceAPI();
        registerinterfaceapi.getResponse(value,new AsyncHandler<List<UserRegisteration>>() {
            @Override
            public void onSuccess(List<UserRegisteration> ragisterInterfacedata) {
                if(ragisterInterfacedata !=null && ragisterInterfacedata.size() >0) {
                    TextInputLayout editTextLayout = null;
                    int customFieldCount=14;
                    Boolean flag =false;
                    for (UserRegisteration data : ragisterInterfacedata) {
                        if (data.getRules().contains("required")){
                            flag=true;
                        }
                    }
                    if (flag==false){
                        sendAccessToken(token.access_token);
                        return;
                    }


                    Boolean permissionflag =false;
                    for (int i=0; i<ragisterInterfacedata.size(); i++){
                        UserRegisteration data=ragisterInterfacedata.get(i);
                        switch (data.getName()) {
                            case "firstname":
                                if (userProfile.FirstName==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputname.setVisibility(View.VISIBLE);
                                        inputname.setHint(customizeLanguageResponse.getFirstname());
                                        editTextLayout = inputname;
                                        permissionflag=true;
                                    }else {
                                        inputname.setVisibility(View.VISIBLE);
                                        inputname.setHint(data.getDisplay());
                                        editTextLayout = inputname;
                                        permissionflag=true;
                                    }}
                                break;
                            case "lastname":
                                if (userProfile.LastName==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputlastname.setVisibility(View.VISIBLE);
                                        inputlastname.setHint(customizeLanguageResponse.getLastname());
                                        editTextLayout = inputlastname;
                                        permissionflag=true;
                                    }else {
                                        inputlastname.setVisibility(View.VISIBLE);
                                        inputlastname.setHint(data.getDisplay());
                                        editTextLayout = inputlastname;
                                        permissionflag=true;
                                    }}
                                break;
                            case "username":
                                if (userProfile.getUserName()==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputusername.setVisibility(View.VISIBLE);
                                        inputusername.setHint(customizeLanguageResponse.getUsername());
                                        editTextLayout = inputusername;
                                        permissionflag=true;
                                    }else {
                                        inputusername.setVisibility(View.VISIBLE);
                                        inputusername.setHint(data.getDisplay());
                                        editTextLayout = inputusername;
                                        permissionflag=true;
                                    }}
                                break;
                            case "emailid":
                                if (userProfile.getEmailVerified().toString().equals("false") && !(isMobile.equals("true")) && flag.equals(true)){
                                    if (CustomizeLanguage!=null && isMobile.equals("false")) {
                                        inputemailid.setVisibility(View.VISIBLE);
                                        inputemailid.setHint(customizeLanguageResponse.getEmailid());
                                        editTextLayout = inputemailid;
                                        permissionflag=true;
                                    }else if(userProfile.getEmailVerified().toString().equals("false") && isMobile.equals("false")){
                                        inputemailid.setVisibility(View.VISIBLE);
                                        inputemailid.setHint(data.getDisplay());
                                        editTextLayout = inputemailid;
                                        permissionflag=true;
                                    }}else if(userProfile.Email==null && isMobile.equals("true")){
                                    if (CustomizeLanguage!=null) {
                                        inputemailid.setVisibility(View.VISIBLE);
                                        inputemailid.setHint(customizeLanguageResponse.getEmailid());
                                        editTextLayout = inputemailid;
                                        permissionflag=true;
                                    }else{
                                        inputemailid.setVisibility(View.VISIBLE);
                                        inputemailid.setHint(data.getDisplay());
                                        editTextLayout = inputemailid;
                                        permissionflag=true;
                                    }
                                }

                                break;
                            case "phonenumber":
                                if (userProfile.PhoneIdVerified.equals("false") && isMobile.equals("true") && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        inputphonenumber.setHint(customizeLanguageResponse.getPhonenumber());
                                        editTextLayout = inputphonenumber;
                                        phoneNumberCountry();
                                        permissionflag=true;
                                    }else {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        inputphonenumber.setHint(data.getDisplay());
                                        editTextLayout = inputphonenumber;
                                        phoneNumberCountry();
                                        permissionflag=true;

                                    }
                                }else if(userProfile.PhoneNumbers==null&& !(isMobile.equals("true"))){

                                    if (CustomizeLanguage!=null) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        inputphonenumber.setHint(customizeLanguageResponse.getPhonenumber());
                                        editTextLayout = inputphonenumber;
                                        phoneNumberCountry();
                                        permissionflag=true;

                                    }else {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        inputphonenumber.setHint(data.getDisplay());
                                        editTextLayout = inputphonenumber;
                                        phoneNumberCountry();
                                        permissionflag=true;

                                    }


                                }
                                break;
                            case "password":
                                if (userProfile.getPassword()==null && flag.equals(true) &&userlogin.equals("true")){
                                    if (CustomizeLanguage!=null) {
                                        inputpassword.setVisibility(View.VISIBLE);
                                        inputpassword.setHint(customizeLanguageResponse.getPassword());
                                        editTextLayout = inputpassword;
                                        permissionflag=true;
                                    }else {
                                        inputpassword.setVisibility(View.VISIBLE);
                                        inputpassword.setHint(data.getDisplay());
                                        editTextLayout = inputpassword;
                                        permissionflag=true;
                                    }}
                                break;
                            case "confirmpassword":
                                if (userProfile.getPassword()==null && flag.equals(true) &&userlogin.equals("true")){
                                    if (CustomizeLanguage!=null) {
                                        inputconfirmpassword.setVisibility(View.VISIBLE);
                                        inputconfirmpassword.setHint(customizeLanguageResponse.getConfirmpassword());
                                        editTextLayout = inputconfirmpassword;
                                        permissionflag=true;
                                    }else {
                                        inputconfirmpassword.setVisibility(View.VISIBLE);
                                        inputconfirmpassword.setHint(data.getDisplay());
                                        editTextLayout = inputconfirmpassword;
                                        permissionflag=true;
                                    }}
                                break;
                            case "birthdate":
                                if (userProfile.BirthDate==null && flag.equals(true)){
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
                                        permissionflag=true;
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
                                        permissionflag=true;
                                    }}
                                break;
                            case "prefix":
                                if (userProfile.Prefix==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputprefix.setVisibility(View.VISIBLE);
                                        inputprefix.setHint(customizeLanguageResponse.getPrefix());
                                        editTextLayout = inputprefix;
                                        permissionflag=true;
                                    }else {
                                        inputprefix.setVisibility(View.VISIBLE);
                                        inputprefix.setHint(data.getDisplay());
                                        editTextLayout = inputprefix;
                                        permissionflag=true;
                                    }}
                                break;
                            case "suffix":
                                if (userProfile.Suffix==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputsuffix.setVisibility(View.VISIBLE);
                                        inputsuffix.setHint(customizeLanguageResponse.getSuffix());
                                        editTextLayout = inputsuffix;
                                        permissionflag=true;
                                    }else {
                                        inputsuffix.setVisibility(View.VISIBLE);
                                        inputsuffix.setHint(data.getDisplay());
                                        editTextLayout = inputsuffix;
                                        permissionflag=true;
                                    }}
                                break;
                            case "city":
                                if (userProfile.City==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputcity.setVisibility(View.VISIBLE);
                                        inputcity.setHint(customizeLanguageResponse.getCity());
                                        editTextLayout = inputcity;
                                        permissionflag=true;
                                    }else {
                                        inputcity.setVisibility(View.VISIBLE);
                                        inputcity.setHint(data.getDisplay());
                                        editTextLayout = inputcity;
                                        permissionflag=true;
                                    }}
                                break;
                            case "state":
                                if (userProfile.State==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputstate.setVisibility(View.VISIBLE);
                                        inputstate.setHint(customizeLanguageResponse.getState());
                                        editTextLayout = inputstate;
                                        permissionflag=true;
                                    }else {
                                        inputstate.setVisibility(View.VISIBLE);
                                        inputstate.setHint(data.getDisplay());
                                        editTextLayout = inputstate;
                                        permissionflag=true;
                                    }}
                                break;
                            case "country":
                                if (userProfile.Country==null && flag.equals(true)){
                                    if (CustomizeLanguage!=null) {
                                        inputcountry.setVisibility(View.VISIBLE);
                                        inputcountry.setHint(customizeLanguageResponse.getCountry());
                                        editTextLayout = inputcountry;
                                        permissionflag=true;
                                    }else {
                                        inputcountry.setVisibility(View.VISIBLE);
                                        inputcountry.setHint(data.getDisplay());
                                        editTextLayout = inputcountry;
                                        permissionflag=true;
                                    }}
                                break;
                            case "PostalCode":
                                if (data.getRules().contains("required")) {
                                    if (CustomizeLanguage!=null) {
                                        inputPostalCode.setVisibility(View.VISIBLE);
                                        inputPostalCode.setHint(customizeLanguageResponse.getPostalcode());
                                        editTextLayout = inputPostalCode;
                                        permissionflag=true;
                                    }else {
                                        inputPostalCode.setVisibility(View.VISIBLE);
                                        inputPostalCode.setHint(data.getDisplay());
                                        editTextLayout = inputPostalCode;
                                        permissionflag=true;
                                    }}
                                break;
                            case "gender":
                                if(userProfile.Gender==null && flag.equals(true)) {
                                    spinner.setVisibility(View.VISIBLE);
                                    spinview="true";
                                    permissionflag=true;
                                }
                                break;
                            default:
                                if (userProfile.getCustomFields()==null && flag.equals(true)){
                                    if(data.getName().contains("cf_")&&customFieldCount==14){
                                        if (CustomizeLanguage!=null){
                                            inputcustem1.setVisibility(View.VISIBLE);
                                            inputcustem1.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields1());
                                            custem1 = edit14.getText().toString();
                                            editTextLayout = inputcustem1;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }else {
                                            inputcustem1.setVisibility(View.VISIBLE);
                                            inputcustem1.setHint(data.getDisplay());
                                            custem1 = edit14.getText().toString();
                                            editTextLayout = inputcustem1;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }
                                    }else if (data.getName().contains("cf_")&&customFieldCount==15){
                                        if (CustomizeLanguage!=null) {
                                            inputcustem2.setVisibility(View.VISIBLE);
                                            inputcustem2.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields2());
                                            custem2 = edit15.getText().toString();
                                            editTextLayout = inputcustem2;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }else {
                                            inputcustem2.setVisibility(View.VISIBLE);
                                            inputcustem2.setHint(data.getDisplay());
                                            custem2 = edit15.getText().toString();
                                            editTextLayout = inputcustem2;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }
                                    }else if (data.getName().contains("cf_")&&customFieldCount==16) {
                                        if (CustomizeLanguage!=null) {
                                            inputcustem3.setVisibility(View.VISIBLE);
                                            inputcustem3.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields3());
                                            custem3 = edit16.getText().toString();
                                            editTextLayout = inputcustem3;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }else {
                                            inputcustem3.setVisibility(View.VISIBLE);
                                            inputcustem3.setHint(data.getDisplay());
                                            custem3 = edit16.getText().toString();
                                            editTextLayout = inputcustem3;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }
                                    }else if (data.getName().contains("cf_")&&customFieldCount==17) {
                                        if (CustomizeLanguage!=null) {
                                            inputcustem4.setVisibility(View.VISIBLE);
                                            inputcustem4.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields4());
                                            custem4 = edit17.getText().toString();
                                            editTextLayout = inputcustem4;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }else {
                                            inputcustem4.setVisibility(View.VISIBLE);
                                            inputcustem4.setHint(data.getDisplay());
                                            custem4 = edit17.getText().toString();
                                            editTextLayout = inputcustem4;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }
                                    }else if (data.getName().contains("cf_")&&customFieldCount==18) {
                                        if (CustomizeLanguage!=null) {
                                            inputcustem5.setVisibility(View.VISIBLE);
                                            inputcustem5.setHint(customizeLanguageResponse.customfields.get(0).getCustomfields5());
                                            custem5 = edit18.getText().toString();
                                            editTextLayout = inputcustem5;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }else {
                                            inputcustem5.setVisibility(View.VISIBLE);
                                            inputcustem5.setHint(data.getDisplay());
                                            custem5 = edit18.getText().toString();
                                            editTextLayout = inputcustem5;
                                            customFieldCount++;
                                            permissionflag=true;
                                        }
                                    }}
                        }
                        if(editTextLayout != null && data.getRules() != null && data.getRules().contains("required")){
                            requiredEditTexts.add(editTextLayout);
                        }

                        hideProgressDialog();


                    }
                    if (permissionflag==true){
                        LinearLayout lirass,libutton;
                        lirass=(LinearLayout)findViewById(R.id.raas);
                        libutton=(LinearLayout)findViewById(R.id.rassbutton);
                        lirass.setVisibility(View.VISIBLE);
                        libutton.setVisibility(View.VISIBLE);
                    }else if(permissionflag==false){
                        sendAccessToken(token.access_token);
                    }
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
                        Toast.makeText(FacebookNativeActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FacebookNativeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void submitForm() {

        if (inputemailid.getVisibility() == View.VISIBLE) {
            if (!validateEmail()) {
                return;
            }
        }
        if (inputpassword.getVisibility() == View.VISIBLE) {
            if (!validatePassword()) {
                return;
            }
        }

        if (inputconfirmpassword.getVisibility() == View.VISIBLE){
            if (!validateConfirmpassword()) {
                return;
            }
        }
        for (TextInputLayout textInputLayout : requiredEditTexts){
            if(textInputLayout.getEditText() == null || textInputLayout.getEditText().getText() == null || textInputLayout.getEditText().getText().toString().isEmpty()){
                String error= textInputLayout.getHint().toString();
                textInputLayout.setError(error +" "+customizeLanguageResponse.validationMessage.get(0).getRequiredfield());
                textInputLayout.setErrorEnabled(true);
                return;
            }
        }
        UpdateProfile();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void UpdateProfile(){
        showProgressDialog();
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", lrapikey);
        params.put("access_token",getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("webtoken", ""));
        if(isMobile!=null && isMobile.equals("true")){
            params.put("smsTemplate",smsTemplate);//if update phonenumber
        }else {
            params.put("verificationUrl", verificationUrl);//optional if update email
            params.put("emailTemplate", emailTemplate);// optional if update email
        }
        final LoginParams value = new LoginParams();
        JsonObject update = new JsonObject();
        if (inputname.getVisibility()==View.VISIBLE){
            update.addProperty("FirstName",edit0.getText().toString());
        }if (inputlastname.getVisibility()==View.VISIBLE){
            update.addProperty("LastName",edit1.getText().toString());
        }if (inputusername.getVisibility()==View.VISIBLE){
            update.addProperty("UserName",edit2.getText().toString());
        }if (inputbirthdate.getVisibility()==View.VISIBLE){
            update.addProperty("BirthDate",edit7.getText().toString());
        }if (inputprefix.getVisibility()==View.VISIBLE){
            update.addProperty("Prefix",edit8.getText().toString());
        }if (inputsuffix.getVisibility()==View.VISIBLE){
            update.addProperty("Suffix",edit9.getText().toString());
        }if (inputcity.getVisibility()==View.VISIBLE){
            update.addProperty("City",edit10.getText().toString());
        } if (inputstate.getVisibility()==View.VISIBLE){
            update.addProperty("State",edit11.getText().toString());
        }
        if (spinner.getVisibility()==View.VISIBLE){
            update.addProperty("Gender",gender);
        }
        if (inputpassword.getVisibility()==View.VISIBLE) {
            update.addProperty("Password",edit5.getText().toString());
        }
        if (inputphonenumber.getVisibility()==View.VISIBLE){
            if (isMobile.equals("true")) {
                update.addProperty("PhoneId", edit4.getText().toString().trim());
                value.phone = edit4.getText().toString().trim();
            }else {

                JsonArray datasets = new JsonArray();
                JsonObject dataset = new JsonObject();
                dataset.addProperty("PhoneType", "Mobile");
                dataset.addProperty("PhoneNumber", edit4.getText().toString().trim());
                datasets.add(dataset);
                update.add("PhoneNumbers", datasets);

            }
        }
        if (inputemailid.getVisibility()==View.VISIBLE) {
            JsonArray datasets = new JsonArray();
            JsonObject dataset = new JsonObject();
            dataset.addProperty("Type", "Primary");
            dataset.addProperty("Value", edit3.getText().toString());
            datasets.add(dataset);
            update.add("Email", datasets);
        }
        if (inputcountry.getVisibility()==View.VISIBLE) {
            StringBuilder b = new StringBuilder(edit12.getText().toString().toLowerCase());
            int i = 0;
            do {
                b.replace(i, i + 1, b.substring(i,i + 1).toUpperCase());
                i =  b.indexOf(" ", i) + 1;
            } while (i > 0 && i < b.length());
            CountryCodes countryCodes=new CountryCodes();
            JsonObject dataset1 = new JsonObject();
            dataset1.addProperty("Code", countryCodes.getCode(b.toString()));
            dataset1.addProperty("Name", edit12.getText().toString());
            update.add("Country", dataset1);
        }

        if (inputcustem1.getVisibility()==View.VISIBLE) {
            //  JsonArray datasets2 = new JsonArray();
            JsonObject dataset2 = new JsonObject();
            dataset2.addProperty(inputcustem1.getHint().toString(), edit14.getText().toString());
            if (inputcustem2.getHint() != null) {
                dataset2.addProperty(inputcustem2.getHint().toString(), edit15.getText().toString());
            }
            if (inputcustem3.getHint() != null) {
                dataset2.addProperty(inputcustem3.getHint().toString(), edit16.getText().toString());
            }
            if (inputcustem4.getHint() != null) {
                dataset2.addProperty(inputcustem4.getHint().toString(), edit17.getText().toString());
            }
            if (inputcustem5.getHint() != null) {
                dataset2.addProperty(inputcustem5.getHint().toString(), edit18.getText().toString());
            }
            update.add("CustomFields", dataset2);
        }
        RestRequest.put(FacebookNativeActivity.this, Endpoint.getUpdateProfileUrl(),params,new Gson().toJson(update),new AsyncHandler<String>() {
            @Override
            public void onSuccess(String response) {
                RegisterResponse registerResponse = new Gson().fromJson(response,RegisterResponse.class);
                if(registerResponse.getIsPosted()){
                    hideProgressDialog();
                    if (isMobile!=null && isMobile.equals("true")) {
                        Intent intent = new Intent(getApplication(), OtpVerificationActivity.class);
                        intent.putExtra("phonenumber",value.getPhone());
                        startActivityForResult(intent,2);
                    }else if(inputemailid.getVisibility()==View.VISIBLE){
                        Toast.makeText(FacebookNativeActivity.this, customizeLanguageResponse.successMessage.get(0).getRegistermessage(), Toast.LENGTH_LONG).show();

                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("accesstoken", getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("webtoken", ""));
                        intent.putExtra("provider", getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("webprovider", ""));
                        setResult(2, intent);
                        finish();//finishing activity
                    }}
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(FacebookNativeActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FacebookNativeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
            inputphonenumber.setError("Please Enter Valid Mobile Number");
            requestFocus(edit4);
            return false;
        } else {
            inputphonenumber.setErrorEnabled(false);
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });}

    public void sendAccessToken(String accessToken){
        hideProgressDialog();
        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "facebook";
        Intent intent=new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("provider", "facebook");
        setResult(2,intent);
        finish();//finishing activity
    }




}


