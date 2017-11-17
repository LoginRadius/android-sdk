package com.loginradius.androidsdk.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.api.ConfigurationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.URLHelper;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.config.ConfigResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegistration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.ui.FieldViewUtil;
import com.loginradius.androidsdk.ui.RequiredFieldsViewGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    String provider, spinview, encodedURL;
    int fieldsColor;
    boolean isRequired,promptPassword,reloadOnError,askOptionalOnSocialLogin,customScopeEnabled;
    List<UserRegistration> raasSchemaList;
    AccessTokenResponse accessToken;
    RequiredFieldsViewGenerator gtr;
    FieldViewUtil fieldUtil;
    Context context;
    LoginRadiusUltimateUserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if(!LoginRadiusSDK.validate()){
            throw new LoginRadiusSDK.InitializeException();
        }

        spinview = "false";
        webView = (WebView) findViewById(R.id.webView1);
        context = webView.getContext();
        Intent i = getIntent();
        provider = i.getStringExtra("provider");
        isRequired = i.getBooleanExtra("isRequired",true);
        fieldsColor = i.getIntExtra("fieldsColor",0);
        reloadOnError = i.getBooleanExtra("reloadOnError",false);
        customScopeEnabled = i.getBooleanExtra("customScopeEnabled",false);
        String sitebuilder = "https://" + LoginRadiusSDK.getSiteName();
        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("provider", provider.toLowerCase());
        params.put("is_access_token", "true");
        params.put("ismobile", "true");
        params.put("is_custom_scope",String.valueOf(customScopeEnabled));
        encodedURL = sitebuilder + URLHelper.URLBuilder(Endpoint.webviewlogin, params);
        if (i != null) {
            startWebView(encodedURL);
        }

    }

    private void startWebView(String url) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("?token")) {
                    Uri uri = Uri.parse(url);
                    String Token = uri.getQueryParameter("token");
                    if (Token != null) {
                        String[] temp = Token.split("#");
                        Token = temp[0];
                        accessToken = new AccessTokenResponse();
                        accessToken.access_token = Token;
                        accessToken.provider = provider;
                        accessToken.apikey = LoginRadiusSDK.getApiKey();
                        if(isRequired){
                            getRaasSchema();
                        }else{
                            sendAccessToken(accessToken);
                        }
                    }
                } else if (url.contains("error=access_denied"))
                {
                    finish();
                } else if(url.contains("/socialauth/validate.sauth?denied")){
                    finish();
                }
                else if(url.contains("error=server_error") && provider.equals("linkedin")){
                    if(reloadOnError){
                        Toast.makeText(context,"Your email contains a white space character, please try again with a proper email",Toast.LENGTH_SHORT).show();
                        startWebView(encodedURL);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("error",getLrError().toString());
                        setResult(2,intent);
                        finish();
                    }
                }else
                {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.startsWith("https://api.twitter.com/oauth") && !url.contains("oauth_token")){
                    finish();
                }else{
                    super.onPageFinished(view, url);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private void getRaasSchema() {
        gtr = new RequiredFieldsViewGenerator(WebViewActivity.this, fieldsColor);
        setContentView(gtr.generateProgressBar());
        ConfigurationAPI api = new ConfigurationAPI();
        api.getResponse(new AsyncHandler<ConfigResponse>() {
            @Override
            public void onSuccess(ConfigResponse data) {
                raasSchemaList = data.getRegistrationFormSchema();
                promptPassword = data.getAskPasswordOnSocialLogin();
                askOptionalOnSocialLogin = data.getAskOptionalFieldsOnSocialSignup();
                validateRaasSchema();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("lr_api_error",error.getMessage());
                sendAccessToken(null);
            }
        });
    }

    private void validateRaasSchema() {
        boolean containsRequired = false;
        for(int i=0;i<raasSchemaList.size();i++){
            if(raasSchemaList.get(i).getRules().contains("required") && !containsRequired){
                containsRequired = true;
            }
        }
        if(containsRequired){
            getUserProfile();
        }else{
            sendAccessToken(accessToken);
        }
    }

    private void getUserProfile() {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(accessToken.access_token);
        api.readAllUserProfile(queryParams, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile data) {
                userProfile = data;
                setRequiredFieldsView();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("lr_api_error",error.getMessage());
                sendAccessToken(null);
            }
        });
    }

    private void setRequiredFieldsView() {
        Observable<ScrollView> observable = Observable.create(new ObservableOnSubscribe<ScrollView>() {
            @Override
            public void subscribe(ObservableEmitter<ScrollView> e) throws Exception {
                e.onNext(getFieldsView());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ScrollView> observer = new Observer<ScrollView>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ScrollView value) {
                if(value.getChildCount()>0){
                    setContentView(value);
                }else{
                    sendAccessToken(accessToken);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("lr_api_error",e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    private ScrollView getFieldsView(){
        fieldUtil = new FieldViewUtil();
        boolean isRequiredAdded = false;
        ScrollView svParent = gtr.generateParentView();
        final LinearLayout linearContainer = gtr.generateParentContainerView();
        svParent.addView(linearContainer);
        LinkedTreeMap customFields = null;
        if(userProfile.getCustomFields()!=null){
            customFields = (LinkedTreeMap) userProfile.getCustomFields();
        }
        TextView tvLabel = gtr.generateLabelTextView("Please fill the required fields to continue");
        tvLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        linearContainer.addView(tvLabel);
        for(int i = 0;i<raasSchemaList.size();i++){
            UserRegistration userField=raasSchemaList.get(i);

            if(userField.getRules()!=null){
                if(userField.getRules().contains("required") && !isRequiredAdded){
                    isRequiredAdded = fieldUtil.addFieldView(gtr,userProfile,userField,linearContainer,customFields,promptPassword);
                }else{
                    fieldUtil.addFieldView(gtr,userProfile,userField,linearContainer,customFields,promptPassword);
                }
            }
        }

        if(isRequiredAdded || (askOptionalOnSocialLogin && userProfile.getNoOfLogins() == 1)){
            Button submitButton = gtr.generateSubmitButton("Register");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable observable = Observable.create(new ObservableOnSubscribe() {
                        @Override
                        public void subscribe(ObservableEmitter e) throws Exception {
                            startUpdateProcess(linearContainer);
                        }
                    });
                    observable.subscribe();
                }
            });
            linearContainer.addView(submitButton);
            return svParent;
        }
        return gtr.generateParentView();
    }

    private void startUpdateProcess(LinearLayout linearContainer) {
        Log.i("AccessToken",accessToken.access_token);
        if(fieldUtil.validateFields(gtr,linearContainer)){
            setContentView(gtr.generateProgressBar());
            AuthenticationAPI api = new AuthenticationAPI();
            QueryParams queryParams = new QueryParams();
            queryParams.setAccess_token(accessToken.access_token);
            JsonObject jsonData = fieldUtil.getData(gtr,linearContainer);
            api.updateProfile(queryParams, jsonData, new AsyncHandler<RegisterResponse>() {
                @Override
                public void onSuccess(RegisterResponse data) {
                    if(fieldUtil.getPhone()!=null){
                        showMobileInfoDialog();
                        return;
                    }
                    if(fieldUtil.getEmail()!=null){
                        showEmailInfoDialog();
                        return;
                    }
                    sendAccessToken(accessToken);
                }

                @Override
                public void onFailure(Throwable error, String errorcode) {
                    ErrorResponse errorResponse = JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                    int errorCode = errorResponse.getErrorCode();
                    Log.i("ErrorCode",String.valueOf(errorCode));
                    switch (errorCode){
                        case 936:
                            Toast.makeText(context,"Email already exists",Toast.LENGTH_SHORT).show();
                            setRequiredFieldsView();
                            break;
                        case 1058:
                            Toast.makeText(context,"Mobile number already exists",Toast.LENGTH_SHORT).show();
                            setRequiredFieldsView();
                            break;
                        case 970:
                            showEmailInfoDialog();
                            break;
                        case 1066:
                            showMobileInfoDialog();
                            break;
                        default:
                            Toast.makeText(context,"Unable to complete the request at the moment",Toast.LENGTH_SHORT).show();
                            setRequiredFieldsView();
                            break;
                    }
                }
            });
        }
    }

    private void showMobileInfoDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(WebViewActivity.this);
        alert.setTitle("Message");
        alert.setMessage("We've sent an OTP on your mobile number. Please verify your mobile number to login.");
        alert.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setOTPView();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setOTPView(){
        View view = gtr.generateOTPLayout();
        Button btnSubmit = (Button)view.findViewWithTag("submit");
        Button btnResend = (Button)view.findViewWithTag("resend_otp");
        final EditText etOTP = (EditText)view.findViewWithTag("otp");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOTP.getText().toString().trim();
                if(otp.length() == 0){
                    etOTP.setError("Required");
                    etOTP.setBackgroundResource(R.drawable.red_border);
                }else{
                    submitOTP(otp);
                }
            }
        });
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
            }
        });
        setContentView(view);
    }

    private void resendOTP() {
        setContentView(gtr.generateProgressBar());
        AuthenticationAPI api = new AuthenticationAPI();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", fieldUtil.getPhone());
        api.resendOtp(null, jsonObject, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse data) {
                Toast.makeText(context,"OTP sent to your mobile number",Toast.LENGTH_SHORT).show();
                setOTPView();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                ErrorResponse errorResponse = JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                int errorCode = errorResponse.getErrorCode();
                Log.i("ErrorCode",String.valueOf(errorCode));
                Toast.makeText(context,"Unable to complete the request at the moment",Toast.LENGTH_SHORT).show();
                setOTPView();
            }
        });
    }

    private void submitOTP(String otp) {
        setContentView(gtr.generateProgressBar());
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setOtp(otp);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", fieldUtil.getPhone());
        api.verifyOtp(queryParams, jsonObject, new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                AccessTokenResponse accessToken = new AccessTokenResponse();
                accessToken.setAccess_token(data.getAccessToken());
                accessToken.setProvider(provider);
                sendAccessToken(accessToken);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                ErrorResponse errorResponse = JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                int errorCode = errorResponse.getErrorCode();
                Log.i("ErrorCode",String.valueOf(errorCode));
                if(errorCode == 1067){
                    Toast.makeText(context,"OTP is not correct. Please try again",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Unable to complete the request at the moment",Toast.LENGTH_SHORT).show();
                }
                setOTPView();
            }
        });
    }

    private void showEmailInfoDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(WebViewActivity.this);
        alert.setTitle("Message");
        alert.setMessage("We've sent a verification mail on your email address. Please verify your email address to login.");
        alert.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendAccessToken(null);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        // Let the system handle the back button
        super.onBackPressed();

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

    private JsonObject getLrError(){
        JsonObject object = new JsonObject();
        object.addProperty("description","The error occurred at social provider's end. It may occur when the credentials contain white space");
        object.addProperty("errorCode","1000");
        object.addProperty("message","An error has occurred at the social identity provider’s end");
        object.addProperty("isProviderError",true);
        object.addProperty("providerErrorResponse","linkedin is experiencing an unexpected error at this moment. Please try again in a few minutes");
        return object;
    }

    public void sendAccessToken(AccessTokenResponse accessToken) {
        Intent intent = new Intent();
        if(accessToken!=null){
            intent.putExtra("accesstoken", accessToken.access_token);
            intent.putExtra("provider", accessToken.provider);
        }
        setResult(2, intent);
        finish();//finishing activity

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

