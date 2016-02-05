package com.loginradius.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.loginradius.sdk.api.StatusUpdateAPI;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.PostAPIResponse;
import com.loginradius.sdk.util.AsyncHandler;
import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusUpdate extends Activity 
{
    private Button submitB;
    private Button backB;
    public static lrAccessToken token=null;
    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_update);
		
		submitB=(Button)findViewById(R.id.lr_PostB);
		backB=(Button)findViewById(R.id.lr_Back);
         
	    final lrAccessToken newToken=token;
		final StatusUpdateAPI status=new StatusUpdateAPI();
		
		submitB.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				    try 
				    {
				    	status.setTitle(URLEncoder.encode(((EditText)findViewById(R.id.lr_title)).getText().toString().trim(),"utf-8"));
				    	status.setUrl(URLEncoder.encode(((EditText)findViewById(R.id.lr_url)).getText().toString().trim(),"utf-8"));
				    	status.setImageurl(URLEncoder.encode(((EditText)findViewById(R.id.lr_imageurl)).getText().toString().trim(),"utf-8"));
				    	status.setStatus(URLEncoder.encode(((EditText)findViewById(R.id.lr_status)).getText().toString().trim(),"utf-8"));
				    	status.setCaption(URLEncoder.encode(((EditText)findViewById(R.id.lr_caption)).getText().toString().trim(),"utf-8"));
				    	status.setDescription(URLEncoder.encode(((EditText)findViewById(R.id.lr_description)).getText().toString().trim(),"utf-8"));
					} 
				    catch (UnsupportedEncodingException e) 
				    {
						e.printStackTrace();
					}
				   
				    status.getResponse(newToken,new AsyncHandler<PostAPIResponse>() 
				    {
				    
					@Override
					public void onSuccess(PostAPIResponse data) {
						if(data.isPosted)
				    	{ 
							Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
						}
						
					}

					@Override
					public void onFailure(Throwable error, String errorcode) {
						
						
					}
				 });
			 }});
		
		backB.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			   startActivity(new Intent(StatusUpdate.this,MainActivity.class));	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.status_update, menu);
		return true;
	}
}
