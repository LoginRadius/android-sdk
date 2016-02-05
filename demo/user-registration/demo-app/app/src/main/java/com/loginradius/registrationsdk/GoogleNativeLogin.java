package com.loginradius.registrationsdk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;


public class GoogleNativeLogin extends ActionBarActivity {
    CallbackManager callManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_native_login);
        lrLoginManager.nativeLogin = true;
        String apiKey= getString(R.string.loginradius_api_key);
        ProviderPermissions.resetPermissions();
        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {
                        for (Provider p : appInfo.Providers) {
                            if (p.Name.equalsIgnoreCase("google")) {
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
        getMenuInflater().inflate(R.menu.menu_google_native_login, menu);
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
    public void showdialog(final Provider p) {
        lrLoginManager.performLogin(GoogleNativeLogin.this, p, new AsyncHandler<lrAccessToken>() {

            @Override
            public void onSuccess(lrAccessToken data) {
                sendAccessToken(data.access_token);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {

            }
        });
    }
    public void sendAccessToken(String accessToken){
        lrAccessToken accesstoken = new lrAccessToken();
        accesstoken.access_token = accessToken;
        accesstoken.provider = "facebook";
        Intent intent = new Intent(getApplication(),UserProfileActivity.class);
        intent.putExtra("accesstoken",accesstoken.access_token);
        startActivity(intent);
    }
}
