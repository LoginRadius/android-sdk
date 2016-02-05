package com.loginradius.registrationsdk;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.FB_Permission;
import com.loginradius.sdk.ui.LRinterfaceimpl;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.models.Provider;

public class nativeLoginActivity extends Activity {
    CallbackManager callManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        lrLoginManager.nativeLogin = true;
        callManager= CallbackManager.Factory.create();
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);

        String apiKey= getString(R.string.loginradius_api_key);

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
        getMenuInflater().inflate(R.menu.menu_native_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendAccessToken(String accessToken){
        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "facebook";
        Intent intent = new Intent(getApplication(),UserProfileActivity.class);
        intent.putExtra("accesstoken",accesstoken.access_token);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callManager.onActivityResult(requestCode, resultCode, data);

    }
    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(nativeLoginActivity.this, p, new AsyncHandler<lrAccessToken>() {

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
