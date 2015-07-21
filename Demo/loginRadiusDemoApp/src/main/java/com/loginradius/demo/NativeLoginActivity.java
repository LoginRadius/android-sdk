package com.loginradius.demo;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.FB_Permission;
import com.loginradius.sdk.ui.LRinterfaceimpl;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;

public class NativeLoginActivity extends Activity {

    CallbackManager callManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        lrLoginManager.nativeLogin = true;
        callManager=CallbackManager.Factory.create();
        String apiKey= getString(R.string.LoginRadius_API_Key);
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.USER_BASIC_INFO);
        ProviderPermissions.addFbPermission(FB_Permission.USER_GROUPS);
        ProviderPermissions.addFbPermission(FB_Permission.USER_BIRTHDAY);


        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {

                        populateListView(appInfo);
                    }

                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {

                    }
                });
    }

    public void populateListView(final AppInformation appinfo) {
        LRinterfaceimpl li2= new LRinterfaceimpl();
        lrLoginManager.ImageUrl = null;
        lrLoginManager.ImgVersion="v1";
        ListView listview = (ListView) findViewById(R.id.listView);
        li2.attachListView(appinfo, listview, this,
                new AsyncHandler<lrAccessToken>() {
                    @Override
                    public void onSuccess(lrAccessToken token) {

                        UserProfileActivity.token = token;
                        Intent i = new Intent(NativeLoginActivity.this, UserProfileActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable error, String errorCode) {
                    }
                });
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callManager.onActivityResult( requestCode, resultCode, data);

    }
}
