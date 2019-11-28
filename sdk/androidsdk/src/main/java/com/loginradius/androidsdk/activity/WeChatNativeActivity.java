package com.loginradius.androidsdk.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.helper.LoginRadiusAuthManager;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.UpdateProfileResponse;
import com.loginradius.androidsdk.response.config.ConfigResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegistration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.ui.FieldViewUtil;
import com.loginradius.androidsdk.ui.RequiredFieldsViewGenerator;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatNativeActivity extends Activity implements IWXAPIEventHandler {
    /**
     * Wechat login related
     */
    public static String wxAppId;
    public static String wxError;
    private IWXAPI api;
    public static AccessTokenResponse wxResponse;
    public static boolean isRequired =false;
    private boolean promptPassword,askOptionalOnSocialLogin;
    public static int fieldsColor = 0;
    RequiredFieldsViewGenerator gtr;
    FieldViewUtil fieldUtil;
    Context context;
    List<UserRegistration> raasSchemaList;
    LoginRadiusUltimateUserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //obtaining IWXApI through the WXAPIFactory factory
        api = WXAPIFactory.createWXAPI(this, wxAppId, true);
        context = WeChatNativeActivity.this;
        //Register the appid of the application to Wechat
        api.registerApp(wxAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e("yc", "onReq:" + baseReq.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                try {
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    exchangeToken(sendResp.code);
                } catch (Exception e) {
                    sendAccessToken(null,null,null,e.getMessage());
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                sendAccessToken(null,null,null,"Wx User canceled the request");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                sendAccessToken(null,null,null,"Wx User denied the request");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }

    public void exchangeToken(String code){
        LoginRadiusAuthManager.getResponseWeChat(code, new AsyncHandler<AccessTokenResponse>() {
            @Override
            public void onSuccess(AccessTokenResponse data) {
                wxResponse = data;
                wxResponse.provider = "wechat";
                if(isRequired){
                    getRaasSchema();
                }else{
                    sendAccessToken(data.access_token,data.refresh_token,data.expires_in,null);
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                sendAccessToken(null,null,null,"lr_api_error");
            }
        });
    }

    private void getRaasSchema() {
        gtr = new RequiredFieldsViewGenerator(context, fieldsColor);
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
                sendAccessToken(null,null,null,"lr_api_error");
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
            sendAccessToken(wxResponse.access_token,wxResponse.refresh_token,wxResponse.expires_in,null);
        }
    }


    private void getUserProfile() {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(wxResponse.access_token);
        api.readAllUserProfile(queryParams, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile data) {
                userProfile = data;
                setRequiredFieldsView();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("lr_api_error",error.getMessage());
                sendAccessToken(null,null,null,"lr_api_error");
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
                    sendAccessToken(wxResponse.access_token,wxResponse.refresh_token,wxResponse.expires_in,null);
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
        Log.i("AccessToken",wxResponse.access_token);
        if(fieldUtil.validateFields(gtr,linearContainer)){
            setContentView(gtr.generateProgressBar());
            AuthenticationAPI api = new AuthenticationAPI();
            QueryParams queryParams = new QueryParams();
            queryParams.setAccess_token(wxResponse.access_token);
            JsonObject jsonData = fieldUtil.getData(gtr,linearContainer);
            api.updateProfile(queryParams, jsonData, new AsyncHandler<UpdateProfileResponse>() {
                @Override
                public void onSuccess(UpdateProfileResponse data) {
                    if(fieldUtil.getPhone()!=null){
                        showMobileInfoDialog();
                        return;
                    }
                    if(fieldUtil.getEmail()!=null){
                        showEmailInfoDialog();
                        return;
                    }
                    sendAccessToken(wxResponse.access_token,wxResponse.refresh_token,wxResponse.expires_in,null);
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
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendAccessToken(null,null,null,"We've sent a verification mail on your email address. Please verify your email address to login.");
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
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        api.resendOtp(null,jsonObject, new AsyncHandler<RegisterResponse>() {
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
                sendAccessToken(data.getAccessToken(),data.getRefreshToken(),data.getExpiresIn(),null);
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

    public void sendAccessToken(String accessToken,String refreshToken,String expiresIn,String error) {
        wxResponse.setAccess_token(accessToken);
        wxResponse.setRefresh_token(refreshToken);
        wxResponse.setProvider("wechat");
        wxResponse.setExpires_in(expiresIn);
        wxError = error;
        finish();//finishing activity
    }
}
