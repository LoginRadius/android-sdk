package com.loginradius.demo;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by loginradius on 8/16/2017.
 */

public class NotifyToastUtil {
    private NotifyToastUtil() {
    }

    public static void showNotify(Activity activity,String message){
        final LinearLayout layout = (LinearLayout)activity.findViewById(R.id.linearNotify);
        TextView tvNotify = (TextView)activity.findViewById(R.id.str_lr_message);
        tvNotify.setText(message);
        Animation fadeIn = AnimationUtils.loadAnimation(activity,R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(activity,R.anim.fade_out);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.startAnimation(fadeOut);
                    }
                },500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        layout.setVisibility(View.VISIBLE);
        layout.startAnimation(fadeIn);
    }
}
