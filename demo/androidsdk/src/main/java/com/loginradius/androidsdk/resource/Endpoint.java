package com.loginradius.androidsdk.resource;



/**
 * Object for setting LoginRadiusSDK end point into requesting urls
 *
 *
 */
public class Endpoint {


	/**
	 * Creates url after appending LoginRadiusSDK api endpoint
	 */

	public static final String API_V2_CONFIG = "ciam/appInfo";
	public static final String API_V2_LOGIN = "identity/v2/auth/login";
	public static final String API_V2_REGISTER = "identity/v2/auth/register";
	public static final String API_V2_RESET_PASSWORD="identity/v2/auth/password/reset";
	public static final String API_V2_CHANGE_PASSWORD="identity/v2/auth/password/change";
	public static final String API_V2_FORGOTPASSWORD_EMAIL = "identity/v2/auth/password";
	public static final String API_V2_FORGOTPASSWORD_PHONE = "identity/v2/auth/password/otp";
	public static final String API_V2_VERIFY_OTP = "identity/v2/auth/phone/otp";
	public static final String API_V2_UPDATE_PHONE = "identity/v2/auth/phone";
	public static final String API_V2_PHONESENDOTPAPI="identity/v2/auth/login/otp";
	public static final String API_V2_UPDATE_PROFILE="identity/v2/auth/account";
	public static final String API_V2_ADD_EMAIL="identity/v2/auth/email";
	public static final String API_V2_VERIFY_EMAIL = "identity/v2/auth/email";
	public static final String API_V2_SOCIALIDENTITIES="identity/v2/auth/socialIdentity";
	public static final String API_V2_CUSTOMOBJECT="identity/v2/auth/customobject";
	public static final String API_V2_VERIFY_USERNAME="identity/v2/auth/username";
	public static final String API_V2_DELETE_ACCOUNT="identity/v2/auth/account/delete";
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
	public static final String API_V2_ACCESS_TOKEN_VKONTAKTE="/api/v2/access_token/vkontakte";
	public static final String API_V2_ACCESS_TOKEN_TWITTER= "/api/v2/access_token/twitter";
	public static final String API_V2_ACCESS_TOKEN_WECHAT= "/api/v2/access_token/wechat";
	public static final String API_V2_SMART_LOGIN="identity/v2/auth/login/smartlogin";
	public static final String API_V2_VERIFY_SMART_LOGIN="identity/v2/auth/email/smartlogin";
	public static final String API_V2_VALIDATE_ACCESS_TOKEN="identity/v2/auth/access_token/validate";
	public static final String API_V2_INVALIDATE_ACCESS_TOKEN="identity/v2/auth/access_token/invalidate";
	public static final String API_V2_ONE_TOUCH_LOGIN="identity/v2/auth/onetouchlogin";
	public static final String API_V2_GET_SECURITY_QUESTIONS="identity/v2/auth/securityquestion";
	public static final String API_V2_RESET_PASSWORD_BY_SECURITY_QUESTION="identity/v2/auth/password/securityanswer";
	public static final String API_V2_PASSWORDLESSLOGIN ="identity/v2/auth/login/passwordlesslogin/email";
	public static final String API_V2_PASSWORDLESSVERIFY ="identity/v2/auth/login/passwordlesslogin/email/verify";
	public static final String API_V2_PASSWORDLESSVERIFYOTP ="identity/v2/auth/login/passwordlesslogin/otp/verify";
	public static final String API_V2_PASSWORDLESSSENDOTP="identity/v2/auth/login/passwordlesslogin/otp";
	public static final String API_V2_ACCEPT_PRIVACY_POLICY="identity/v2/auth/privacypolicy/accept";
	public static final String API_V2_SEND_WELCOME_EMAIL="identity/v2/auth/account/sendwelcomeemail";
	public static final String API_V2_VERIFY_URL = "https://auth.lrcontent.com/mobile/verification/index.html";
	public static final String API_V2_PIN_LOGIN="identity/v2/auth/login/pin";
	public static final String API_V2_PIN_BY_PINAuth="identity/v2/auth/pin/set/pinauthtoken";
	public static final String API_V2_PIN_FORGOT_BY_EMAIL="identity/v2/auth/pin/forgot/email";
	public static final String API_V2_PIN_FORGOT_BY_PHONE="identity/v2/auth/pin/forgot/otp";
	public static final String API_V2_PIN_FORGOT_BY_USERNAME="identity/v2/auth/pin/forgot/username";
	public static final String API_V2_PIN_INVALIDATE_SESSION_TOKEN="identity/v2/auth/session_token/InValidate";
	public static final String API_V2_PIN_RESET_EMAIL_OTP="identity/v2/auth/pin/reset/otp/email";
	public static final String API_V2_PIN_RESET_PHONE_OTP="identity/v2/auth/pin/reset/otp/phone";
	public static final String API_V2_PIN_RESET_USERNAME_OTP="identity/v2/auth/pin/reset/otp/username";
	public static final String API_V2_PIN_RESET_BY_RESET_TOKEN="identity/v2/auth/pin/reset/token";
	public static final String API_V2_PIN_RESET_EMAIL_SECURITY_QUESTION="identity/v2/auth/pin/reset/securityAnswer/email";
	public static final String API_V2_PIN_RESET_USERNAME_SECURITY_QUESTION="identity/v2/auth/pin/reset/securityAnswer/username";
	public static final String API_V2_PIN_RESET_PHONE_SECURITY_QUESTION="identity/v2/auth/pin/reset/securityAnswer/phone";
	public static final String API_V2_PIN_CHANGE_BY_ACCESS_TOKEN="identity/v2/auth/pin/change";

	public static final String SHAREDPREFERENCEFILEKEY = "com.loginradius.loginradiusraas.PREFERENCE_FILE_KEY";
	public static final String webviewlogin=".hub.loginradius.com/RequestHandlor.aspx";


}
