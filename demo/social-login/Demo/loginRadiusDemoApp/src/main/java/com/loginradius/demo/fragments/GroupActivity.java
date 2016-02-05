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
import com.loginradius.sdk.api.GroupAPI;
import com.loginradius.sdk.models.group.LoginRadiusGroup;
import com.loginradius.sdk.util.AsyncHandler;

public class GroupActivity extends Fragment {
	
	View rootview=null;
	public String grouptext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textgroup);
		txt.setText(Html.fromHtml(grouptext));		
	}
	
	public void getgroupdata(){
		GroupAPI group = new GroupAPI();
		group.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusGroup[]>() {
			
			@Override
			public void onSuccess(LoginRadiusGroup[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Group Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].name+"<br>");
				}
				grouptext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					grouptext = "The Provider is not suported for the given Provider";
				}
				else
					grouptext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
		
	}
		
	public static GroupActivity getInstance(){
		GroupActivity groupActivity = new GroupActivity();	    
		return groupActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getgroupdata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_group, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
