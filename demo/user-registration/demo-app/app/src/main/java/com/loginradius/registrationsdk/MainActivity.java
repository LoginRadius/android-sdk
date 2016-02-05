package com.loginradius.registrationsdk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    Button loginBt, signinBt, socialBt, forgetBt;
    Button.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("LoginRadius Android Demo");

        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebviewActivity.class);
                switch (v.getId()) {
                    case R.id.login_bt:
                        intent.putExtra("action", "LOGIN");
                        break;
                    case R.id.signin_bt:
                        intent.putExtra("action", "SIGNIN");
                        // do stuff;
                        break;
                    case R.id.social_bt:
                        intent.putExtra("action", "SOCIAL");
                        break;
                    case R.id.forget_bt:
                        intent.putExtra("action", "FORGET");
                        break;
                    default:
                        return;
                }
                startActivity(intent);
            }
        };
        initWidget();
    }

    private void initWidget() {
        //initialize button
        loginBt = (Button) findViewById(R.id.login_bt);
        signinBt = (Button) findViewById(R.id.signin_bt);
        socialBt = (Button) findViewById(R.id.social_bt);
        forgetBt = (Button) findViewById(R.id.forget_bt);
        //set on Click listener
        loginBt.setOnClickListener(listener);
        signinBt.setOnClickListener(listener);
        socialBt.setOnClickListener(listener);
        forgetBt.setOnClickListener(listener);
        //resetBt.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
