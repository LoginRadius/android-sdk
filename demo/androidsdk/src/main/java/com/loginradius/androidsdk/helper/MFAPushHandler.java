package com.loginradius.androidsdk.helper;

import static android.content.ContentValues.TAG;


import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.MFAPushAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.DeviceRegisterResponse;

public class MFAPushHandler extends AppCompatActivity implements FcmUtils.FcmTokenListener {
    public Context context;


    public MFAPushHandler(Context context) {
        this.context = context;
    }


    private String fcmToken;
    FcmUtils fcmUtils = new FcmUtils();

    String qrData, publicKey, deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void processData(String QRdata, String key, String devicename) {
        qrData = QRdata;
        publicKey = key;
        deviceName = devicename;
        fcmUtils.fetchFcmToken(this);


    }

    @Override
    public void onFcmTokenObtained(String fcmToken) {
        this.fcmToken = fcmToken;
        Log.w("FCM TOKEN Value:", fcmToken);

        JsonObject jsonData = new JsonObject();

        jsonData.addProperty("deviceName", deviceName);
        jsonData.addProperty("deviceType", "android");
        jsonData.addProperty("publicKey", publicKey);
        jsonData.addProperty("deviceToken", fcmToken);
        startDeviceRegisterProcess(qrData, jsonData);

    }

    @Override
    public void onFcmFailure(Throwable exception) {
        Log.e(TAG, "Error obtaining FCM token", exception);
    }


    public void startDeviceRegisterProcess(String endpoint, JsonObject jsonData) {
        MFAPushAPI api = new MFAPushAPI();

        api.registerDevice(endpoint, jsonData, new AsyncHandler<DeviceRegisterResponse>() {
            @Override
            public void onSuccess(DeviceRegisterResponse deviceRegisterResponse) {

 SharedPreferences sh = context.getSharedPreferences("Message", Context.MODE_PRIVATE);
  String message = sh.getString("message", "");
  String displayMessage  = !TextUtils.isEmpty(message)? message : "Registration Successful.";

                Toast.makeText ( context, displayMessage, Toast.LENGTH_LONG ).show ( );
                Log.w("Registration Successful", deviceRegisterResponse.toString());

            }

                       @Override
            public void onFailure ( Throwable error, String errorcode ) {
                Log.w ( "Error is", error.getMessage ( ) );
                if (error.getMessage ( ).contains("<html>") && error.getMessage ( ).contains("</html>")){
                    Toast.makeText ( context, "Error:" + "Network Error Try Again!", Toast.LENGTH_LONG ).show ( );

                }else {
                    Toast.makeText(context, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
