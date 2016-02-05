package com.loginradius.demo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;

public class LoginRadius extends Activity 
{
  @SuppressWarnings("unused")
private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_radius_welcome_screen);
		imageView=(ImageView)findViewById(R.id.lrSplash);
		
	    Thread logoTimer = new Thread() 
	    {
            public void run(){
                try
                {
                    int logoTimer = 0;
                    while(logoTimer <2000)
                    {
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };
                    startActivity(new Intent(LoginRadius.this,MainActivity.class));	
                } 
                 
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                 
                finally
                {
                    finish();
                }
            }
        };
         
        logoTimer.start();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.login_radius_welcome_screen, menu);
		return true;
	}

}
