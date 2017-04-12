package com.loginradius.androidsdk.helper;;

/**
 * Facebook permissions used to retrieve data
 * Permissions used in case of native login
 */
public enum FB_Permission {
	
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
	
	FB_Permission(String id, int type) {
		this.id=id;
		this.type=type;
	}
}
