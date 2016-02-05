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
import com.loginradius.sdk.api.ContactAPI;
import com.loginradius.sdk.models.LoginRadiusContactCursorResponse;
import com.loginradius.sdk.util.AsyncHandler;

public class ContactActivity extends Fragment {
	
	View rootview=null;
	public String contacttext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textcontact);
		txt.setText(Html.fromHtml(contacttext));		
	}
	
	public void getcontactdata(){
		ContactAPI contact = new ContactAPI();
		contact.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusContactCursorResponse>() {
			
			@Override
			public void onSuccess(LoginRadiusContactCursorResponse data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Contact Details are :<br>");
				for(int i=0;i<data.Data.size();i++){
					if(i>10) break;
					sb.append((i+1)+". "+data.Data.get(i).name+"<br>");
				}
				contacttext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					contacttext = "The Provider is not suported for the given Provider";
				}
				else
					contacttext = "Some error occurred while fetching data from server";
				updatedata();
			
			}
		});
	}
		
	public static ContactActivity getInstance(){
		ContactActivity contactActivity = new ContactActivity();
	    
		return contactActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getcontactdata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_contact, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
