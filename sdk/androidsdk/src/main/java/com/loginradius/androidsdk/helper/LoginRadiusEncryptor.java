package com.loginradius.androidsdk.helper;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by loginradius on 22/05/2022.
 */

public class LoginRadiusEncryptor {
    private static Encryptor encryptor=new Encryptor();
    private static final String LOG_TAG = "LoginRadiusEncryptor";

    /**
     * This function is used to encrypt the sensitive information
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String encryptData(String data){

        try {
           return  encryptor.encryptText(data);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchProviderException | BadPaddingException | IllegalBlockSizeException | UnrecoverableEntryException e) {
            Log.e(LOG_TAG, "encryptData() executed with error: ", e);
        }
        return null;
    }

    /**
     * This function is used to decrypt encrypted data
     * @param encryptedData encrypted data to be decrypted
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String  decryptData(String encryptedData){
        try {
            return encryptor.decryptText(encryptedData);
        }  catch (NoSuchPaddingException | NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchProviderException | BadPaddingException | IllegalBlockSizeException | UnrecoverableEntryException e) {
            Log.e(LOG_TAG, "decryptData() executed with error: ", e);
        }
        return null;
    }
}
