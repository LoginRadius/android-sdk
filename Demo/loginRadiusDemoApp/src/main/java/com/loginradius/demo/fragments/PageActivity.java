package com.loginradius.demo.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loginradius.demo.R;
import com.loginradius.demo.UserProfileActivity;
import com.loginradius.sdk.api.PageAPI;
import com.loginradius.sdk.models.page.LoginRadiusPage;
import com.loginradius.sdk.util.AsyncHandler;

public class PageActivity extends Fragment {
	
	View rootview=null;
	AlertDialog alert=null;
	public String pagetext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textpage);
		txt.setText(Html.fromHtml(pagetext));		
	}
	
	public void getpagedata(String text){
		PageAPI page = new PageAPI();
		page.setPageName(text);
		page.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusPage>() {
			
			@Override
			public void onSuccess(LoginRadiusPage data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Page Details are :<br>");
				sb.append("Name : "+data.Name+" Category : "+data.Category+"<br>");
				pagetext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {		
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					pagetext = "The Provider is not suported for the given Provider";
				}
				else
					pagetext = "Some error occurred while fetching data from server<br>Please check " +
							   "for more details<br>"+errorcode;
				updatedata();
			}
		});
	}
		
	public static PageActivity getInstance(){
		PageActivity pageActivity = new PageActivity();
		return pageActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
	}
	public void setcancelevent(){
		pagetext = "You have not entered the page name. Please click on " +
				    "Page from the sidebar again to continue.";
		updatedata();
	}
	public void getpagename() {
		if(alert==null){
		alert = new AlertDialog.Builder(PageActivity.this.getActivity()).create();
		
        alert.setTitle("Page Name");
		alert.setMessage("Please Enter Page Name");
		
		
		final EditText input = new EditText(PageActivity.this.getActivity());
		input.setText("Page 1");
    	alert.setView(input);
    	alert.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(input.getText().equals(""))
				    Toast.makeText(PageActivity.this.getActivity(), "Please enter valid page name", Toast.LENGTH_SHORT).show();
				else {
					PageActivity.this.getpagedata(input.getText().toString());					
				}
			}
		});
    	alert.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				PageActivity.this.setcancelevent();
				
			}
		});
    	alert.setCancelable(false);
    	alert.setCanceledOnTouchOutside(false);
    	alert.show();
		}   	
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getpagename();	
	}	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	 }	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_page, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
