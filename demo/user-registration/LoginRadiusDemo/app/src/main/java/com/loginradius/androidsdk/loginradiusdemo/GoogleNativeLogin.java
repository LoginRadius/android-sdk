package com.loginradius.androidsdk.loginradiusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.ProviderPermissions;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;


public class GoogleNativeLogin extends ActionBarActivity {
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
private String apiKey;
    private String siteName;
    CallbackManager callManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_native_login);
        getSupportActionBar().hide();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        lrLoginManager.nativeLogin = true;
         apiKey= getString(R.string.loginradius_api_key);
        siteName= getString(R.string.loginradius_site_name);
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
                            if(!isConnected){
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
            Intent intent = new Intent(getApplication(),WebviewActivity.class);
            intent.putExtra("action", "LOGIN");
            intent.putExtra("keyName", apiKey);
            intent.putExtra("SiteName", siteName);
            startActivity(intent);
            isConnected = false;
            return false;
        }
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
