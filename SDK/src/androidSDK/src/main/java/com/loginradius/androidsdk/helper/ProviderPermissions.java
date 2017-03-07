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
	private static HashSet<FB_Permission> FB_PUBLISH = new HashSet<FB_Permission>();
	private static HashSet<FB_Permission> FB_READ = new HashSet<FB_Permission>();

	/**
	 * Add desired Facebook Permission. Automatically splits publish/read
	 * @param permissions permission from the FB_Permission class
	 */
	public static void addFbPermission(FB_Permission... permissions) {

		for (FB_Permission p : permissions) {
			if (p.type == FB_Permission.READ)
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
		for (FB_Permission p : FB_PUBLISH) {
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
		for (FB_Permission p : FB_PUBLISH) {
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
		for (FB_Permission p : FB_READ) {
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
		for (FB_Permission p : FB_READ) {
				sRead.add(p.id);
		}
		return sRead;
	}

	/**
	 * Remove all publish/read permissions
	 */
	public static void resetPermissions() {
		FB_PUBLISH = new HashSet<FB_Permission>();
		FB_READ = new HashSet<FB_Permission>();
	}
	/**
	 * String used by Google native login
	 */
	public static String SCOPES = "oauth2:" + Scopes.PROFILE +
			" https://www.googleapis.com/auth/userinfo.profile " +
			"https://www.googleapis.com/auth/userinfo.email " +
			"https://www.google.com/m8/feeds/";
}
