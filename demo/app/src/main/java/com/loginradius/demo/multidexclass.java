package com.loginradius.demo;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

/**
 * Created by loginradius on 11-Oct-16.
 */

public class multidexclass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
