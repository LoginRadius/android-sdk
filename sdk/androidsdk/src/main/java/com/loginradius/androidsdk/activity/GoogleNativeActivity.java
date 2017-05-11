package com.loginradius.androidsdk.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.helper.ProviderPermissions;
import com.loginradius.androidsdk.helper.lrLoginManager;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;


import java.util.List;


public class GoogleNativeActivity extends AppCompatActivity {

    CallbackManager callManager;
    ProgressBar bar;
    private List<Provider> providers;
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;


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
        final String dataapi = getIntent().getExtras().getString("apikey");
        String apiKey = dataapi;
        ProviderPermissions.resetPermissions();
        lrLoginManager.getNativeAppConfiguration(apiKey, callManager,
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
                sendAccessToken(data.access_token);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {

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


