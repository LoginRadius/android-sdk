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
import com.loginradius.sdk.api.PhotoAPI;
import com.loginradius.sdk.models.photo.LoginRadiusPhoto;
import com.loginradius.sdk.util.AsyncHandler;

public class PhotoActivity extends Fragment {
	
	View rootview=null;
	public String phototext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textphoto);
		txt.setText(Html.fromHtml(phototext));		
	}
	
	public void getphotodata(){
		PhotoAPI photo = new PhotoAPI();
		photo.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusPhoto[]>() {
			
			@Override
			public void onSuccess(LoginRadiusPhoto[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Photo Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].Name+" URL: "+data[i].Link+"<br>");
				}
				phototext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {	
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					phototext = "The Provider is not suported for the given Provider";
				}
				else
					phototext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static PhotoActivity getInstance(){
		PhotoActivity photoActivity = new PhotoActivity();	    
		return photoActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getphotodata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_photo, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
