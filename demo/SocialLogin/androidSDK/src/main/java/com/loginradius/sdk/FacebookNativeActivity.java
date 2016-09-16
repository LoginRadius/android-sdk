package com.loginradius.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.FB_Permission;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.userregistration.resource.StrResource;

public class FacebookNativeActivity extends Activity {
    CallbackManager callManager;
    private String lrapikey,lrsitename;
    private String facebooknativelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_native);
        FacebookSdk.sdkInitialize(getApplicationContext());
        lrLoginManager.nativeLogin = true;
        callManager= CallbackManager.Factory.create();
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);
        ProviderPermissions.addFbPermission(FB_Permission.USER_EMAIL);
        LoginManager.getInstance().logOut();
        final String dataapi = getIntent().getExtras().getString("keyName");
        final String nativelogin = getIntent().getExtras().getString("nativelogin");
        String apiKey= dataapi;
        lrapikey = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("api", "");
        lrsitename = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("sitename", "");

       if (nativelogin!=null && nativelogin.equals("true")){
           facebooknativelogin=nativelogin;
       }else {
           facebooknativelogin="false";

       }

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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.loginradius.sdk.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.loginradius.sdk.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendAccessToken(String accessToken){
        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "facebook";
        Intent intent = new Intent(getApplication(),NativeWeb.class);
        intent.putExtra("action", "FB");
        intent.putExtra("keyName", lrapikey);
        intent.putExtra("SiteName", lrsitename);
        intent.putExtra("accesstoken", accesstoken.access_token);
        startActivityForResult(intent, 2);

    }



    public void sendAccessTokenFinal(String accessToken) {
        Intent intent=new Intent();
        intent.putExtra("accesstoken", accessToken);
        setResult(2,intent);
        finish();//finishing activity

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
         if(data!=null){
            String accessToken=data.getStringExtra("accesstoken");
           sendAccessTokenFinal(accessToken);
        }
        }else {

            callManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(FacebookNativeActivity.this, p, new AsyncHandler<lrAccessToken>() {

            @Override
            public void onSuccess(lrAccessToken data) {

                if (facebooknativelogin!=null&&facebooknativelogin.equals("true")){
                    sendAccessTokenFinal(data.access_token);
                }else {
                    sendAccessToken(data.access_token);
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {

            }
        });
    }
}
