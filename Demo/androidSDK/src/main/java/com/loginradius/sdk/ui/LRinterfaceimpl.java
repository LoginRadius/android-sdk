package com.loginradius.sdk.ui;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.loginradius.sdk.R;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.util.AsyncHandler;

public class LRinterfaceimpl implements LoginRadiusInterface{
	
	/**
	 * Populate ListView with available providers, using default row layout
	 * @param appInfo AppInformation retrieved from LoginRadius server
	 * @param listview ListView provided where all the providers will be listed
	 * @param activity Activity of the ListView
	 * @param asyncHandler callback handler
	 */
	@Override
	public void attachListView(AppInformation appInfo, ListView listview,
			Activity activity, AsyncHandler<lrAccessToken> asyncHandler) {
		attachListView(appInfo, listview, activity, asyncHandler, R.layout.lr_row_layout);		
	}	
	
	/**
	 * Populate ListView with available providers
	 * @param appInfo AppInformation retrieved from LoginRadius server
	 * @param listview ListView provided where all the providers will be listed
	 * @param activity Activity of the ListView
	 * @param asyncHandler callback handler
	 * @param rowLayoutId Customized rowView of the list
	 */
	public void attachListView(final AppInformation appInfo, final ListView listview, 
			final Activity activity, final AsyncHandler<lrAccessToken> asyncHandler, int rowLayoutId) {

		/* Check parameters */
		if (asyncHandler == null) return;
		if (appInfo == null) { asyncHandler.onFailure(new Throwable("AppInfo is null"), "lr_NULL_PARAM");return;}
		if (listview == null) { asyncHandler.onFailure(new Throwable("View is null"), "lr_NULL_PARAM"); return;}
		if (activity == null) { asyncHandler.onFailure(new Throwable("Activity is null"), "lr_NULL_PARAM"); return;}
		
		/* Check Online status */
		if (!lrLoginManager.isOnline(activity.getApplicationContext())) {
			asyncHandler.onFailure(new Throwable("No internet connection"), "LoginRadius services require an internet connection");
			return;
		}
		
		/* Populate ListView */
		RowArrayAdapter adapter = new RowArrayAdapter(activity, rowLayoutId, appInfo.Providers,asyncHandler);
		listview.setAdapter(adapter);
		listview.setClickable(true);		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				lrLoginManager.performLogin(activity, appInfo.Providers.get(position), asyncHandler);
			}
		});

	}
	

}
