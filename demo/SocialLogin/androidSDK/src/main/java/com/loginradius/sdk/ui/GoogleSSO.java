package com.loginradius.sdk.ui;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.util.AsyncHandler;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 *	Manage Google token requests - used for Google Native Login
*/
@SuppressLint("InlinedApi")
public class GoogleSSO extends Activity
{
	private static final int ACCOUNT_CODE = 1601;
	static final int REQUEST_AUTHORIZATION = 2;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("hello","qwe");

		if (savedInstanceState == null) {		
			Intent googlePicker = AccountPicker.newChooseAccountIntent(null,null,
					new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},true,"Choose Your Google Account",null,null,null) ;
			GoogleSSO.this.startActivityForResult(googlePicker,ACCOUNT_CODE);
		} 
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void requestToken(final String accountName) 
	{
		final Context context=this.getApplicationContext();
		AsyncTask task = new AsyncTask() 
		{
			
			@Override
			protected Object doInBackground(Object... params) 
			{
				String token=null;
				try {
					token = GoogleAuthUtil.getToken(context,accountName, ProviderPermissions.SCOPES);
				} catch (UserRecoverableAuthException e) {
					this.cancel(true);
					handleRecoverableerror(e);					
				} catch (Exception e) {
					e.printStackTrace();
				} 
				return token;

			}

			@Override
			protected void onPostExecute(Object token) 
			{
				executeForResult((String)token);
				finish();				
			}
		};
		task.execute((Void) null);
	}

	private void handleRecoverableerror(UserRecoverableAuthException u){
		GoogleSSO.this.startActivityForResult(u.getIntent(),REQUEST_AUTHORIZATION);
	}
	
	private void executeForResult(String token){
		TokenHelper helper = new TokenHelper();
		helper.getResponseGoogle((String)token, new AsyncHandler<lrAccessToken>() {
			@Override
			public void onSuccess(lrAccessToken data) {
				data.provider = "google";
				lrLoginManager.asyncHandler.onSuccess(data);
			}
			@Override
			public void onFailure(Throwable error, String response) {
				lrLoginManager.asyncHandler.onFailure(error, response);
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACCOUNT_CODE && resultCode == RESULT_OK) 
		{
			String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			requestToken(accountName);
		} else if (resultCode == Activity.RESULT_CANCELED) {
			lrLoginManager.asyncHandler.onFailure(new Throwable("GoogleSSO cancelled"), "lr_LOGIN_CANCELLED");
			finish();
		} else if (requestCode == REQUEST_AUTHORIZATION){//Handling the UserRecoverableAuthException
			 Bundle b = data.getExtras();
			 String token = b.getString("authtoken");

			 executeForResult(token);
			 finish();
		}
	}
}
