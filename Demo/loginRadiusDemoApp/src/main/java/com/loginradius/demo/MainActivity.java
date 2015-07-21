package com.loginradius.demo;
//https://code.google.com/apis/console/b/0/?noredirect#project:274151280735:access

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		setButtonListener();
	}
	
	private void setButtonListener(){
		Button btn = (Button) findViewById(R.id.Button01);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,ListViewActivity.class);
				startActivity(i);				
			}
		});
		Button btn2 = (Button) findViewById(R.id.Button02);
        btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,SingleLoginActivity.class);
				startActivity(i);				
			}
		});
		Button btn3 = (Button) findViewById(R.id.Button03);
        btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,NativeLoginActivity.class);
				startActivity(i);
			}
		});
		Button btn4 = (Button) findViewById(R.id.Button04);
        btn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,PostStatusActivity.class);
				startActivity(i);
			}
		});
		Button btn5 = (Button) findViewById(R.id.Button05);
		btn5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,NativeFacebookPermissionsUpdate.class);
				startActivity(i);
			}
		});
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		return true;
	}
	
}
