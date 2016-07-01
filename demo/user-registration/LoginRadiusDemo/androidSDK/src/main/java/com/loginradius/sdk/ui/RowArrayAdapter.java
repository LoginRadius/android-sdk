package com.loginradius.sdk.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loginradius.sdk.R;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.Provider;
import com.loginradius.sdk.util.AsyncHandler;
import com.loginradius.sdk.util.ViewHolder;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Used by LoginRadiusInterface to provide default layout for populated listview
 *
 */
public class RowArrayAdapter extends ArrayAdapter<Provider> {
	
	private Context context;
	private Provider[] values;
	private int layoutResourceID;
	private AsyncHandler<lrAccessToken> asyncHandler;
	private LayoutInflater inflater;
	
	public RowArrayAdapter(Context context, int layoutResourceID, List<Provider> values, AsyncHandler<lrAccessToken> handler) {
		super(context, layoutResourceID, values);
		
		this.context = context;
		this.layoutResourceID = layoutResourceID;
		this.values = values.toArray(new Provider[values.size()]);
		this.asyncHandler = handler;
		
	}
	
	@SuppressLint("ViewHolder") public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
		
		View rowView = convertView;
		if(inflater==null)
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(rowView ==null){
		    rowView	= inflater.inflate(layoutResourceID, null, true);
		    viewHolder = new ViewHolder();
		    viewHolder.setImgView((ImageView)rowView.findViewById(R.id.lr_icon));
		    viewHolder.setTextView((TextView)rowView.findViewById(R.id.lr_title));
		    rowView.setTag(viewHolder);		    
		}
		else {
			viewHolder =(ViewHolder)rowView.getTag();
		}
		Provider provider = values[position];
	    viewHolder.getTextView().setText(provider.Name);
	    viewHolder.getImgView().getLayoutParams().height = context.getResources().getInteger(R.integer.icon_height);
	    viewHolder.getImgView().getLayoutParams().width = context.getResources().getInteger(R.integer.icon_width);
		if(lrLoginManager.ImageUrl!=null){
		    getAndroidDrawable(provider.Name,context,asyncHandler,viewHolder.getImgView());		     
		} else {
			viewHolder.getImgView().setImageResource(getstaticAndroidDrawable(provider.Name));	
		}	
		return rowView;
	}
	
	/**
	 * Returns drawable resource 
	 * @param pDrawableName provider name
	 * @param c Application context
	 * @param asynchandler callback handler
	 * @param bitmap Imageview object where the bitmap is to be set
	 * @return
	 */
	
	private static void getAndroidDrawable(final String pDrawableName,Context c,final AsyncHandler<lrAccessToken> asynchandler,ImageView bitmap)
	{
		File f = c.getFileStreamPath(lrLoginManager.ImgVersion+"_lr_"+pDrawableName.toLowerCase()+".png");
		try {
		if(!f.exists()){
			   //Download the image
			   if(!ImageLoader.getInstance().isInited())
			   InitImageLoader(c);
			   GetImageFromUrl("lr_"+pDrawableName.toLowerCase(),bitmap,c,asynchandler);
			   return;
		}
		    else {
			//Get the file from memory
			FileInputStream fin;
			fin = c.openFileInput(lrLoginManager.ImgVersion+"_lr_"+pDrawableName.toLowerCase()+".png");
			bitmap.setImageBitmap(BitmapFactory.decodeStream(fin));
			fin.close();
			return; 
		    }
	    } catch (FileNotFoundException e) {
		     e.printStackTrace();
		     asynchandler.onFailure(new Throwable(e), "lr_icon_not_found");
		     return;
	    }
		catch(Exception e){
			asynchandler.onFailure(new Throwable(e), "lr_exception_found");
			return;
		}
		
	}
	/**
	 * Get images from the given url
	 * @param pDrawableName provider name
	 * @param image Imageview object where the bitmap is to be set
	 * @param c Application context
	 * @param asynchandler callback handler
	 * @return
	 */
	
	  private static void GetImageFromUrl(final String pDrawableName,final ImageView image,final Context c,final AsyncHandler<lrAccessToken> asynchandler ) {
		  ImageLoader.getInstance().displayImage(lrLoginManager.ImageUrl+pDrawableName+".png", image, new ImageLoadingListener() {
			
			  @Override
				public void onLoadingStarted(String arg0, View arg1) {
					image.setImageResource(com.loginradius.sdk.R.drawable.loader);
					
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					
					
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap finalImage) {
					try{
					FileOutputStream fout = c.openFileOutput(lrLoginManager.ImgVersion+"_"+pDrawableName+".png", Context.MODE_PRIVATE);
					finalImage.compress(CompressFormat.JPEG, 100, fout);
					fout.close();		
					}
					catch(Exception e){						
						asynchandler.onFailure(new Throwable(e), "lr_icon_loading_error");
					}
					
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					
					
				}
			}); 
	 }
	  /**
	   * Used to initiate UniversalImageLoader
	   * @param c Application context
	   */
     private static  void InitImageLoader(Context c){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) 
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
        ImageLoader.getInstance().init(config);
        
        new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .build();
        
	}
     /**
      * If the Url is null then static images are picked from resources
      * @param pDrawableName provider name
      * @return drawable resource id
      */
	public static int getstaticAndroidDrawable(String pDrawableName) 
	{
		if (pDrawableName.equalsIgnoreCase("facebook")) return com.loginradius.sdk.R.drawable.lr_facebook;
		else if (pDrawableName.equalsIgnoreCase("Twitter")) return com.loginradius.sdk.R.drawable.lr_twitter;
		else if (pDrawableName.equalsIgnoreCase("Odnoklassniki")) return com.loginradius.sdk.R.drawable.lr_odnoklassniki;
		else if (pDrawableName.equalsIgnoreCase("Amazon")) return com.loginradius.sdk.R.drawable.lr_amazon;
		else if (pDrawableName.equalsIgnoreCase("Salesforce")) return com.loginradius.sdk.R.drawable.lr_salesforce;
		else if (pDrawableName.equalsIgnoreCase("Steamcommunity")) return com.loginradius.sdk.R.drawable.lr_steamcommunity;
		else if (pDrawableName.equalsIgnoreCase("Paypal")) return com.loginradius.sdk.R.drawable.lr_paypal;
		else if (pDrawableName.equalsIgnoreCase("Google")) return com.loginradius.sdk.R.drawable.lr_google;
		else if (pDrawableName.equalsIgnoreCase("LinkedIn")) return com.loginradius.sdk.R.drawable.lr_linkedin;
		else if (pDrawableName.equalsIgnoreCase("Yahoo")) return com.loginradius.sdk.R.drawable.lr_yahoo;
		else if (pDrawableName.equalsIgnoreCase("aol")) return com.loginradius.sdk.R.drawable.lr_aol;
		else if (pDrawableName.equalsIgnoreCase("hyves")) return com.loginradius.sdk.R.drawable.lr_hyves;
		else if (pDrawableName.equalsIgnoreCase("live")) return com.loginradius.sdk.R.drawable.lr_live;
		else if (pDrawableName.equalsIgnoreCase("persona")) return com.loginradius.sdk.R.drawable.lr_persona;
		else if (pDrawableName.equalsIgnoreCase("Foursquare")) return com.loginradius.sdk.R.drawable.lr_foursquare;
		else if (pDrawableName.equalsIgnoreCase("vkontakte")) return com.loginradius.sdk.R.drawable.lr_vkontakte;
		else if (pDrawableName.equalsIgnoreCase("odno")) return com.loginradius.sdk.R.drawable.lr_vkontakte;
		else if (pDrawableName.equalsIgnoreCase("Livejournal")) return com.loginradius.sdk.R.drawable.lr_livejournal;
		else if (pDrawableName.equalsIgnoreCase("mixi")) return com.loginradius.sdk.R.drawable.lr_mixi;
		else if (pDrawableName.equalsIgnoreCase("MyOpenId")) return com.loginradius.sdk.R.drawable.lr_myopenid;
		else if (pDrawableName.equalsIgnoreCase("myspace")) return com.loginradius.sdk.R.drawable.lr_myspace;
		else if (pDrawableName.equalsIgnoreCase("openid")) return com.loginradius.sdk.R.drawable.lr_openid;
		else if (pDrawableName.equalsIgnoreCase("orange")) return com.loginradius.sdk.R.drawable.lr_orange;
		else if (pDrawableName.equalsIgnoreCase("stackexchange"))return com.loginradius.sdk.R.drawable.lr_stackexchange;
		else if (pDrawableName.equalsIgnoreCase("verisign"))return com.loginradius.sdk.R.drawable.lr_verisign;
		else if (pDrawableName.equalsIgnoreCase("virgilio"))return com.loginradius.sdk.R.drawable.lr_virgilio;
		else if (pDrawableName.equalsIgnoreCase("wordpress"))return com.loginradius.sdk.R.drawable.lr_wordpress;
		else if (pDrawableName.equalsIgnoreCase("Github"))return com.loginradius.sdk.R.drawable.lr_github;
		else if (pDrawableName.equalsIgnoreCase("QQ"))return com.loginradius.sdk.R.drawable.lr_qq;
		else if (pDrawableName.equalsIgnoreCase("Kaixin"))return com.loginradius.sdk.R.drawable.lr_kaixin;
		else if (pDrawableName.equalsIgnoreCase("Renren"))return com.loginradius.sdk.R.drawable.lr_renren;
		else return com.loginradius.sdk.R.drawable.loader;
		
	}

}
