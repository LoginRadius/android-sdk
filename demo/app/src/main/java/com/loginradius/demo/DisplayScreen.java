package com.loginradius.demo;

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.MFAPushAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;

import com.loginradius.androidsdk.helper.QRScanner;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.PostResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DisplayScreen extends AppCompatActivity {
    QRScanner qrscanner = new QRScanner ( this );
    QueryParams queryParams = new QueryParams ( );

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.display_screen );
        Log.d ( TAG, "Display Screen" );

        // Retrieve data from intent extras
       String secondFactorAuthToken = getIntent ( ).getStringExtra ( "secondfactorauthenticationtoken" );

       String identifier = getIntent ( ).getStringExtra ( "identifier" );
        String browser = getIntent ( ).getStringExtra ( "browser" );
        String location = getIntent ( ).getStringExtra ( "location" );
        String timestamp = getIntent ( ).getStringExtra ( "timestamp" );
        String apikey = getIntent ( ).getStringExtra ( "apikey" );
        queryParams.setApiKey ( apikey );
        queryParams.setSecondfactorauthenticationtoken ( secondFactorAuthToken );

        System.out.println("secondFactorAuthToken");

        LocalDateTime dateTime = LocalDateTime.ofInstant ( Instant.ofEpochSecond ( Long.parseLong ( timestamp ) ), ZoneId.systemDefault ( ) );

        // Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss" );
        String formattedDateTime = dateTime.format ( formatter );


        // Set data to views

        TextView nameTextView = findViewById ( R.id.NameTextView );
        TextView deviceTextView = findViewById ( R.id.deviceTextView );
        TextView locationTextView = findViewById ( R.id.locationtextview );
        TextView timeTextView = findViewById ( R.id.timetextview );


        nameTextView.setText ( identifier );
        deviceTextView.setText ( browser );
        locationTextView.setText ( location );
        timeTextView.setText ( formattedDateTime );


        // MFAPushHandler button clicks
        Button noButton = findViewById ( R.id.noButton );
        noButton.setOnClickListener ( v -> {
            // MFAPushHandler "No, don't allow" button click

            String signedData = qrscanner.createSignature ( "No", secondFactorAuthToken );
            MFAPushAPI api = new MFAPushAPI ( );
            JsonObject jsonData = new JsonObject ( );


            jsonData.addProperty ( "verify", "No" );
            jsonData.addProperty ( "signature", signedData );
            api.MfaPushVerification ( jsonData, queryParams, new AsyncHandler<PostResponse> ( ) {

                @Override
                public void onSuccess ( PostResponse data ) {
                    Toast.makeText ( DisplayScreen.this, "Verification Denied.", Toast.LENGTH_LONG ).show ( );
                    Intent i = new Intent ( getApplicationContext ( ), MainActivity.class );
                    startActivity ( i );

                }

                @Override
                public void onFailure ( Throwable error, String errorcode ) {
                    System.out.println ( "Error is:" + error.getMessage ( ) );
                    Toast.makeText ( DisplayScreen.this, "Error:" + error.getMessage ( ), Toast.LENGTH_LONG ).show ( );
                }
            } );

        } );

        Button yesButton = findViewById ( R.id.yesButton );
        yesButton.setOnClickListener ( v -> {
            // MFAPushHandler "Yes, it's me" button click

            //Sign Yes and SFA and send to the API
            String signedData = qrscanner.createSignature ( "Yes", secondFactorAuthToken );
            MFAPushAPI api = new MFAPushAPI ( );
            JsonObject jsonData = new JsonObject ( );


            jsonData.addProperty ( "verify", "Yes" );
            jsonData.addProperty ( "signature", signedData );
            api.MfaPushVerification ( jsonData, queryParams, new AsyncHandler<PostResponse> ( ) {

                @Override
                public void onSuccess ( PostResponse data ) {
                    Toast.makeText ( DisplayScreen.this, "Verification sent successfully", Toast.LENGTH_LONG ).show ( );

                    Intent i = new Intent ( getApplicationContext ( ), MainActivity.class );
                    startActivity ( i );
                }

                @Override
                public void onFailure ( Throwable error, String errorcode ) {
                    System.out.println ( "Error is:" + error.getMessage ( ) );
                    Toast.makeText ( DisplayScreen.this, "Error:" + error.getMessage ( ), Toast.LENGTH_LONG ).show ( );
                }
            } );
        } );


    }
}
