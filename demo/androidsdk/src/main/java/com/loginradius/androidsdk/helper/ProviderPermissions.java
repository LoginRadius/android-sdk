package com.loginradius.androidsdk.helper;


import com.google.android.gms.common.Scopes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Contains sets of available Facebook Permissions
 *
 */
public class ProviderPermissions 
{
	/** Contains Needed Facebook Permissions - differentiates publish / read **/
	private static HashSet<FacebookPermission> FB_PUBLISH = new HashSet<FacebookPermission>();
	private static HashSet<FacebookPermission> FB_READ = new HashSet<FacebookPermission>();

	/**
	 * Add desired Facebook Permission. Automatically splits publish/read
	 * @param permissions permission from the FB_Permission class
	 */
	public static void addFbPermission(FacebookPermission... permissions) {

		for (FacebookPermission p : permissions) {
			if (p.type == FacebookPermission.READ)
				FB_READ.add(p);
			else
				FB_PUBLISH.add(p);
		}
	}
	/**
	 * Retrieve set publish permissions
	 * @return Publish Permission string
	 */
	public static String getFBPublishPermissions() {
		StringBuilder sB = new StringBuilder();

		// CSV
		for (FacebookPermission p : FB_PUBLISH) {
			if (sB.length()>0) {
				sB.append(",");
				sB.append(p.id); 
			} else
				sB.append(p.id);
		}
		return sB.toString();
	}

	public static List<String> getFBPublishPermissionsArr() {

		List<String> sPub= new ArrayList<String>();
		for (FacebookPermission p : FB_PUBLISH) {
			sPub.add(p.id);
		}
		return sPub;

	}
	/**
	 * Retrieve set read permissions
	 * @return Read Permission string
	 */
	public static String getFBReadPermissions() {
		StringBuilder sB = new StringBuilder();
		for (FacebookPermission p : FB_READ) {
			if (sB.length()>0) {
				sB.append(",");
				sB.append(p.id); 
			} else
				sB.append(p.id);
		}
		return sB.toString();
	}
	public static List<String> getFBReadPermissionsArr() {

		List<String> sRead= new ArrayList<String>();
		for (FacebookPermission p : FB_READ) {
				sRead.add(p.id);
		}
		return sRead;
	}

	/**
	 * Remove all publish/read permissions
	 */
	public static void resetPermissions() {
		FB_PUBLISH = new HashSet<FacebookPermission>();
		FB_READ = new HashSet<FacebookPermission>();
	}



	/**
	 * String used by Google native login
	 */
	public static String SCOPES = "oauth2:" + Scopes.PROFILE +
			" https://www.googleapis.com/auth/userinfo.profile " +
			"https://www.googleapis.com/auth/userinfo.email ";



	public enum GoogleScopes {

		USER_BASIC_INFO			("https://www.googleapis.com/auth/userinfo.profile"),
		USER_EMAIL				("https://www.googleapis.com/auth/userinfo.email"),
		USER_PHOTOS             ("https://picasaweb.google.com/data/"),
		USER_FEEDS				("https://www.google.com/m8/feeds"),
		USER_VIDEO			    ("https://www.googleapis.com/auth/youtube");
		public final String id;
		GoogleScopes(String id) {
			this.id=id;
		}
	}

	/**
	 * Facebook permissions used to retrieve data
	 * Permissions used in case of native login
	 */


	public enum FacebookPermission {

		USER_BASIC_INFO			("public_profile",			0),

		/* Basic User data permissions */
		USER_ABOUT				("user_about_me",		0),
		USER_BOOKS				("user_actions.books", 0),
		USER_FITNESS			("user_actions.fitness", 0),
		USER_MUSIC				("user_actions.music", 0),
		USER_NEWS				("user_actions.news", 0),
		USER_VIDEO				("user_actions.video", 0),
		USER_ACTIVITIES			("user_activities",		0),
		USER_BIRTHDAY			("user_birthday",		0),
		USER_EDUCATION			("user_education_history",		0),
		USER_EVENTS				("user_events",			0),
		USER_FRIEND_USING_APP   ("user_friends", 0),
		USER_GAMES_ACTIVITY     ("user_games_activity", 0),
		USER_GROUPS				("user_groups",			0),
		USER_HOMETOWN			("user_hometown",		0),
		USER_INTERESTS			("user_interests",		0),
		USER_LIKES				("user_likes",			0),
		USER_LOCATION			("user_location",		0),
		USER_PHOTOS				("user_photos",			0),
		USER_RELATIONSHIPS		("user_relationships",	0),
		USER_RELATIONSHIP_PREF	("user_relationship_details",	0),
		USER_RELIGION_POLITICS  ("user_religion_politics",0),
		USER_STATUS				("user_status",			0),
		USER_TAGGED_PLACES	    ("user_tagged_places", 0),
		USER_VIDEOS				("user_videos",			0),
		USER_WEBSITE			("user_website",		0),
		USER_WORK_HISTORY		("user_work_history",	0),

		/* Extended permissions */
		USER_EMAIL				("email",				0),
		INSIGHTS				("read_insights",		0),
		MAILBOX					("read_mailbox",		0),
		STREAM					("read_stream",			0),
		PAGE_MAILBOX			("read_page_mailboxes",0),
		FRIENDS					("read_friendlists",	0),
		ADS_MANAGEMENT			("ads_management",		1),
		ADS_READ				("ads_read", 0),
		MANAGE_NOTIFICATIONS	("manage_notifications",1),
		PUBLISH_ACTIONS			("publish_actions",		1),
		MANAGE_PAGES            ("manage_pages", 1),
		RSVP_EVENTS				("rsvp_events",			1);

		public static final int READ = 0;
		public static final int PUBLISH = 1;

		public final String id;
		public final int type;

		FacebookPermission(String id, int type) {
			this.id=id;
			this.type=type;
		}
	}
}



