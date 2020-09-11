package com.loginradius.androidsdk.helper;

import android.accounts.Account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.AccessTokenResponse;


/**
 *	Manage Google token requests - used for Google Native Login
 */
@SuppressLint("InlinedApi")
public class GoogleSSO extends Activity
{
	private static final int ACCOUNT_CODE = 1601;
	static final int REQUEST_AUTHORIZATION = 2;
	private GoogleSignInClient mGoogleSignInClient;
	private String googleServerClientID = LoginRadiusSDK.getGoogleServerClientID();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
   if(savedInstanceState == null) {

	   GoogleSignInOptions gso =null;
	   if (googleServerClientID == null){
		   if (LoginRadiusSDK.getGoogleScopes().length == 1) {
			   gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					   .requestEmail().requestProfile()
					   .requestScopes(LoginRadiusSDK.getGoogleScopes()[0]).build();
		   } else if (LoginRadiusSDK.getGoogleScopes().length > 1) {
			   gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					   .requestEmail().requestProfile()
					   .requestScopes(LoginRadiusSDK.getGoogleScopes()[0], LoginRadiusSDK.getGoogleScopes()).build();
		   }
	   }else if (googleServerClientID != null) {
		   if (LoginRadiusSDK.getGoogleScopes().length == 1) {
			   gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					   .requestEmail().requestProfile().requestServerAuthCode(googleServerClientID,true)
					   .requestScopes(LoginRadiusSDK.getGoogleScopes()[0]).build();
		   } else if (LoginRadiusSDK.getGoogleScopes().length > 1) {
			   gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					   .requestEmail().requestProfile().requestServerAuthCode(googleServerClientID,true)
					   .requestScopes(LoginRadiusSDK.getGoogleScopes()[0], LoginRadiusSDK.getGoogleScopes()).build();
		   }

	   }
	   mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
	   mGoogleSignInClient.signOut();
	   SignIn();

   }
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void requestToken(final Account accountName)
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
				executeForResult((String)token ,false);
				finish();
			}
		};
		task.execute((Void) null);
	}

	private void handleRecoverableerror(UserRecoverableAuthException u){
		GoogleSSO.this.startActivityForResult(u.getIntent(),REQUEST_AUTHORIZATION);
	}

	private void executeForResult(String token ,boolean offline){
		LoginRadiusAuthManager.getResponseGoogle((String)token,offline ,new AsyncHandler<AccessTokenResponse>() {
			@Override
			public void onSuccess(AccessTokenResponse data) {
				data.provider = "google";
				LoginRadiusAuthManager.asyncHandler.onSuccess(data);
			}
			@Override
			public void onFailure(Throwable error, String response) {
				LoginRadiusAuthManager.asyncHandler.onFailure(error, response);
			}
		});
	}

	private void SignIn() {
		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, ACCOUNT_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

			if (requestCode == ACCOUNT_CODE && resultCode == RESULT_OK) {
				GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
				GoogleSignInAccount acct = result.getSignInAccount();
				if(googleServerClientID ==null && result.isSuccess()) {
					requestToken(  acct.getAccount());
				}else if (googleServerClientID!=null && result.isSuccess()){
					executeForResult(acct.getServerAuthCode(),true);
					finish();
				}

	    	} else if (resultCode == Activity.RESULT_CANCELED) {
				LoginRadiusAuthManager.asyncHandler.onFailure(new Throwable("GoogleSSO cancelled"), "lr_LOGIN_CANCELLED");
				finish();
			} else if (requestCode == REQUEST_AUTHORIZATION) {//Handling the UserRecoverableAuthException
				Bundle b = data.getExtras();
				String token = b.getString("authtoken");
				executeForResult(token,false);
				finish();
			}

	}
}
