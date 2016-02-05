package com.loginradius.demo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> 
{
  private final Activity context;
  private final String[] web;
  public CustomList(Activity context,String[] web)
  {
	  super(context, R.layout.activity_event, web);
	  this.context = context;
	   this.web = web;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) 
  {
	  LayoutInflater inflater = context.getLayoutInflater();
	  View rowView= inflater.inflate(R.layout.activity_event, null, true);
	  TextView txtTitle = (TextView) rowView.findViewById(R.id.lr_defaults);
	  txtTitle.setText(web[position]);
	  return rowView;
  }
}
