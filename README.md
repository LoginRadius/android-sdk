# LoginRadius Android SDK
![Home Image](http://docs.lrcontent.com/resources/github/banner-1544x500.png)

## Introduction ##
LoginRadius is an Identity Management Platform that simplifies user registration while securing data. LoginRadius Platform simplifies and secures your user registration process, increases conversion with Social Login that combines 30 major social platforms, and offers a full solution with Traditional Customer Registration. You can gather a wealth of user profile data from Social Login or Traditional Customer Registration.

LoginRadius centralizes it all in one place, making it easy to manage and access. Easily integrate LoginRadius with all of your third-party applications, like MailChimp, Google Analytics, Livefyre and many more, making it easy to utilize the data you are capturing.

LoginRadius helps businesses boost user engagement on their web/mobile platform, manage online identities, utilize social media for marketing, capture accurate consumer data, and get unique social insight into their customer base.

Please visit [here](http://www.loginradius.com/) for more information.



#### There are two projects in the library:
a. demo - This is the demo application.    
b. androidSDK -This is the LoginRadius SDK.


# Installing

LoginRadius is now using Gradle.

Use the following dependency in your project:

```
implementation 'com.loginradius.android:androidsdk:4.9.0'

```

LoginRadius SDK is now available as AAR dependency. You can add it using File > New Module > Import .JAR/.AAR Package. Then, add it in your build.gradle:

```
compile project(':androidsdk-release')

```

## Initialize SDK

Before using the SDK, you must initialize the SDK with the help of following code:



```
LoginRadiusSDK.Initialize init = new LoginRadiusSDK.Initialize();
init.setApiKey("<your-api-key>");
init.setSiteName("<your-site-name>");

```

## Referar Header (Optional)

The referer header is used to determine the registration source from which the user has created the account and is synced in the  RegistrationSource field for the user profile. When initializing the SDK, you can optionally specify Referar Header.

```
init.setReferer("<Referar-Header-Value>");
```



## Custom Header (Optional)
You can optionally specify Custom Header. This feature allow you to add the Custom header in an API request, you can add multiple headers using key ,value pair.


```
Map<String,String> customHeader=new HashMap<String, String>();
customHeader.put("<Custom-Header-Name>", "<Custom-Header-Value>");
customHeader.put("<Custom-Header-Name1>", "<Custom-Header-Value1>");
init.setCustomHeader(customHeader);
       
```


