


package com.loginradius.sdk.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;



import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.JsonDeserializer;
import com.loginradius.sdk.util.RestRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import android.app.AlertDialog;

//import twitter4j.TwitterException;

/**
 * Performs user login and manages different native / web logins
 *
 */
public class lrLoginManager {
	private static final String MOBILE_EXT = "&ismobile=1";
	/** Enable orDisable Native Login for Google and Facebook **/
	public static Boolean nativeLogin = false;
	/** Callback function. SDK handles this variable. No need to modify. **/ 
	public static AsyncHandler<lrAccessToken> asyncHandler;
	/** LoginRadius api key. SDK handles this variable. No need to modify. **/
	protected static String AKey;
	/** Image Url for getting the image icons of Providers **/
	public static String ImageUrl;
	/** File System version **/
	public static String ImgVersion;
	/**Callback handler for Face book Native Login **/
	protected static CallbackManager lrCallbackManager;

	/**
	 * Authenticate api key with LoginRadius server. 
	 * @param key	apikey KEY given by the Loginradius
	 * @param asyncHandler	callback returning AppInformation
	 */
	public static void getAppConfiguration(String key, final AsyncHandler<AppInformation> asyncHandler) {
		lrLoginManager.AKey = key;
		restHandler(key, asyncHandler);
	}
	/**
	 * Authenticate api key with LoginRadius server.
	 * @param key	apikey KEY given by the Loginradius
	 * @param callback callback manager for Facebook Native Login
	 * @param asyncHandler	callback returning AppInformation
	 */
	public static void getNativeAppConfiguration(String key,CallbackManager callback, final AsyncHandler<AppInformation> asyncHandler) {
		lrLoginManager.AKey = key;
		lrCallbackManager=callback;
		restHandler(key, asyncHandler);
	}

	/**
	 * Base function to log in user. Ensure static 'asyncHandler' set first
	 * @param activity Activity where the user will land after login process
	 * @param provider	provider to be logged in with from providers list retrieved from loginradius server.
	 * @param asyncHandler	callback function
	 */
	public static void performLogin(final Activity activity, Provider provider, AsyncHandler<lrAccessToken> asyncHandler) {
		lrLoginManager.asyncHandler = asyncHandler;
		if (provider.Name.equalsIgnoreCase("facebook") && lrLoginManager.nativeLogin) {
			/** FACEBOOK SESSION **/
			OpenFacebookSession(activity);
			/**openActiveSession(activity, true);**/
		}
		else if (provider.Name.equalsIgnoreCase("google") && lrLoginManager.nativeLogin) {
			/** Google Activity **/
			activity.startActivity(new Intent(activity, GoogleSSO.class));
		}

		else {
			performWebLogin(activity, provider);
		}
	}

	/**
	 * Redirect user to a web view for provider-specific login
	 * @param activity Activity where the user will be returned
	 * @param provider provider by which the login process was started
    */
	public static void performWebLogin(final Activity activity, Provider provider) {
		Intent i = new Intent(activity, WebLogin.class);
		i.putExtra(WebLogin.KEY_URL, provider.Endpoint + MOBILE_EXT);
		i.putExtra(WebLogin.KEY_PROVIDER, provider.Name);
		activity.startActivity(i);
	}

	private static void HandleFacebookToken()
	{
		try{
			String fbToken = AccessToken.getCurrentAccessToken().getToken().toString();
			TokenHelper helper = new TokenHelper();
			helper.getResponseFb(fbToken, new AsyncHandler<lrAccessToken>() {
				@Override
				public void onSuccess(lrAccessToken data) {
					data.provider = "facebook";
					lrLoginManager.asyncHandler.onSuccess(data);
				}

				@Override
				public void onFailure(Throwable error, String code) {
					lrLoginManager.asyncHandler.onFailure(error, code);
				}
			});
		}
		catch(FacebookOperationCanceledException f){
			Log.i("Cancel","facebook");
			lrLoginManager.asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
		}

	}






	public static void SetMissingPermission()
   {
	   Collection<String> permissions = AccessToken.getCurrentAccessToken().getPermissions();
	   List<String> CurrentPubPerms=ProviderPermissions.getFBPublishPermissionsArr();
	   List<String> CurrentReadPerms=ProviderPermissions.getFBReadPermissionsArr();
	   List<String> CurrentPerms=new ArrayList<String>();
	   CurrentPerms.addAll(CurrentReadPerms);
	   CurrentPerms.addAll(CurrentPubPerms);
	   ProviderPermissions.resetPermissions();
	   for (String sPerm : CurrentPerms)
	   {
		   if (!permissions.contains(sPerm))
		   {

			  FB_Permission temp[] = FB_Permission.values();
			   for (FB_Permission findid : temp) {
				   if (findid.id==sPerm)
				   {
					   ProviderPermissions.addFbPermission(findid);
				   }
			   }
		   }
	   }
   }

	private static void OpenFacebookSession(final Activity activity) {

		LoginManager.getInstance().registerCallback(lrCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				HandleFacebookToken();
			}

			@Override
			public void onCancel() {
				Log.i("Cancel", "facebook");
				lrLoginManager.asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
			}

			@Override
			public void onError(FacebookException error) {
				lrLoginManager.asyncHandler.onFailure(error, error.toString());
			}

		});

		if (ProviderPermissions.getFBPublishPermissions().length() == 0) {
			LoginManager.getInstance().logInWithReadPermissions(activity, ProviderPermissions.getFBReadPermissionsArr());
		}
		else{
			LoginManager.getInstance().logInWithPublishPermissions(activity,ProviderPermissions.getFBPublishPermissionsArr());
		}

	}







	/**
	 * Determine whether user is online or not
	 * @param c Application context
	 * @return Online status of the user
	 */
	public static boolean isOnline(Context c) {
		if (c == null) return false;
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable() && ni.isConnected());
	}

	/**
	 * Request for user's LoginRadius app settings
	 * @param key	API-KEY provided by the loginradius
	 * @param asyncHandler	callback handler
	 */
	private static void restHandler(String key, final AsyncHandler<AppInformation> asyncHandler) {

		// Format key into map
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", key);

		// Send request to LoginRadius server
		RestRequest.get("/api/v2/app/jsinterface", params, new AsyncHandler<String>() {
			@Override
			public void onSuccess(String data) {
				AppInformation appInfo = JsonDeserializer.deserializeJson(data, AppInformation.class);
				asyncHandler.onSuccess(appInfo);
			}

			@Override
			public void onFailure(Throwable error, String errorCode) {
				Log.e("lrLoginManager", "API json can't get fetched"); //Log error to handle the errors
				asyncHandler.onFailure(error, errorCode);
			}
		});
	}
	
	/* Handling Facebook Operation Cancellation Event for native facebook login */
	public static void onNativeFailure(){
		   asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
	}

}
