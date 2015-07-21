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
import com.loginradius.sdk.api.EventAPI;
import com.loginradius.sdk.models.event.LoginRadiusEvent;
import com.loginradius.sdk.util.AsyncHandler;

public class EventActivity extends Fragment {
	
	View rootview=null;
	public String eventtext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textevent);
		txt.setText(Html.fromHtml(eventtext));		
	}
	
	public void geteventdata(){
		EventAPI event = new EventAPI();
		event.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusEvent[]>() {
			
			@Override
			public void onSuccess(LoginRadiusEvent[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Event Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].Name+"<br>");
				}
				eventtext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {		
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					eventtext = "The Provider is not suported for the given Provider";
				}
				else
					eventtext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static EventActivity getInstance(){
		EventActivity eventActivity = new EventActivity();
	    
		return eventActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		geteventdata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_events, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
