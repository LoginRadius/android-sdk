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
compile 'com.loginradius.android:androidsdk:4.2.0'

```

LoginRadius SDK is now available as AAR dependency. You can add it using File > New Module > Import .JAR/.AAR Package. Then, add it in your build.gradle:

```
compile project(':androidsdk-release')

```

If you prefer to use AAR dependency, then you've to add the following dependencies manually in build.gradle. The AAR file doesn't hold any information about external dependencies.

```
implementation 'com.facebook.android:facebook-android-sdk:4.36.1'
implementation 'com.squareup.retrofit2:retrofit:2.4.0'
implementation 'com.google.code.gson:gson:2.8.5'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
implementation 'com.google.android.gms:play-services-auth:16.0.0'
implementation 'com.android.support:appcompat-v7:28.0.0'
implementation 'com.vk:androidsdk:1.6.7'
```
