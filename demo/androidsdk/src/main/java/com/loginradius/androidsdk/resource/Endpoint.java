package com.loginradius.androidsdk.resource;

import java.util.Map;

/**
 * Object for setting LoginRadius end point into requesting urls
 * 
 *
 */
public class Endpoint
{
	private static final String LOGINRADIUS_API_ROOT = "https://api.loginradius.com";
	private static final String LOGINRADIUS_SOCIALINTERFACE_URL = "http://cdn.loginradius.com/interface/json/";
	private static final String LOGINRADIUS_RAASINTERFACE_URL = "http://cdn.loginradius.com/raas/regSchema/";
	private static final String API_V2_LOGIN = "/identity/v2/auth/login";
	private static final String API_REGISTER = "/identity/v2/auth/register";
	private static final String API_FORGOTPASSWORDEamil = "/identity/v2/auth/password";
	private static final String API_FORGOTPASSWORDMobile = "/identity/v2/auth/password/otp";
	private static final String LOGINRADIUS_verifyotp = "/identity/v2/auth/phone/otp";
	private static final String LOGINRADIUS_updatephone = "/identity/v2/auth/phone";
	private static final String LOGINRADIUS_updateprofile="/identity/v2/auth/account";
	private  static final String LOGINRADIUSU_AddEmail="/identity/v2/auth/email";
	private  static final String LOGINRADIUSU_SocialIdentities="/identity/v2/auth/socialIdentity";
	private  static final String LOGINRADIUSU_CustomObject="/identity/v2/auth/customobject";
	private static final String API_V2_ALBUM = "/api/v2/album";
	private static final String API_V2_AUDIO = "/api/v2/audio";
	private static final String API_V2_CHECKIN = "/api/v2/checkin";
	private static final String API_V2_COMPANY = "/api/v2/company";
	private static final String API_V2_CONTACT = "/api/v2/contact";
	private static final String API_V2_EVENT = "/api/v2/event";
	private static final String API_V2_FOLLOWING = "/api/v2/following";
	private static final String API_V2_GROUP = "/api/v2/group";
	private static final String API_V2_LIKE = "/api/v2/like";
	private static final String API_V2_MENTION = "/api/v2/mention";
	private static final String API_V2_MESSAGE = "/api/v2/message";
	private static final String API_V2_PAGE = "/api/v2/page";
	private static final String API_V2_PHOTO = "/api/v2/photo";
	private static final String API_V2_POST = "/api/v2/post";
	private static final String API_V2_STATUS = "/api/v2/status";
	private static final String API_V2_USERPROFILE = "/identity/v2/auth/account";
	private static final String API_V2_VIDEO = "/api/v2/video";
	private static final String FACEBOOK_PERMISSION = "/api/v2/app/jsinterface";
	private static final String API_V2_ACCESS_TOKEN_FB = "/api/v2/access_token/facebook";
	private static final String API_V2_ACCESS_TOKEN_GOOGLE = "/api/v2/access_token/google";
	public static final String SHAREDPREFERENCEFILEKEY = "com.loginradius.loginradiusraas.PREFERENCE_FILE_KEY";
	public static final String webviewlogin=".hub.loginradius.com/RequestHandlor.aspx";
	/**
	 * Creates url after appending LoginRadius api root url

	 * @return complete url for fetching data
	 */
	public static String getV2_VIDEO() {
		return LOGINRADIUS_API_ROOT+API_V2_VIDEO;
	}
	public static String getV2_USERPROFILE() {
		return LOGINRADIUS_API_ROOT+API_V2_USERPROFILE;
	}
	public static String getV2_STATUS() {
		return LOGINRADIUS_API_ROOT+API_V2_STATUS;
	}
	public static String getV2_POST() {
		return LOGINRADIUS_API_ROOT+API_V2_POST;
	}
	public static String getV2_PHOTO() {
		return LOGINRADIUS_API_ROOT+API_V2_PHOTO;
	}
	public static String getV2_PAGE() {
		return LOGINRADIUS_API_ROOT+API_V2_PAGE;
	}
	public static String getV2_MESSAGE() {
		return LOGINRADIUS_API_ROOT+API_V2_MESSAGE;
	}
	public static String getV2_MENTION() {
		return LOGINRADIUS_API_ROOT+API_V2_MENTION;
	}
	public static String getV2_LIKE() {
		return LOGINRADIUS_API_ROOT+API_V2_LIKE;
	}
	public static String getV2_GROUP() {
		return LOGINRADIUS_API_ROOT+API_V2_GROUP;
	}
	public static String getV2_FOLLOWING() {
		return LOGINRADIUS_API_ROOT+API_V2_FOLLOWING;
	}
	public static String getV2_EVENT() {
		return LOGINRADIUS_API_ROOT+API_V2_EVENT;
	}
	public static String getV2_CONTACT() {
		return LOGINRADIUS_API_ROOT+API_V2_CONTACT;
	}
	public static String getV2_COMPANY() {
		return LOGINRADIUS_API_ROOT+API_V2_COMPANY;
	}
	public static String getV2_ALBUM() {
		return LOGINRADIUS_API_ROOT+API_V2_ALBUM;
	}
	public static String getV2_CHECKIN() {
		return LOGINRADIUS_API_ROOT+API_V2_CHECKIN;
	}
	public static String getV2_AUDIO() {
		return LOGINRADIUS_API_ROOT+API_V2_AUDIO;
	}
	public static String getLoginUrl() {
		return LOGINRADIUS_API_ROOT+API_V2_LOGIN;
	}



	public static String getCustomObjectUrl() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUSU_CustomObject;
	}

	public static String getUpdateProfileUrl() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUS_updateprofile;
	}

	public static String getRegistrationUrl() {
		return LOGINRADIUS_API_ROOT+API_REGISTER;
	}

	public static String getVerifyotpUrl() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUS_verifyotp;
	}

	public static String getUpdatephoneUrl() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUS_updatephone;
	}

	public static String getAddEmailUrl() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUSU_AddEmail;
	}


	public static String getForgotPasswordUrlEmail() {
		return LOGINRADIUS_API_ROOT+API_FORGOTPASSWORDEamil;
	}
	public static String getForgotPasswordUrlMobile() {
		return LOGINRADIUS_API_ROOT+API_FORGOTPASSWORDMobile;
	}

	public static String getSocialInterfaceUrl(String apiKey) {
		return LOGINRADIUS_SOCIALINTERFACE_URL+apiKey+".json";
	}

	public static String getRegisterInterfaceUrl(String apiKey) {
		return LOGINRADIUS_RAASINTERFACE_URL+apiKey+".json";
	}


	public static String getSocialIdentities() {
		return LOGINRADIUS_API_ROOT+LOGINRADIUSU_SocialIdentities;
	}

	public static String getFacebookPermissionfornative() {
		return LOGINRADIUS_API_ROOT+FACEBOOK_PERMISSION;
	}

	public static String getFacebookToken_Native() {
		return LOGINRADIUS_API_ROOT+API_V2_ACCESS_TOKEN_FB;
	}
	public static String getGoogleToken_Native() {
		return LOGINRADIUS_API_ROOT+API_V2_ACCESS_TOKEN_GOOGLE;
	}
	/**
	 * Creates url after appending loginradius api root url and query parameters
	 * @param url url for appending to the api url
	 * @param queryArgs extra parameters for sending with url
	 * @return complete url for fetching data
	 */
	public static String GetRequestUrl(String url, Map<String, String> queryArgs)
	{
		String keyvalueString = createKeyValueString(queryArgs);
		if (url.contains("?")) return url + "&" + keyvalueString;

		return url + "?" + keyvalueString;
	}

	/**
	 * Creates key-value string
	 * @param queryArgs parameters that will attach to the url
	 * @return query string with the given parameters
	 */
	public static String createKeyValueString(Map<String, String> queryArgs)
	{
		if (queryArgs != null) 
		{
			String[] sb = new String[queryArgs.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : queryArgs.entrySet())
			{
				sb[i] = entry.getKey() + "=" + entry.getValue();
				i++;
			}
			return combine(sb, "&");
		} 
		else return null;
	}

	/**
	 * Combine to create key-value string
	 * @param s Array String where the glue will be appended
	 * @param glue String to be appended with Array String
	 * @return appended String
	 */
	public static String combine(String[] s, String glue)
	{
		int k = s.length;
		if (k == 0)return null;
		StringBuilder out = new StringBuilder();
		out.append(s[0]);
		for (int x = 1; x < k; ++x) out.append(glue).append(s[x]);
		return out.toString();
	}
}
