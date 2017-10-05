> **LoginRadius Android SDK Change Log** provides information regarding what has changed, more specifically what changes, improvements and bug fix has been made to the SDK. For more details please refer to the [LoginRadius API Documention](https://docs.loginradius.com/api/v2/mobile-libraries/android)

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


 
 
  
