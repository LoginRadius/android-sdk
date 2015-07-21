package com.loginradius.sdk.util;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder class used to attach imageview and textview in listview row object 
 *
 */
public class ViewHolder {	
	private ImageView imgView;
	private TextView textView;
	public ImageView getImgView() {
		return imgView;
	}
	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}
	public TextView getTextView() {
		return textView;
	}
	public void setTextView(TextView textView) {
		this.textView = textView;
	}
	
}
