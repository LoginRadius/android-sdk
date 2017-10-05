package com.loginradius.androidsdk.resource;



/**
 * Object for setting LoginRadius end point into requesting urls
 *
 *
 */
public class Endpoint {


	/**
	 * Creates url after appending LoginRadius api endpoint
	 * @return complete url for fetching data
	 */


	public static final String API_V2_SOCIALINTERFACE_URL = "interface/json/";
	public static final String API_V2_RAASINTERFACE_URL = "raas/regSchema/";
	public static final String API_V2_LOGIN = "identity/v2/auth/login";
	public static final String API_V2_REGISTER = "identity/v2/auth/register";
	public static final String API_V2_FORGOTPASSWORD_EMAIL = "identity/v2/auth/password";
	public static final String API_V2_FORGOTPASSWORD_PHONE = "identity/v2/auth/password/otp";
	public static final String API_V2_VERIFY_OTP = "identity/v2/auth/phone/otp";
	public static final String API_V2_UPDATE_PHONE = "identity/v2/auth/phone";
	public static final String API_V2_PHONESENDOTPAPI="identity/v2/auth/login/otp";
	public static final String API_V2_UPDATE_PROFILE="identity/v2/auth/account";
	public static final String API_V2_ADD_EMAIL="identity/v2/auth/email";
	public static final String API_V2_SOCIALIDENTITIES="identity/v2/auth/socialIdentity";
	public static final String API_V2_CUSTOMOBJECT="identity/v2/auth/customobject";
	public static final String API_V2_ALBUM = "api/v2/album";
	public static final String API_V2_AUDIO = "api/v2/audio";
	public static final String API_V2_CHECKIN = "api/v2/checkin";
	public static final String API_V2_COMPANY = "api/v2/company";
	public static final String API_V2_CONTACT = "api/v2/contact";
	public static final String API_V2_EVENT = "api/v2/event";
	public static final String API_V2_FOLLOWING = "api/v2/following";
	public static final String API_V2_GROUP = "api/v2/group";
	public static final String API_V2_LIKE = "api/v2/like";
	public static final String API_V2_MENTION = "api/v2/mention";
	public static final String API_V2_MESSAGE = "api/v2/message";
	public static final String API_V2_PAGE = "api/v2/page";
	public static final String API_V2_PHOTO = "api/v2/photo";
	public static final String API_V2_POST = "api/v2/post";
	public static final String API_V2_STATUS = "api/v2/status";
	public static final String API_V2_USERPROFILE = "identity/v2/auth/account";
	public static final String API_V2_VIDEO = "api/v2/video";
	public static final String API_V2_ACCESS_TOKEN_FB = "api/v2/access_token/facebook";
	public static final String API_V2_ACCESS_TOKEN_GOOGLE = "api/v2/access_token/google";
	public static final String API_V2_EMAIL_PROMPT_AUTO_LOGIN="identity/v2/auth/login/autologin";
	public static final String API_V2_VERIFY_AUTO_LOGIN_EMAIL="identity/v2/auth/email/autologin";
	public static final String API_V2_VALIDATE_ACCESS_TOKEN="identity/v2/auth/access_token/validate";
	public static final String API_V2_INVALIDATE_ACCESS_TOKEN="identity/v2/auth/access_token/invalidate";
	public static final String API_V2_SIMPLIFIED_REGISTRATION="identity/v2/auth/noregistration";
	public static final String API_V2_GET_SECURITY_QUESTIONS="identity/v2/auth/securityquestion";
	public static final String API_V2_RESET_PASSWORD_BY_SECURITY_QUESTION="identity/v2/auth/password/securityanswer";

	public static final String SHAREDPREFERENCEFILEKEY = "com.loginradius.loginradiusraas.PREFERENCE_FILE_KEY";
	public static final String webviewlogin=".hub.loginradius.com/RequestHandlor.aspx";


}
