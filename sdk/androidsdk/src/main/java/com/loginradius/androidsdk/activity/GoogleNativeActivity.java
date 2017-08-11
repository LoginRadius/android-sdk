package com.loginradius.androidsdk.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.api.OtpVerificationAPI;
import com.loginradius.androidsdk.api.ResendotpAPI;
import com.loginradius.androidsdk.api.TraditionalInterfaceAPI;
import com.loginradius.androidsdk.api.UpdateProfileAPI;
import com.loginradius.androidsdk.api.UserProfileAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.ui.FieldViewUtil;
import com.loginradius.androidsdk.helper.ProviderPermissions;
import com.loginradius.androidsdk.ui.RequiredFieldsViewGenerator;
import com.loginradius.androidsdk.helper.lrLoginManager;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GoogleNativeActivity extends AppCompatActivity {

    CallbackManager callManager;
    ProgressBar bar;
    private List<Provider> providers;
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    private lrAccessToken accessToken;
    private boolean isRequired,promptPassword;
    private String verificationurl;
    private int fieldsColor;
    RequiredFieldsViewGenerator gtr;
    FieldViewUtil fieldUtil;
    Context context;
    List<UserRegisteration> raasSchemaList;
    String apikey;
    LoginRadiusUltimateUserProfile userProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_native);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        lrLoginManager.nativeLogin = true;

        Intent i = getIntent();
        isRequired = i.getBooleanExtra("isRequired",false);
        promptPassword = i.getBooleanExtra("promptPassword",false);
        verificationurl = i.getStringExtra("verificationurl");
        fieldsColor = i.getIntExtra("fieldsColor",0);
        context = GoogleNativeActivity.this;

        apikey = getIntent().getExtras().getString("apikey");

        ProviderPermissions.resetPermissions();
        lrLoginManager.getNativeAppConfiguration(apikey, callManager,
                new AsyncHandler<SocialInterface>() {
                    @Override
                    public void onSuccess(SocialInterface socialInterface) {
                        providers = socialInterface.getProviders();
                        for (Provider provider : providers) {
                            if (provider.getName().equalsIgnoreCase("google")) {
                                showdialog(provider);
                                break;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {
                    }
                });


    }


    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();

        unregisterReceiver(receiver);

    }


    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);

        }


        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.v(LOG_TAG, "Now you are connected to Internet!");
                                //  Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                isConnected = true;
                                //do your processing here ---
                                //if you need to post any data to the server or get status
                                //update from the server
                            }
                            return true;
                        }
                    }
                }
            }
            Log.v(LOG_TAG, "You are not connected to Internet!");
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            isConnected = false;
            return false;
        }
    }


    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(GoogleNativeActivity.this, p, new AsyncHandler<lrAccessToken>() {

            @Override
            public void onSuccess(lrAccessToken data) {
                accessToken = data;
                accessToken.apikey = apikey;
                accessToken.provider = "google";
                if(isRequired){
                    getRaasSchema();
                }else {
                    sendAccessToken(data.access_token);
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.toString().equals("lr_LOGIN_CANCELLED")){
                    finish();
                }
            }
        });

    }

    private void getRaasSchema() {
        gtr = new RequiredFieldsViewGenerator(context, fieldsColor);
        setContentView(gtr.generateProgressBar());
        LoginParams params = new LoginParams();
        params.apikey = apikey;
        TraditionalInterfaceAPI api = new TraditionalInterfaceAPI();
        api.getResponse(params, new AsyncHandler<List<UserRegisteration>>() {
            @Override
            public void onSuccess(List<UserRegisteration> data) {
                boolean containsRequired = false;
                raasSchemaList = data;
                for(int i=0;i<raasSchemaList.size();i++){
                    if(raasSchemaList.get(i).getRules()!=null && raasSchemaList.get(i).getRules().contains("required") && !containsRequired){
                        containsRequired = true;
                    }
                }
                if(containsRequired){
                    getUserProfile();
                }else{
                    sendAccessToken(accessToken.access_token);
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("lr_api_error",error.getMessage());
                sendAccessToken(null);
            }
        });
    }

    private void getUserProfile() {
        UserProfileAPI api = new UserProfileAPI();
        api.getResponse(accessToken, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
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
                    sendAccessToken(accessToken.access_token);
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
            UserRegisteration userField=raasSchemaList.get(i);

            if(userField.getRules()!=null){
                if(userField.getRules().contains("required") && !isRequiredAdded){
                    isRequiredAdded = fieldUtil.addFieldView(gtr,userProfile,userField,linearContainer,customFields,promptPassword);
                }else{
                    fieldUtil.addFieldView(gtr,userProfile,userField,linearContainer,customFields,promptPassword);
                }
            }
        }

        if(isRequiredAdded){
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
            UpdateProfileAPI api = new UpdateProfileAPI();
            LoginParams params = new LoginParams();
            params.apikey = apikey;
            params.verificationUrl= verificationurl;
            JsonObject jsonData = fieldUtil.getData(gtr,linearContainer);
            api.getResponse(params, accessToken, jsonData, new AsyncHandler<RegisterResponse>() {
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
                    sendAccessToken(accessToken.access_token);
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

    private void showEmailInfoDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

    private void showMobileInfoDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
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
        LoginParams params = new LoginParams();
        params.apikey = apikey;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", fieldUtil.getPhone());
        ResendotpAPI api = new ResendotpAPI();
        api.getResponse(params, jsonObject, new AsyncHandler<RegisterResponse>() {
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
        LoginParams params = new LoginParams();
        params.apikey = apikey;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone", fieldUtil.getPhone());
        params.otp = otp;
        OtpVerificationAPI api = new OtpVerificationAPI();
        api.getResponse(params, jsonObject, new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                sendAccessToken(accessToken.access_token);
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

    public void sendAccessToken(String accessToken) {

        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "google";
        Intent intent = new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("provider", "Google");
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


