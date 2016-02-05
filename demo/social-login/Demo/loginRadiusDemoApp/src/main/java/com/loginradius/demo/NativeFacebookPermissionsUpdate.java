package com.loginradius.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.FB_Permission;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;


public class NativeFacebookPermissionsUpdate extends Activity {
    CallbackManager callManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_facebook_permissions_update);

        FacebookSdk.sdkInitialize(getApplicationContext());
        lrLoginManager.nativeLogin = true;
        callManager=CallbackManager.Factory.create();;
        ProviderPermissions.resetPermissions();
        ProviderPermissions.addFbPermission(FB_Permission.PUBLISH_ACTIONS);
        String apiKey= getString(R.string.LoginRadius_API_Key);
        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {
                        int i = 0;
                        for (Provider p : appInfo.Providers) {
                            if (p.Name.equalsIgnoreCase("facebook")) {
                                showdialog(p);
                                break;
                            } else i++;
                        }
                        if (i == appInfo.Providers.size())
                            Toast.makeText(getApplicationContext(), "Facebook provider not supported", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {
                    }
                });

    }

    public void showdialog(final Provider p) {
        Button btn = (Button) findViewById(R.id.ButtonUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lrLoginManager.performLogin(NativeFacebookPermissionsUpdate.this, p, new AsyncHandler<lrAccessToken>() {

                    @Override
                    public void onSuccess(lrAccessToken data) {
                        Intent i = new Intent(NativeFacebookPermissionsUpdate.this, StatusUpdate.class);
                        StatusUpdate.token=data;
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable error, String errorcode) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_native_facebook_permissions_update, menu);
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
        callManager.onActivityResult(requestCode, resultCode, data);

    }
}
