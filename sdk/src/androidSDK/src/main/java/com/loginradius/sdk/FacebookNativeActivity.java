package com.loginradius.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String ApplicationActivityId ;
    private String lrapikey;
    private String lrsitename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_native);
        FacebookSdk.sdkInitialize(getApplicationContext());
        lrLoginManager.nativeLogin = true;
        Log.d("hello", "govind");
        callManager= CallbackManager.Factory.create();
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);
        ProviderPermissions.addFbPermission(FB_Permission.USER_EMAIL);
        LoginManager.getInstance().logOut();
        final String dataapi = getIntent().getExtras().getString("keyName");
        String apiKey= dataapi;
        // String apiKey= getString(R.string.loginradius_api_key);

        ApplicationActivityId= getIntent().getExtras().getString("ApplicationActivityId");
        lrapikey = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("api", "");
        lrsitename = getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("sitename", "");




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



    /* public void populateListView(final AppInformation appinfo) {
         LRinterfaceimpl li2= new LRinterfaceimpl();
         lrLoginManager.ImageUrl = null;
         lrLoginManager.ImgVersion="v1";
         ListView listview = (ListView) findViewById(R.id.listView2);
         li2.attachListView(appinfo, listview, this,
                 new AsyncHandler<lrAccessToken>() {
                     @Override
                     public void onSuccess(lrAccessToken token) {
                         sendAccessToken(token.access_token);

                     }

                     @Override
                     public void onFailure(Throwable error, String errorCode) {
                         Log.d("Error", "Error");
                     }
                 });
     }*/
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
        intent.putExtra("ApplicationActivityIdfb",ApplicationActivityId);
        startActivity(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callManager.onActivityResult(requestCode, resultCode, data);

    }
    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(FacebookNativeActivity.this, p, new AsyncHandler<lrAccessToken>() {

            @Override
            public void onSuccess(lrAccessToken data) {
                sendAccessToken(data.access_token);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {

            }
        });
    }
}
