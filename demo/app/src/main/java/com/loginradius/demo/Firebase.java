package com.loginradius.demo;

import android.annotation.SuppressLint;


import android.content.Intent;

import android.os.Build;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class Firebase extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMessagingService";


    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, String.format("Received FCM message from: %s with data: %s", remoteMessage.getFrom(), remoteMessage.getData()));


        // Extract notification details
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        String sender = String.valueOf(data);


        System.out.println("Notification Received" + sender);


        Intent dialogIntent = new Intent(this, DisplayScreen.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Put data into intent extras
        for (Map.Entry<String, String> entry : data.entrySet()) {
            dialogIntent.putExtra(entry.getKey(), entry.getValue());
        }


        startActivity(dialogIntent);


        String secondFactorAuthToken = data.get("secondfactorauthenticationtoken");
        String identifier = data.get("identifier");
        String browser = data.get("browser");
        String location = data.get("location");
        String timestamp = data.get("timestamp");
        String apiKey = data.get("apikey");
        // Convert Unix timestamp to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestamp)), ZoneId.systemDefault());

        // Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        Log.d(TAG, secondFactorAuthToken);
        Log.d(TAG, identifier);
        Log.d(TAG, browser);
        Log.d(TAG, location);
        Log.d(TAG, apiKey);
        Log.d(TAG, timestamp);
        Log.d(TAG,formattedDateTime);



    }


    @SuppressLint("LongLogTag")
    @Override
    public void onNewToken(@NonNull String token) {
        //
        Log.d(TAG, "Refreshed token: " + token);


    }


}
