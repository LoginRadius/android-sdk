package com.loginradius.androidsdk.helper;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
/**
 * Created by loginradius on 22/05/2022.
 */

public class Encryptor {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String AES_MODE= "AES/GCM/NoPadding";
    private static final String KEY_ALIAS = "LoginRadiusEncryption";
    private static final byte[] IV = new byte[12];
    private static final String LOG_TAG = "Encryptor";
    private final static Object keyInitLock = new Object();

    /**
     * This function is used to load security keys while executing
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, NoSuchProviderException, InvalidAlgorithmParameterException, UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            initValidKeys();
        } else {
            boolean keyValid = false;
            try {
                KeyStore.Entry keyEntry = keyStore.getEntry(KEY_ALIAS, null);
                if (keyEntry instanceof KeyStore.SecretKeyEntry &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    keyValid = true;
                }
            } catch (NullPointerException | UnrecoverableKeyException e) {
                Log.e(LOG_TAG, "Failed to get key entry", e);
            }

            if (!keyValid) {
                synchronized (keyInitLock) {
                    // System upgrade or something made key invalid so remove the existing key
                    removeSecurityKeys(keyStore);
                    initValidKeys();
                }
            }

        }

    }

    protected void removeSecurityKeys(KeyStore keyStore) throws KeyStoreException {
        keyStore.deleteEntry(KEY_ALIAS);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initValidKeys() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException{
        synchronized (keyInitLock) {
            generateKeys();
        }
    }

    /**
     * This function is used to generate security keys
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyGenerator keyGenerator;
        keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
        keyGenerator.init(
                new KeyGenParameterSpec.Builder(KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setRandomizedEncryptionRequired(false)
                        .build());
        keyGenerator.generateKey();
    }

    /**
     * This function is used to encrypt sensitive data
     * @param textToEncrypt data to be encrypted
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String encryptText(String textToEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableEntryException, CertificateException, KeyStoreException, IOException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException {

        loadKey();

        if (textToEncrypt == null) {
            throw new IllegalArgumentException("Data to be decrypted must be non null");
        }

        Cipher cipher;
        cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(),
                new GCMParameterSpec(128, IV));

        byte[] encodedBytes = cipher.doFinal(textToEncrypt.getBytes("UTF-8"));
        String encryptedBase64Encoded = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
        return encryptedBase64Encoded;

    }

    /**
     * This function is used to decrypt encrypted data
     * @param encryptedData encrypted data to be decrypted
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String decryptText(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableEntryException, CertificateException, KeyStoreException, IOException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException {

        loadKey();

        if (encryptedData == null) {
            throw new IllegalArgumentException("Data to be decrypted must be non null");
        }

        byte[] encryptedDecodedData = Base64.decode(encryptedData, Base64.DEFAULT);

        Cipher c;

        try {
            c = Cipher.getInstance(AES_MODE);
            c.init(Cipher.DECRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, IV));
        } catch (InvalidKeyException | IOException e) {
            removeSecurityKeys();
            throw e;
        }

        byte[] decodedBytes = c.doFinal(encryptedDecodedData);
        return new String(decodedBytes, "UTF-8");

    }

    /**
     * This function is used to remove security keys if something goes wrong with existing key
     */
    public void removeSecurityKeys() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        synchronized (keyInitLock) {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            removeSecurityKeys(keyStore);
        }
    }

    /**
     * This function is used to fetch secret keys
     */
    private Key getSecretKey() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
        return keyStore.getKey(KEY_ALIAS, null);

    }


}