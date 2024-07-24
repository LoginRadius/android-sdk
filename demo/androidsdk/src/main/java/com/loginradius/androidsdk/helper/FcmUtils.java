package com.loginradius.androidsdk.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FcmUtils {

    public interface FcmTokenListener {
        void onFcmTokenObtained ( String fcmToken );

        void onFcmFailure ( Throwable exception );
    }

    public void fetchFcmToken ( FcmTokenListener listener ) {


        FirebaseMessaging.getInstance ( ).getToken ( ).addOnCompleteListener ( new OnCompleteListener<String> ( ) {
            @Override
            public void onComplete ( @NonNull Task<String> task ) {
                if (!task.isSuccessful ( )) {
                    Log.w ( "FCM TOKEN Failed", task.getException ( ) );
                    listener.onFcmFailure ( task.getException ( ) );

                    return;

                }


                String token = task.getResult ( );

                listener.onFcmTokenObtained ( token );


            }
        } );
    }


}
