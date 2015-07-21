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
import com.loginradius.sdk.api.PostAPI;
import com.loginradius.sdk.models.post.LoginRadiusPost;
import com.loginradius.sdk.util.AsyncHandler;

public class PostActivity extends Fragment {
	
	View rootview=null;
	public String posttext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textpost);
		txt.setText(Html.fromHtml(posttext));		
	}
	
	public void getpostdata(){
		PostAPI post = new PostAPI();
		post.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusPost[]>() {
			
			@Override
			public void onSuccess(LoginRadiusPost[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Post Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].name+"<br>");
				}
				posttext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					posttext = "The Provider is not suported for the given Provider";
				}
				else
					posttext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static PostActivity getInstance(){
		PostActivity postActivity = new PostActivity();
	    
		return postActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getpostdata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_post, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
