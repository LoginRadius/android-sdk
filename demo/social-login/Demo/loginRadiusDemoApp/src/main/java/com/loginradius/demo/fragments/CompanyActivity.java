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
import com.loginradius.sdk.api.CompanyAPI;
import com.loginradius.sdk.models.company.LoginRadiusCompany;
import com.loginradius.sdk.util.AsyncHandler;

public class CompanyActivity extends Fragment {
	
	View rootview=null;
	public String companytext;
	public void updatedata(){
		TextView txt = (TextView)this.getActivity().findViewById(R.id.textcompany);
		txt.setText(Html.fromHtml(companytext));		
	}
	
	public void getcompanydata(){
		CompanyAPI company = new CompanyAPI();
		company.getResponse(UserProfileActivity.token, new AsyncHandler<LoginRadiusCompany[]>() {
			
			@Override
			public void onSuccess(LoginRadiusCompany[] data) {
				StringBuilder sb = new StringBuilder();
				sb.append("Company Details are :<br>");
				for(int i=0;i<data.length;i++){
					sb.append((i+1)+". "+data[i].name+"<br>");
				}
				companytext = sb.toString();
				updatedata();
			}
			
			@Override
			public void onFailure(Throwable error, String errorcode) {			
				if(errorcode.equals("lr_API_NOT_SUPPORTED") ){
					companytext = "The Provider is not suported for the given Provider";
				}
				else
					companytext = "Some error occurred while fetching data from server";
				updatedata();
			}
		});
	}
		
	public static CompanyActivity getInstance(){
		CompanyActivity companyActivity = new CompanyActivity();
	    
		return companyActivity;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		getcompanydata();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		if(rootview ==null)
		     rootview = (View) inflater.inflate(R.layout.activity_company, container,false);
		else {
			ViewGroup parent = (ViewGroup)rootview.getParent();
			parent.removeView(rootview);
		}
				
		return rootview;
	}	
	
	
}
