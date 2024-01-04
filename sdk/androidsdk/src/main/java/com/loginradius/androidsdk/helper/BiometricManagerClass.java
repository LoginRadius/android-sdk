package com.loginradius.androidsdk.helper;


import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.resource.BiometricPromptBuilder;

import java.util.concurrent.Executor;

public class BiometricManagerClass extends AppCompatActivity {

    public interface BiometricCallback {
        void onBiometricAuthenticationSuccess ( );
        void onBiometricAuthenticationFailure( );
        void onBiometricAuthenticationError(ErrorResponse error);
    }

    public interface biometricSupported{

        void onBiometricDeviceSuccess();
        void onBiometricDeviceError(ErrorResponse error);
    }

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private Context context;

    public BiometricManagerClass ( Context context ) {
        this.context = context;
    }

    public void showBiometricPrompt (BiometricPromptBuilder biometricPromptBuilder, BiometricCallback callback ) {
        String title = ((biometricPromptBuilder.getTitle () != null &&  !biometricPromptBuilder.getTitle ().trim().isEmpty()) ? biometricPromptBuilder.getTitle() : "Biometric Login");
        String subTitle = ((biometricPromptBuilder.getSubtitle () != null &&  !biometricPromptBuilder.getSubtitle ().trim().isEmpty()) ? biometricPromptBuilder.getSubtitle() : "Log in using Biometric Credential");
        String negativeButtonName = ((biometricPromptBuilder.getNegativeButtonName () != null &&  !biometricPromptBuilder.getNegativeButtonName ().trim().isEmpty()) ? biometricPromptBuilder.getNegativeButtonName(): "Cancel");
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder ( )
                .setTitle (title)
                .setSubtitle (subTitle)
                .setNegativeButtonText (negativeButtonName)
                .setConfirmationRequired ( true )
                .build ( );


        executor = ContextCompat.getMainExecutor ( context );
        biometricPrompt = new BiometricPrompt ( (FragmentActivity) context,
                executor, new BiometricPrompt.AuthenticationCallback ( ) {

            @Override
            public void onAuthenticationError ( int errorCode, CharSequence errString ) {
                super.onAuthenticationError ( errorCode, errString );
                ErrorResponse errorResponse = (ErrorResponse) JsonDeserializer.deserializeJson(errString.toString(), ErrorResponse.class);
                errorResponse.setMessage ( errString.toString () );
                errorResponse.setErrorCode (  errorCode  );
                callback.onBiometricAuthenticationError ( errorResponse );
            }

            @Override
            public void onAuthenticationSucceeded ( BiometricPrompt.AuthenticationResult result ) {
                super.onAuthenticationSucceeded ( result );
                callback.onBiometricAuthenticationSuccess ( );
            }

            @Override
            public void onAuthenticationFailed () {
                super.onAuthenticationFailed ( );
                callback.onBiometricAuthenticationFailure ( );
            }
        } );
        biometricPrompt.authenticate ( promptInfo );
    }


    public void isBiometricSupported ( biometricSupported handler) {
        BiometricManager biometricManager = BiometricManager.from ( context );
        int canAuthenticate = biometricManager.canAuthenticate ( BiometricManager.Authenticators.BIOMETRIC_WEAK );
        switch (canAuthenticate) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                // The user can authenticate with biometrics, continue with the authentication process
                handler.onBiometricDeviceSuccess ();
                break;

            //The device does not have the necessary hardware components to support biometric authentication.
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                ErrorResponse noHardwareError = JsonDeserializer.deserializeJson("The Device does not support Biometric Authentication. ", ErrorResponse.class);
                noHardwareError.setErrorCode (  BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE );
                noHardwareError.setMessage ( "There is no biometric hardware." );
                handler.onBiometricDeviceError ( noHardwareError );
                break;

            // The hardware required for biometric authentication is currently unavailable or cannot be accessed for some reason.
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                ErrorResponse unavailableError = JsonDeserializer.deserializeJson("The hardware required for biometric authentication is currently unavailable or cannot be accessed for some reason.", ErrorResponse.class);
                unavailableError.setErrorCode ( BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE );
                unavailableError.setMessage ( "The hardware is unavailable. Try again later." );
                handler.onBiometricDeviceError ( unavailableError );
                break;

            // No biometric profiles (e.g., fingerprints or facial recognition data) have been enrolled on the device.
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                ErrorResponse enrolledError = JsonDeserializer.deserializeJson("No biometric profiles have been enrolled on the device. ", ErrorResponse.class);
                enrolledError.setErrorCode ( BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED );
                enrolledError.setMessage ( "The user does not have any biometrics enrolled." );
                handler.onBiometricDeviceError ( enrolledError );
                break;

            default:
                // Biometric status unknown or another error occurred
                ErrorResponse errorUnknown = JsonDeserializer.deserializeJson("Unknown Status. ", ErrorResponse.class);
                errorUnknown.setMessage ( "Status Unknown." );
                handler.onBiometricDeviceError ( errorUnknown );
                break;


        }
    }

}
