package com.loginradius.demo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loginradius.demo.R;
import com.loginradius.demo.UserProfileActivity;
import com.loginradius.sdk.api.CheckInAPI;
import com.loginradius.sdk.models.checkin.LoginRadiusCheckIn;
import com.loginradius.sdk.util.AsyncHandler;

public class CheckInActivity extends Fragment {
	
	View rootview=null;
	public String checkintext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textcheckin);
		txt.setText(Html.fromHtml(checkintext));		
	}
	
	public void getcheckindata(){
		CheckInAPI checkin = new CheckInAPI();
		checkin.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusCheckIn[]>() {
			
			@Override
			public void onSuccess(LoginRadiusCheckIn[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("CheckIn Places are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].PlaceTitle+"<br>");
				}
				checkintext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {	
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					checkintext = "The Provider is not suported for the given Provider";
				}
				else
					checkintext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static CheckInActivity getInstance(){
		CheckInActivity checkinActivity = new CheckInActivity();
	    
		return checkinActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getcheckindata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_checkin, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
