> **LoginRadius Android SDK Change Log** provides information regarding what has changed, more specifically what changes, improvements and bug fix has been made to the SDK. For more details please refer to the [LoginRadius API Documention](https://www.loginradius.com/docs/api/v2/deployment/mobile-sdk-libraries/android-library/)

### Version 5.2.1
Released on ** April 09, 2025**

##### Enhancements

- Improved error and success messages.
- Updated the Google Sign-In and authentication services with version 21.3.0

### Version 5.2.0
Released on ** June 06, 2024**

##### Enhancements
Added MFA push notification functionality in the SDK which allow to  register the device by scanning the QR code to register the device. Upon successful registration, LoginRadius can issue an authentication challenge to the registered device. When the consumer receives the push notification, they simply need to tap 'allow' or 'deny' to respond.

### Version 5.1.0
Released on **January 04, 2024**

##### Enhancements
Added Biometric Authentication helper class in the LoginRadius SDK to authenticate using Face and Fingerprint. For detailed information on Biometric Authentication, please refer to the following document:
https://developer.android.com/training/sign-in/biometric-auth


### Version 5.0.0
Released on **June 03,  2022**

##### Enhancements


- Added a Security Improvement in the Android SDK for Storing secure information like a key, token, and secret in the [Android Keystore system](https://developer.android.com/training/articles/keystore). The Android Keystore system lets you store cryptographic keys in a container to make it more difficult to extract from the device.

- The LoginRadius SDK provides the public helper functions `encryptData()` & `decryptData()` for the Secure Keystore encryption Process, these functions will be used for the encryption and decryption of sensitive information that will be stored inside the project. 

> For Example:- Customers can able store any sensitive information in the above method by adding an additional security layer to make it difficult to extract data from the device.

- Updated Facebook SDK with the latest version.

#### Breaking Changes
For developers migrating from v4.9.2, there will be 1 minor breaking change in terms of SDK implementation.<br>
In this version, a Facebook Client Token will be required while implementing facebook native login, for more info please refer to our implementation [guide](https://www.loginradius.com/docs/libraries/mobile-sdk-libraries/android-library/#advancedoptions11).

### Version 4.9.2
Released on **February 07,  2022**

##### Removed (Deprecated) API:

- `getUserProfile` (Social User Profile EndPoint)

In this Android SDK version, we have **removed/deprecated** the `getUserProfile` method (Social User Profile  EndPoint ).
This API endpoint is also deprecated from the LoginRadius backend soon, so we will suggest please use  `readAllUserProfile` method instead of `getUserProfile` method.

To know more about the Implementation of this API please refer to this [document](https://www.loginradius.com/docs/libraries/mobile-sdk-libraries/android-library/#read-complete-user-profile).


### Version 4.9.1
Released on **October 01,  2021**

##### Enhancements
- Added Custom header feature
- Added Referer Header Feature
- Updated the Facebook Login SDK with version 11.2.0

### Version 4.8.0
Released on **September 11,  2020**

##### Enhancements

  - Added new Dyanamic methods to changing Social Permission according to requirement for Google and Facebook Native Login.


### Version 4.7.0
Released on **June 16,  2020**

##### Enhancements

  - Added new SocialAppName(optional) Parameter in Native Social Login methods for supporting multiple apps feature for the social providers

### Version 4.6.0
Released on **November 28,  2019**

##### Enhancements

  - Added new WeChat Native Login feature for Better Native User Experience.
   
##### Bug Fixes

  - Fixed issue related to libraries reference with the SDK Gradle installation.


### Version 4.5.1
Released on **September 24,  2019** 

##### Bug Fixes

  - Fixed bug to retrieve Updated UserProfile After calling UpdateProfile API.

##### Breaking Changes

For developers migrating from v4.5.0, there will be some breaking changes in terms of SDK implementation. In this version, we have made some changes in the Response model in the "UPDATE USER PROFILE" method. For more details, please have a look [here](https://docs.loginradius.com/api/v2/mobile-libraries/android)

### Version 4.5.0
Released on **Aug 30,  2019** 

##### Enhancements

   - Added new PIN Authentication APIs. PIN authentication is an alternate way to do authentication, instead of password users can use the PIN. PIN Authentication can be used for re-authentication also (when the user accesses a specific section or area).
   - Added new model parameters in LoginData and RegisterationData Class.	
	
	
##### Bug Fixes

  - Fixed bug to handle non-json error response.
  
  
### Version 4.4.0
Released on **July 31,  2019** 

##### Enhancements

  - LoginRadius SDK compiled with the new version of Facebook Login SDK(Version [5,6)).
  - Added new callback method for SDK Logout function.
  - Added new a overload method for SetLogin function now user can store access_token and UserProfile after successful login in local Session for long time.

##### Breaking Changes

For developers migrating from v4.4.0-beta, there will be one breaking changes in terms of SDK implementation. In this version, we have updated the methods of the Registration API. For more details, please have a look [here](https://www.loginradius.com/docs/api/v2/deployment/mobile-sdk-libraries/android-library/)
  
##### Bug Fixes

  - Fixed bug to retrieve LoginRadius Security Questions By AccessToken.
  - Fixed Some Missing Model Parameters in RegisterationData JSON.



### Version 4.4.0-beta
Released on **June 18,  2019** 

##### Enhancements

  - LoginRadius SDK compiled with the new version of Facebook Login SDK(Version [5,6)).
  
##### Bug Fixes

  - Fixed bug to retrieve LoginRadius Security Questions By AccessToken.
  - Fixed Some Missing Model Parameters in RegisterationData JSON.
  


### Version 4.3.0
Released on **November 19,  2018** 

##### Enhancements
 
  - Improved google native login Performance and UI for Better Native User Experience.
  
##### Bug Fixes

  - Fixed bug to retrieve LoginRadius refresh_token in Native Login and Traditional Login.
  - Fixed Some Missing Parameters in Traditional Login Response JSON.
  - Fixed google refresh token issue in native login.

### Version 4.2.0
Released on **September 25,  2018** 

##### Enhancements

  - LoginRadius SDK compiled with the new version of Facebook SDK(Version 4.36.1).
  - LoginRadius SDK compiled with the new version of Google Account Login(Version 16.0.0).
  - Added New Remove Phone ID by Access Token API.
  
##### Bug Fixes

  - Fixed bug in Twitter first time login on a newly installed application on mobile device.
  - Fixed bug in Demo at the time of unlinking social provider.


### Version 4.1.0
Released on **August 21,  2018**

##### Enhancements

  - SOTT is added as header in Registration API.
  - Added Privacy Policy API
  - Added Reset Password By Email OTP API
  - Added Verify Email By OTP API
  - Added acceptPrivacyPolicy (Boolean) property in RegistrationData.
  - Access Token is added as header in all Authentication APIs
  - Added preventEmailVerification (Boolean) option to prevent email verification flow in Auth Login and Registration APIs (where optional email is enabled)
  - Updated Auth Registration API to add Access Token as response (where optional email is enabled)
  - Added Send Welcome Email API

##### Breaking Changes

For developers migrating from v4.0.1, there will be some breaking changes in terms of SDK implementation. In this version, we have updated endpoints and renamed "Auto Login" to "Smart Login", "No Registration/Simplified Registration" to "One touch Login" and "Instant Link Login" to "PasswordLess Login". Also, changed the methods of the above APIs accordingly. For more details, please have a look [here](https://docs.loginradius.com/api/v2/mobile-libraries/android)

### Version 4.0.1
Released on **January 12,  2018**

##### Enhancements

  - Optimized native social login and enhanced it's performance.
  - Added askRequiredFieldsOnTraditionalLogin (boolean) option in Authentication API for login process. It is used for enabling missing/required fields flow in traditional login.
  - Updated Configuration API endpoint.

### Version 4.0.0
Released on **November 17,  2017**

##### Breaking Changes

For developers migrating from v3.x.x, there will be some breaking changes in terms of SDK implementation. In the previous versions, you've to define the common variables everytime while making API calls. But, from this release onwards, you can initialize them once and they'll be automatically reflected in every single API call you make. It can be done using LoginRadiusSDK.Initialize class.

Also, you'll find some major restructuring while using APIs. For example, till v3.x.x, for getting a user profile, you've to use the code similar to the following:

```
lrAccessToken token = new lrAccessToken();
token.access_token = "<your-access-token>";
token.apikey="<your-api-key>";
UserProfileAPI api = new UserProfileAPI();
api.getResponse(token, new AsyncHandler <LoginRadiusUltimateUserProfile> () {
 @Override
 public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
     Toast.makeText(context,"First Name: "+userProfile.FirstName+" Last Name:"+userProfile.LastName,Toast.LENGTH_SHORT).show();
 }

 @Override
 public void onFailure(Throwable error, String errorcode) {
     Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
 }
});
```

From v4.x.x, for fetching user profile, you've to use code similar to the following:

```
QueryParams params = new QueryParams();
params.setAccess_token(access_token);
AuthenticationAPI api = new AuthenticationAPI();
api.readAllUserProfile(params, new AsyncHandler < LoginRadiusUltimateUserProfile > () {
	@Override
	public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
		Toast.makeText(context, "First Name: " + userProfile.FirstName + " Last Name:" + userProfile.LastName, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailure(Throwable error, String errorcode) {
		Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
	}
});
```

##### Enhancements

  - Restructuring of API calls and methods.
  - Initialize required values with LoginRadiusSDK.Initialize class.
  - Added LoginRadiusSDK.NativeLogin and LoginRadiusSDK.WebLogin classes for better social login usage.
  - Added constant strings for social providers.
  - SOTT as header in Registration API
  - Added password strength indicator field in Profile class
  - Added more APIs
  
##### Bug Fixes

  - Minor bug fixes

### Version 3.5.0
Released on **October 5,  2017**


##### Enhancements

  - Added projection of fields in some APIs.
  - Added Verify Auto Login Email API.
  - Released AAR dependency
  
##### Bug Fixes

  - Now, LoginRadiusUltimateUserProfile.getIdentities() will return a list of Identity objects.
  - Modified the Google Play Services version to 11.0.4

### Version 3.4.0
Released on **August 11,  2017**


##### Enhancements

  - Added required fields registration feature along with RxAndroid/RxJava to optimise rendering of dynamic views at social login.
  - Added new Simplified Registration APIs.
  - Added new Get Security Questions By Phone,Email,UserName or Access_token APIs.
  - Added new Update Security Question by Access_token APIs.
  - Added new Reset Password by Security Question APIs.
  - Added updatetype flag in Custom Object By Access Token API to enable complete or partial update custom object data.
  - Significantly improved code performance.
  
##### Bug Fixes

  - Fixed LinkedIn white space issue.
  

### Version 3.3.5
Released on **July 12,  2017**

##### Bug Fixes

  - Fixed LinkedIn Provider issue related to UserProfileAPI.



### Version 3.3.4
Released on **July 11,  2017**

##### Bug Fixes

  - Improved Exception handling, added few more cases to handle specific exception, like IllegalArgumentException, IOException,IllegalStateException etc.


   
### Version 3.3.3
Released on **July 10,  2017**

##### Bug Fixes

  - Fixed status update permission issue of Facebook native login.
  
 

### Version 3.3.2
Released on **July 06,  2017**

##### Bug Fixes

  - Fixed email template issue of ForgotPasswordAPI.
  - Fixed email type issue of AddEmailAPI.
  - Fixed SMS template issue of VerifyOtpAPI.
  

  
### Version 3.3.1
Released on **June 23,  2017**


##### Bug Fixes

  - Fixed some minor issues related to Facebook and Google native login.
  

### Version 3.3.0
Released on **June 14,  2017**


##### Enhancements

  - Added new Access Token Validate and Invalidate APIs.
  - Added new Phone Login using OTP API.
  - Enhanced performance of network requests and response time reduced.
  - Automatic smart request retries optimized for spotty mobile connections.
  - Avoid intricate stateful programs, using clean input/output functions over observable streams with help of ReactiveX.
  - Implemented ReactiveX, this is a best way to handle I/O blocking operation which is on event based model, this doesn't block main thread and doesn't create whole new thread. So it is lightweight yet powerfull.
  - Significantly improved code performance.


### Version 3.2.3
Released on **May 11,  2017**


##### Enhancements
  
  - Added new fields in APIs response Unverified email ids in UserProfile and Image in PhotoAPI.
  - Added new email prompt Auto login.
  - Significantly improved code performance.

##### Bug Fixes

  - Fixed instagram login bug.



### Version 3.2.2
Released on **April 27,  2017**


##### Enhancements
  
  - Reduce Number of library From SDK.
  - Using upstream HttpClient of version 4.3.6 instead of Android provided DefaultHttpClient.
  - Significantly improved code performance.




### Version 3.2.0
Released on **April 12,  2017**


##### Enhancements
  
  - Easy to Customization.
  - Tiny size overhead to your application, below 200kb for everything.
  - Improved native login performance for Better Native User Experience.
  - Significantly improved code performance.



### Version 3.1.0
Released on **March 23,  2017**


##### Enhancements
  
  - Added CustomeObject API,Custom objects are custom database tables that allow you to store information unique to your organization.
  - Improved native login performance for Better Native User Experience.
  - Significantly improved code performance.



  
### Version 3.0.0
Released on **March 7,  2017**


##### Enhancements

  - Remove Hosted Page Dependency From LoginRadius Android SDK.
  - Added PhoneNuber and UserName Login.
  - Fully Customizable SDK.
  - Added New Multiple APIs For Better User Experience.
  - EndUser Add and Remove Multiple Emails.
  - Added SSO(Single Sign On) in SDK
  - Added Account Linking and Unlinking in SDK.
  - Added Update Profile Feature in SDK.
  - Significantly improved code performance.
  - Handled network connection error and failures.
  - Update all important library.
  - Reduce Number of library From SDK.
  - Using upstream HttpClient of version 4.3.6 instead of Android provided DefaultHttpClient.
  - Compatible with Android API 24 and higher.
  - Support for Multiple languages Feature at all content of SDK.
  - Tiny size overhead to your application, below 450kb for everything.
  - Support Android Material Designing,You can configure your app to use the material theme on devices that support it.
  - Automatic smart request retries optimized for spotty mobile connections.
  - Support Google Smart Lock in SDK.



### Version 2.0.0
Released on **July 12,  2016**

##### Enhancements

  - Significantly improved code performance.
  - Added new fields for hosted page PromptPasswordOnSocialLogin and Google Recaptcha .
  - Handled network connection error and failures.
  - Remove unused library.
  - Update all important library.
  - Reduce android SDK size below 500kb.
  - Add pro-guard compatibility for LoginRadius SDK.
  - A lesser number of activity implementation. Now web view and native login are in LoginRadius SDK.
  - Removed Google native login useless words and implemented a progress bar for waiting.
  - update connectivity manager and broadcast receiver in Google native login for handling network connectivity and network change.
  - Update Facebook native login handle null email condition through hosted page.
  
##### Bug Fixes
  - Handled social ID provider Empty email address scenario. 
