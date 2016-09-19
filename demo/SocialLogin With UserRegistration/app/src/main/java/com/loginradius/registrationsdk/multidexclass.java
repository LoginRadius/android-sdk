package com.loginradius.registrationsdk;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by loginradius on 9/2/2016.
 */
public class multidexclass extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}