package com.loginradius.androidsdk.embeddedsociallogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        lrLoginManager.getAppConfiguration("<api_key>",
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {
                        setupView(appInfo);
                    }

                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {
                    }
                });
    }


    private void setupView(final AppInformation appInfo) {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /** Perform login through Yahoo **/
                lrLoginManager.performLogin(MainActivity.this,
                        Provider.findByName("facebook", appInfo.Providers),
                        new AsyncHandler<lrAccessToken>() {
                            @Override
                            public void onSuccess(lrAccessToken token) {
                                Intent intent = new Intent(getApplication(), UserProfileActivity.class);
                                intent.putExtra("accesstoken", token.access_token);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Throwable error, String errorcode) {
                            }
                        });

            }
        });
        btnLogin.setEnabled(true);

    }





}