package com.loginradius.androidsdk.helper;


import static android.content.Context.MODE_PRIVATE;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;

import android.util.Log;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;


public class QRScanner {


    private static final String TAG = "QRScanner";

    public Context context;


    public QRScanner(Context context) {

        this.context = context;

    }

    private String publicKeyValue;

    public String getPublicKeyValue() {
        return publicKeyValue;
    }

    public void setPublicKeyValue(String publicKeyValue) {
        this.publicKeyValue = publicKeyValue;
    }


    public void startScan(String Message) {
       SharedPreferences sharedPreferences = context.getSharedPreferences("Message",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("message", Message);
        myEdit.commit();

        IntentIntegrator integrator = new IntentIntegrator((Activity) context);
        integrator.setPrompt("Scanning QR code");
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.initiateScan();

    }

    public KeyPair generateKeyPairs() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            return keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //MFAPushHandler the result of the QR code scanning
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // MFAPushHandler cancellation
                Log.d(TAG, "Scanning cancelled");
                Toast.makeText(context, "Scanning cancelled.", Toast.LENGTH_LONG).show();
            } else {
                // MFAPushHandler QR code data
                String qrData = result.getContents();
                Log.d(TAG, "QR Code Data: " + qrData);
                // Process the QR code data further as needed


                KeyPair keyPair = generateKeyPairs();

                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();


                String privateKeyString = bytesToHex(privateKey.getEncoded());


                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);


                SharedPreferences.Editor myEdit = sharedPreferences.edit();


                myEdit.putString("privateKey", privateKeyString);

                myEdit.commit();


                String publicKeyString = bytesToHex(publicKey.getEncoded());
                setPublicKeyValue(publicKeyString);


                MFAPushHandler mfaPushHandler = new MFAPushHandler(context);

                mfaPushHandler.processData(qrData, getPublicKeyValue(), getDeviceName());

            }
        }
    }


    // Method to convert Hexadecimal String to byte array
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException("Hex string cannot be null");
        }

        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    // Method to convert Hexadecimal String to PrivateKey
    public static PrivateKey hexStringToPrivateKey(String hexString) throws Exception {


        byte[] privateKeyBytes = hexStringToBytes(hexString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }


    public static String getDeviceName() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    @SuppressLint("LongLogTag")
    public String createSignature(String Value, String SFA) {
        // Sign the payload


        String signedData1 = null;
        String response = Value;
        String secondFactorToken = SFA;
        String payload = response + secondFactorToken;

        SharedPreferences sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String s1 = sh.getString("privateKey", "");

        Log.w("Private Key for signing the data", s1);


        try {
            if (payload != null) {
                PrivateKey reconstructedPrivateKey = hexStringToPrivateKey(s1);


                Signature signature = Signature.getInstance("SHA256withRSA");
                signature.initSign(reconstructedPrivateKey);


                signature.update(payload.getBytes());
                byte[] signedPayload = signature.sign();
                String signedData = bytesToHex(signedPayload);
                signedData1 = signedData;
                Log.w("Signed data (signature): ", signedData);


            } else {

                Toast.makeText(context, "Please Provide valid parameters to Sign.", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedData1;


    }


}
