package com.loginradius.demo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loginradius.demo.R;
import com.loginradius.demo.UserProfileActivity;
import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.sdk.models.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.sdk.util.AsyncHandler;

public class UserActivity extends Fragment {
    
	public String username;
	public String iD;
	public String Provider;
	public String Birhdate;
	
	View rootview=null;
	
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.section_label);
		TextView txt2 = (TextView) this.getActivity().findViewById(R.id.textView1);
		TextView txt3 =  (TextView) this.getActivity().findViewById(R.id.textView2);
		TextView txt4 = (TextView) this.getActivity().findViewById(R.id.textView3);
		txt.setText(username);
		txt2.setText(iD);
		txt3.setText(Provider);
		txt4.setText(Birhdate);
	}
	
	public void getuserdata(){
		UserProfileAPI userProfile = new UserProfileAPI();
	   	userProfile.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
			
			@Override
			public void onSuccess(LoginRadiusUltimateUserProfile data) {
				username = "Name : "+data.FullName;
				iD = "ID : "+data.Provider;
				Provider = "Provider : "+data.BirthDate;
				Birhdate = "Birth date : "+data.ID;
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {				
			}
		});
	}
		
	public static UserActivity getInstance(){
		UserActivity userActivity = new UserActivity();
	    
		return userActivity;
	}
	@Override
	public void onAttach(Activity activity) {
	
		super.onAttach(activity);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getuserdata();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.user_profile_details, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
}
