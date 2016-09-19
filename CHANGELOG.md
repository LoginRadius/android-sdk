> **LoginRadius Android SDK Change Log** provides information regarding what has changed, more specifically what changes, improvements and bug fix has been made to the SDK. For more details please refer to the [LoginRadius API Documention](http://apidocs.loginradius.com/docs/android)

### Version 2.0.0
Released on **July 18, 2016**

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
  - update connectivity manager and broadcast receiver in Google native login for handling network connectivity and network      change.
  - Update Facebook native login handle null email condition through hosted page.
  
##### Bug Fixes
- Handled social ID provider Empty email address scenario. 


### Version 2.0.2
Released on **September 15,  2016**


##### Bug Fixes
- Fix When back in App crash SDK Activity issue.
- Fix Google Native Login Crash issue if the users device does not have Google Playservices installed.


### Version 2.0.3
Released on **September 19  2016**


##### Bug Fixes
- Removed material design dependency from style.
- Increased heap size in demo.
