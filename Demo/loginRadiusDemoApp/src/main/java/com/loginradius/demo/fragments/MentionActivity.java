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
import com.loginradius.sdk.api.MentionAPI;
import com.loginradius.sdk.models.mention.LoginRadiusMention;
import com.loginradius.sdk.util.AsyncHandler;

public class MentionActivity extends Fragment {
	
	View rootview=null;
	public String mentiontext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textmention);
		txt.setText(Html.fromHtml(mentiontext));		
	}
	
	public void getmentiondata(){
		MentionAPI mention= new MentionAPI();
		mention.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusMention[]>() {
			
			@Override
			public void onSuccess(LoginRadiusMention[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Mention Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].name+"<br>");
				}
				mentiontext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {		
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					mentiontext = "The Provider is not suported for the given Provider";
				}
				else
					mentiontext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static MentionActivity getInstance(){
		MentionActivity mentionActivity = new MentionActivity();
	    
		return mentionActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);				
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getmentiondata();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null){
		     rootview = (View) inflater.inflate(R.layout.activity_mention, container,false);
		}
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
		
		return rootview;
	}	
	
	
}
