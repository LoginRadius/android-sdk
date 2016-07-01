package com.loginradius.sdk.ui;

import android.app.Activity;
import android.widget.ListView;

import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.AppInformation;
import com.loginradius.sdk.util.AsyncHandler;

/**
 * LoginRadius view interface. Allows ListView login attachments
 * Implement this to create own implementation of listview
 */
public interface LoginRadiusInterface {

	public void attachListView(final AppInformation appInfo, final ListView listview, 
			final Activity activity, AsyncHandler<lrAccessToken> asyncHandler);

	
	
	

}
