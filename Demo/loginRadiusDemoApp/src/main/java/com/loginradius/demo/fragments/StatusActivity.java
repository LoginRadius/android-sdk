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
import com.loginradius.sdk.api.StatusAPI;
import com.loginradius.sdk.models.status.LoginRadiusStatus;
import com.loginradius.sdk.util.AsyncHandler;

public class StatusActivity extends Fragment {
	
	View rootview=null;
	public String statustext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textstatus);
		txt.setText(Html.fromHtml(statustext));		
	}
	
	public void getstatusdata(){
		StatusAPI status = new StatusAPI();
		status.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusStatus[]>() {
			
			@Override
			public void onSuccess(LoginRadiusStatus[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Status Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].text+"<br>");
				}
				statustext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {		
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					statustext = "The Provider is not suported for the given Provider";
				}
				else
					statustext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static StatusActivity getInstance(){
		StatusActivity statusActivity = new StatusActivity();
	    
		return statusActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getstatusdata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_status, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
