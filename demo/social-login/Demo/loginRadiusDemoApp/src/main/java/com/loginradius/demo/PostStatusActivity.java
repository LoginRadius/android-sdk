package com.loginradius.demo;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.ui.lrLoginManager;
import com.loginradius.sdk.util.AsyncHandler;

public class PostStatusActivity extends Activity {

	private String[] supportedProviders = new String[]{"Facebook","Twitter","Linkedin"};
	public static lrAccessToken token=null;
	public AppInformation appinfo;
	public ListView lstview =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_provider_list);
		initialize();
	}

	public void initialize() {
		lrLoginManager.nativeLogin=false;
		lstview = (ListView) findViewById(R.id.providers_list);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		lstview.setAdapter(adapter);
		String apiKey= getString(R.string.LoginRadius_API_Key);
		lrLoginManager.getAppConfiguration(apiKey,
				new AsyncHandler<AppInformation>() {
					
					@Override
					public void onSuccess(AppInformation appInfo) {
						appinfo = appInfo;
						for(Provider provider : appInfo.Providers)
						{	
							String selectedProvider = provider.Name;
							if(Arrays.asList(supportedProviders).contains(selectedProvider)){
								adapter.add(selectedProvider);
								adapter.notifyDataSetChanged();
							}
						}
						setlistclicklistener();
					}
					@Override
					public void onFailure(Throwable error, String errorcode) {
						Toast.makeText(getApplicationContext(), errorcode, Toast.LENGTH_LONG).show();
					}
		});		
	}
	public void setlistclicklistener() {
		lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				int i=PostStatusActivity.this.getposition(position);    
				
    				lrLoginManager.performLogin(PostStatusActivity.this,appinfo.Providers.get(i) , new AsyncHandler<lrAccessToken>() {
					@Override
					public void onSuccess(lrAccessToken data) {
						StatusUpdate.token = data; 
						Intent i = new Intent(PostStatusActivity.this,StatusUpdate.class);
					    startActivity(i);					
					}
					@Override
					public void onFailure(Throwable error, String errorcode) {
						
					}
				
				
				});		
			}
		});
	}
	
	public int getposition(int position) {
		int i=0;
		for(Provider p : appinfo.Providers){
			if(lstview.getItemAtPosition(position).toString().equalsIgnoreCase(p.Name)){
	    		return i;
	    	}
	    	else i++;
	    	return i;
	    }
		return i;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_status, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	
}
