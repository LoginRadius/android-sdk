package com.loginradius.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.loginradius.demo.fragments.AlbumActivity;
import com.loginradius.demo.fragments.CheckInActivity;
import com.loginradius.demo.fragments.CompanyActivity;
import com.loginradius.demo.fragments.ContactActivity;
import com.loginradius.demo.fragments.EventActivity;
import com.loginradius.demo.fragments.GroupActivity;
import com.loginradius.demo.fragments.LikeActivity;
import com.loginradius.demo.fragments.MentionActivity;
import com.loginradius.demo.fragments.PageActivity;
import com.loginradius.demo.fragments.PhotoActivity;
import com.loginradius.demo.fragments.PostActivity;
import com.loginradius.demo.fragments.StatusActivity;
import com.loginradius.demo.fragments.UserActivity;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.Provider;

public class UserProfileActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	
	public static lrAccessToken token;
	public static Provider provider;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_profile_nav);
       
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		getTitle();
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch(position){
		case 0 : fragmentManager.
			           beginTransaction().
                         replace(R.id.container,UserActivity.getInstance()).commit();
		          break;
		case 1 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,ContactActivity.getInstance()).commit();
				  break;
		case 2 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,GroupActivity.getInstance()).commit();	
				  break;
		case 3 :  fragmentManager.
        				beginTransaction().
        					replace(R.id.container,EventActivity.getInstance()).commit();
				 break;
		case 4 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,PostActivity.getInstance()).commit();
				 break;
		case 5 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,StatusActivity.getInstance()).commit();
				 break;
		case 6 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,AlbumActivity.getInstance()).commit();
				  break;
		case 7 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,LikeActivity.getInstance()).commit();
				  break;
		case 8 : fragmentManager.
        				beginTransaction().
        					replace(R.id.container,CheckInActivity.getInstance()).commit();
				  break;
		case 9 : fragmentManager.
						beginTransaction().
							replace(R.id.container,CompanyActivity.getInstance()).commit();
				   break;
		case 10 : fragmentManager.
						beginTransaction().
							replace(R.id.container,MentionActivity.getInstance()).commit();
				   break;
		case 11 : fragmentManager.
						beginTransaction().
							replace(R.id.container,PhotoActivity.getInstance()).commit();
		           break;
		case 12 : fragmentManager.
						beginTransaction().
							replace(R.id.container,PageActivity.getInstance()).commit();
				   break;
		case 13 : UserProfileActivity.token =null;
				  this.finish();
				  break;		
		}	
	}
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.user_profile, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		UserProfileActivity.token =null;
		this.finish();
	}
	
	
	
}
