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
import com.loginradius.sdk.api.AlbumAPI;
import com.loginradius.sdk.models.album.LoginRadiusAlbum;
import com.loginradius.sdk.util.AsyncHandler;

public class AlbumActivity extends Fragment {
	
	View rootview=null;
	public String albumtext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textalbum);
		txt.setText(Html.fromHtml(albumtext));		
	}
	
	public void getalbumdata(){
		AlbumAPI album = new AlbumAPI();
		album.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusAlbum[]>() {
			
			@Override
			public void onSuccess(LoginRadiusAlbum[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Album Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].title+"<br>");
				}
				albumtext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {	
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					albumtext = "The Provider is not suported for the given Provider";
				}
				else
					albumtext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static AlbumActivity getInstance(){
		AlbumActivity albumActivity = new AlbumActivity();	    
		return albumActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
	}
	@Override
	public void onStart() {
		super.onStart();
		getalbumdata();	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {		
		super.onCreateView(inflater, container, savedInstanceState);		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_album, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}				
		return rootview;
	}	
	
	
}
