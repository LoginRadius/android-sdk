package com.loginradius.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loginradius.androidsdk.helper.BiometricManagerClass;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.helper.LoginRadiusSDK.Initialize;
import com.loginradius.androidsdk.resource.BiometricPromptBuilder;


public class MainActivity extends AppCompatActivity {
    private Button btnlogin,btnregister,btnforgotpassword,btnbiometric;
    Button.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginRadiusSDK.Initialize init = new Initialize();
        init.setApiKey(getString(R.string.api_key));
        init.setSiteName(getString(R.string.site_name));
        init.setVerificationUrl(getString(R.string.verification_url));
        init.setResetPasswordUrl(getString(R.string.reset_password_url));


        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.login_bt:
                      intent = new Intent(getApplication(), LoginActivity.class);
                       break;
                    case R.id.signup_bt:
                        intent = new Intent(getApplication(), RegisterActivity.class);
                        // do stuff;
                        break;
                    case R.id.forgot_bt:
                        intent = new Intent(getApplication(), ForgotPasswordActivity.class);
                        break;

                    case R.id.face_finger:
                        BiometricManagerClass obj = new BiometricManagerClass ( MainActivity.this );
                        //Check if Biometric is supported by device.
                        obj.isBiometricSupported ( new BiometricManagerClass.biometricSupported ( ) {

                            @Override
                            public void onBiometricDeviceSuccess() {
                                //if Biometric supported display Biometric prompt.
                                BiometricPromptBuilder biometricPromptBuilder=new BiometricPromptBuilder();
                                biometricPromptBuilder.setTitle("LoginRadius Biometric Login");
                                biometricPromptBuilder.setSubtitle ( "Log in using Biometric Credential" );
                                biometricPromptBuilder.setNegativeButtonName ( "Cancel" );
                                obj.showBiometricPrompt ( biometricPromptBuilder, new BiometricManagerClass.BiometricCallback ( ) {
                                    @Override
                                    public void onBiometricAuthenticationSuccess (  ) {
                                        // Handle authentication success.
                                        Toast.makeText ( MainActivity.this, "Authentication Succeeded!", Toast.LENGTH_SHORT ).show ( );
                                    }


                                    @Override
                                    public void onBiometricAuthenticationFailure () {
                                        // Handle authentication failure.
                                        Toast.makeText ( MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT ).show ();
                                    }

                                    @Override
                                    public void onBiometricAuthenticationError ( ErrorResponse error ) {
                                        // Handle authentication error.
                                        Toast.makeText(MainActivity.this, "Error:"+error.getMessage (), Toast.LENGTH_SHORT).show();
                                    }
                                } );
                            }

                            @Override
                            public void onBiometricDeviceError ( ErrorResponse error) {
                                // Handle cases when Biometric is not available or enrolled in the device(e.g. open setting to enroll biometric or display message.)
                                Toast.makeText(MainActivity.this, "Error:"+error.getMessage (), Toast.LENGTH_SHORT).show();
                            }

                        } );

                    default:
                        return;
                }


                startActivityForResult(intent, 2);
            }
        };
        initWidget();

    }
   private void initWidget() {
        //initialize button
        btnlogin = (Button) findViewById(R.id.login_bt);
        btnregister = (Button) findViewById(R.id.signup_bt);
        btnforgotpassword = (Button) findViewById(R.id.forgot_bt);
        btnbiometric = (Button) findViewById ( R.id.face_finger );

        //set on Click listener
        btnlogin.setOnClickListener(listener);
        btnregister.setOnClickListener(listener);
        btnforgotpassword.setOnClickListener(listener);
       btnbiometric.setOnClickListener ( listener );

}


}