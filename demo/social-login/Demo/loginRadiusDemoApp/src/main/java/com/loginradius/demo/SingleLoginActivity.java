package com.loginradius.demo;


import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class SingleLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_login);
		Button loginButton = (Button) findViewById(R.id.button1);
		loginButton.setBackground(getResources().getDrawable(com.facebook.R.drawable.com_facebook_button_background));
		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_login, menu);
		return true;
	}

	public void initialize() {
		String apiKey= getString(R.string.LoginRadius_API_Key);
		lrLoginManager.getAppConfiguration(apiKey,
				new AsyncHandler<AppInformation>() {
					
					@Override
					public void onSuccess(AppInformation appInfo) {
						populateListView(appInfo);
					}
					
					@Override
					public void onFailure(Throwable error, String errorcode) {
						Toast.makeText(getApplicationContext(), errorcode, Toast.LENGTH_LONG).show();
					}
		});
	}
	public void populateListView(final AppInformation appinfo) {
		lrLoginManager.nativeLogin = false;
		int i=0;
		for(Provider p : appinfo.Providers){
			if(p.Name.equalsIgnoreCase("facebook"))
				break;
			else i++;
		}		
		final int index = i;
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lrLoginManager.performLogin(SingleLoginActivity.this, appinfo.Providers.get(index), new AsyncHandler<lrAccessToken>() {

					@Override
					public void onSuccess(lrAccessToken data) {
						UserProfileActivity.token = data;
						Intent i = new Intent(SingleLoginActivity.this,UserProfileActivity.class);
						startActivity(i);
					}

					@Override
					public void onFailure(Throwable error, String errorcode) {
						
						
					}
				});				
			}
		});		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
