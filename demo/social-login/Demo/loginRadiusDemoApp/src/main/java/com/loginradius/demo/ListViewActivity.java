package com.loginradius.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.ui.LRinterfaceimpl;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;

public class ListViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		initialize();		
	}

	public void initialize() {
		lrLoginManager.nativeLogin=false;
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
	
	private void populateListView(AppInformation appInfo) {
	    LRinterfaceimpl li = new LRinterfaceimpl();
	    lrLoginManager.ImageUrl = "https://cdn.loginradius.com/mobile/android/images/";
        lrLoginManager.ImgVersion="v1";
		ListView listview = (ListView) findViewById(R.id.listView1);		
		li.attachListView(appInfo, listview, this, new AsyncHandler<lrAccessToken>() {
			@Override
			public void onSuccess(lrAccessToken token) {
				UserProfileActivity.token = token;
				Intent i = new Intent(ListViewActivity.this,UserProfileActivity.class);
				startActivity(i);
				ListViewActivity.this.finish();
			}
			@Override
			public void onFailure(Throwable error, String errorcode) {
				/** Handling errors **/
				Toast.makeText(getApplicationContext(), "There is some error occurred "+errorcode, Toast.LENGTH_LONG).show();
			}
		});		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.list_view, menu);
		return true;
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
