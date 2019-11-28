


package com.loginradius.androidsdk.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.helper.ProviderPermissions.FacebookPermission;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



/**
 * Performs user login and manages different native / web logins
 *
 */
public class LoginRadiusAuthManager {
	private static final String MOBILE_EXT = "&ismobile=1";
	/** Enable orDisable Native Login for Google and Facebook **/
	public static Boolean nativeLogin = false;
	/** Callback function. SDK handles this variable. No need to modify. **/ 
	public static AsyncHandler<AccessTokenResponse> asyncHandler;
	/** LoginRadiusSDK api key. SDK handles this variable. No need to modify. **/
	protected static String AKey;
	/** Image Url for getting the image icons of Providers **/
	public static String ImageUrl;
	/** File System version **/
	public static String ImgVersion;
	/**Callback handler for Face book Native Login **/
	protected static CallbackManager lrCallbackManager;

	public static void setCallbackManager(String key,CallbackManager callback){
		LoginRadiusAuthManager.AKey = key;
		lrCallbackManager = callback;
	}

	/**
	 * Base function to log in user. Ensure static 'asyncHandler' set first
	 * @param activity Activity where the user will land after login process
	 * @param provider	provider to be logged in with from providers list retrieved from loginradius server.
	 * @param asyncHandler	callback function
	 */
	public static void performLogin(final Activity activity, Provider provider, AsyncHandler<AccessTokenResponse> asyncHandler) {
		LoginRadiusAuthManager.asyncHandler = asyncHandler;
		if (provider.getName().equalsIgnoreCase("facebook") && LoginRadiusAuthManager.nativeLogin) {
			/** FACEBOOK SESSION **/
			OpenFacebookSession(activity);
			/**openActiveSession(activity, true);**/
		}
		else if (provider.getName().equalsIgnoreCase("google") && LoginRadiusAuthManager.nativeLogin) {
			/** Google Activity **/
			activity.startActivity(new Intent(activity, GoogleSSO.class));
		}
		else if(provider.getName().equalsIgnoreCase("vkontakte") && LoginRadiusAuthManager.nativeLogin){
			/** VKONTAKTE SESSION **/
			OpenVkontakteSession(activity);
		}

		else {
			performWebLogin(activity, provider);
		}
	}

	public static void getNativeAppConfiguration(String key, CallbackManager callback) {
		LoginRadiusAuthManager.AKey = key;
		lrCallbackManager=callback;
	}

	/**
	 * Redirect user to a web view for provider-specific login
	 * @param activity Activity where the user will be returned
	 * @param provider provider by which the login process was started
    */
	public static void performWebLogin(final Activity activity, Provider provider) {
	//	Intent i = new Intent(activity, WebLogin.class);
	//	i.putExtra(WebLogin.KEY_URL, provider.Endpoint + MOBILE_EXT);
	//	i.putExtra(WebLogin.KEY_PROVIDER, provider.Name);
	//	activity.startActivity(i);
	}

	private static void HandleFacebookToken()
	{
		try{
			String fbToken = AccessToken.getCurrentAccessToken().getToken().toString();
			LoginRadiusAuthManager.getResponseFb(fbToken, new AsyncHandler<AccessTokenResponse>() {
				@Override
				public void onSuccess(AccessTokenResponse data) {
					data.provider = "facebook";
					LoginRadiusAuthManager.asyncHandler.onSuccess(data);
				}

				@Override
				public void onFailure(Throwable error, String code) {
					LoginRadiusAuthManager.asyncHandler.onFailure(error, code);

				}
			});
		}
		catch(FacebookOperationCanceledException f){
			Log.i("Cancel","facebook");
			LoginRadiusAuthManager.asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
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

			  FacebookPermission temp[] = FacebookPermission.values();
			   for (FacebookPermission findid : temp) {
				   if (findid.id==sPerm)
				   {
					   ProviderPermissions.addFbPermission(findid);
				   }
			   }
		   }
	   }
   }

   private static void OpenVkontakteSession(final Activity activity){
	   if(VKSdk.isLoggedIn()){
		   VKSdk.logout();
	   }
	   VKSdk.login(activity, VKScope.DIRECT,VKScope.EMAIL,VKScope.FRIENDS,VKScope.WALL,VKScope.STATUS,VKScope.PHOTOS,VKScope.VIDEO);
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
				LoginRadiusAuthManager.asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
			}

			@Override
			public void onError(FacebookException error) {
				LoginRadiusAuthManager.asyncHandler.onFailure(error, error.toString());
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


	/* Handling Facebook Operation Cancellation Event for native facebook login */
	public static void onNativeFailure(){
		   asyncHandler.onFailure(new Throwable("Facebook Operation cancelled"), "lr_LOGIN_CANCELLED");
	}

	/**
	 * Send Facebook token to LR server
	 * @param fbToken Token from facebook
	 * @param handler callback handler
	 */
	public static void getResponseFb(String fbToken, final AsyncHandler<AccessTokenResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("key",AKey);
		params.put("fb_access_token",fbToken);
		providerHandler(Endpoint.API_V2_ACCESS_TOKEN_FB, params, handler);
	}
	/**
	 * Send Google token to LR server
	 * @param googleToken Token from google
	 * @param handler callback handler
	 */
	public static void getResponseGoogle(String googleToken,boolean offline, final AsyncHandler<AccessTokenResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		if (offline){
			params.put("apikey",AKey);
			params.put("google_authcode",googleToken);
		}else {
			params.put("key",AKey);
			params.put("google_access_token",googleToken);
		}

		providerHandler(Endpoint.API_V2_ACCESS_TOKEN_GOOGLE, params, handler);
	}

	/**
	 * Send Vkontakte token to LR server
	 * @param vkontakteToken Token from Vkontakte
	 * @param handler callback handler
	 */
	public static void getResponseVkontakte(String vkontakteToken, final AsyncHandler<AccessTokenResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("key",AKey);
		params.put("vk_access_token",vkontakteToken);
		providerHandler(Endpoint.API_V2_ACCESS_TOKEN_VKONTAKTE, params, handler);
	}

	/**
	 * Send WeChat Code to LR server
	 * @param code from Wechat
	 * @param handler callback handler
	 */
	public static void getResponseWeChat(String code, final AsyncHandler<AccessTokenResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("key",LoginRadiusSDK.getApiKey());
		params.put("code",code);
		providerHandler(Endpoint.API_V2_ACCESS_TOKEN_WECHAT, params, handler);
	}

	/**
	 * Generic request
	 * @param uri Url for sending request
	 * @param params query parameters to be used
	 * @param handler callback handler
	 */
	public static void providerHandler(String uri, Map<String,String> params, final AsyncHandler<AccessTokenResponse> handler) {



		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getNativeLogin(uri,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<AccessTokenResponse>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(AccessTokenResponse response) {
						handler.onSuccess(response);
					}

				});
	}
}
