package com.loginradius.androidsdk.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.loginradius.androidsdk.R;


import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.helper.FB_Permission;
import com.loginradius.androidsdk.helper.ProviderPermissions;
import com.loginradius.androidsdk.helper.lrLoginManager;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;

import java.util.List;


public class FacebookNativeActivity extends AppCompatActivity {
    CallbackManager callManager;
    private List<Provider> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_native);
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        lrLoginManager.nativeLogin = true;
        callManager = CallbackManager.Factory.create();
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);
        ProviderPermissions.addFbPermission(FB_Permission.USER_EMAIL);
        ProviderPermissions.addFbPermission(FB_Permission.PUBLISH_ACTIONS);
        final String dataapi = getIntent().getExtras().getString("apikey");
        String apiKey = dataapi;
        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
                new AsyncHandler<SocialInterface>() {
                    @Override
                    public void onSuccess(SocialInterface socialInterface) {

                        providers = socialInterface.getProviders();
                        for (Provider provider : providers) {
                            if (provider.getName().equalsIgnoreCase("facebook")) {
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


    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(FacebookNativeActivity.this, p, new AsyncHandler<lrAccessToken>() {

            @Override
            public void onSuccess(lrAccessToken data) {
                sendAccessToken(data.access_token);

            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.toString().equals("lr_LOGIN_CANCELLED")){
                    finish();
                }
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


    public void sendAccessToken(String accessToken) {
        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "facebook";
        Intent intent = new Intent();
        intent.putExtra("accesstoken", accessToken);
        intent.putExtra("provider", "facebook");
        setResult(2, intent);
        finish();//finishing activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
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
        } else {
            callManager.onActivityResult(requestCode, resultCode, data);
        }

    }



}


