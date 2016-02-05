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
import com.loginradius.sdk.api.LikeAPI;
import com.loginradius.sdk.models.like.LoginRadiusLike;
import com.loginradius.sdk.util.AsyncHandler;

public class LikeActivity extends Fragment {
	
	View rootview=null;
	public String liketext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textlike);
		txt.setText(Html.fromHtml(liketext));		
	}
	
	public void getlikedata(){
		LikeAPI like = new LikeAPI();
		like.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusLike[]>() {
			
			@Override
			public void onSuccess(LoginRadiusLike[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Like Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].Name+"<br>");
				}
				liketext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					liketext = "The Provider is not suported for the given Provider";
				}
				else
					liketext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static LikeActivity getInstance(){
		LikeActivity likeActivity = new LikeActivity();
	    
		return likeActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
			
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getlikedata();	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_like, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
