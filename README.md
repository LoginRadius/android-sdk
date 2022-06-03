# LoginRadius Android SDK
![Home Image](http://docs.lrcontent.com/resources/github/banner-1544x500.png)

## Introduction ##
LoginRadius is an Identity Management Platform that simplifies user registration while securing data. LoginRadius Platform simplifies and secures your user registration process, increases conversion with Social Login that combines 30 major social platforms, and offers a full solution with Traditional Customer Registration. You can gather a wealth of user profile data from Social Login or Traditional Customer Registration.

LoginRadius centralizes it all in one place, making it easy to manage and access. Easily integrate LoginRadius with all of your third-party applications, like MailChimp, Google Analytics, Livefyre, and many more, making it easy to utilize the data you are capturing.

LoginRadius helps businesses boost user engagement on their web/mobile platform, manage online identities, utilize social media for marketing, capture accurate consumer data, and get unique social insight into their customer base.

Please visit [here](http://www.loginradius.com/) for more information.



#### There are two projects in the library:
a. demo - This is the demo application.    
b. androidSDK -This is the LoginRadius SDK.


# Installing

LoginRadius is now using Gradle.

Use the following dependency in your project:

```
implementation 'com.loginradius.android:androidsdk:5.0.0'

```

LoginRadius SDK is now available as an AAR dependency. You can add it using File > New Module > Import .JAR/.AAR Package. Then, add it to your build.gradle:

```
compile project(':androidsdk-release')

```

## Initialize SDK

Before using the SDK, you must initialize the SDK with the help of the following code:



```
LoginRadiusSDK.Initialize init = new LoginRadiusSDK.Initialize();
```

### Credential Encryption (Optional)

LoginRadius provides an extra level of security for Api Credentials inside the SDK using key store encryption, this encryption will encrypt the Api Key & Site Name, to enable this encryption you need to pass  ```true``` into `setIsEncryption` function.

```
init.setIsEncryption(true);
```

> <b>Note:</b>  For enabling encryption ``init.setIsEncryption(true)`` need to be added before initializing the API key and Site Name. 


<br> 

### Initialize API credentials (Required)
Before using the SDK, you must initialize the Api credentials with the help of the following code

```
init.setApiKey("<your-api-key>");
init.setSiteName("<your-site-name>");
```
For step by step guide on getting Api Credentials from Login Radius Admin Console, please refer to this [document](https://www.loginradius.com/docs/api/v2/admin-console/platform-security/api-key-and-secret/#api-key-and-secret)

### Referer Header (Optional)

The referer header is used to determine the registration source from which the user has created the account and is synced in the  RegistrationSource field for the user profile. When initializing the SDK, you can optionally specify Referer Header.

```
init.setReferer("<Referer-Header-Value>");
```



### Custom Header (Optional)
You can optionally specify Custom Header. This feature allows you to add the Custom header in an API request, you can add multiple headers using key, value pair.


```
Map<String,String> customHeader=new HashMap<String, String>();
customHeader.put("<Custom-Header-Name>", "<Custom-Header-Value>");
customHeader.put("<Custom-Header-Name1>", "<Custom-Header-Value1>");
init.setCustomHeader(customHeader);
       
```


### Facebook Login
For Native Facebook Login Add the following meta data to your Android Manifest inside the application tag:
```

<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="facebook_app_id" />
 <meta-data 
    android:name="com.facebook.sdk.ClientToken" 
    android:value="facebook_client_token"/>

```

### Encryption of Sensitive information

LoginRadius provides key store encryption for sensitive information, you can leverage the following helper functions for encryption and decryption of sensitive information stored inside your project.

> Note: These helper function require Android 6/Api Level 23 or higher, 


#### <b>Encryption</b>
To encrypt the sensitive information using the helper function `encryptData()` please use the following code 
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    String dataToBeEncrypted="<data-to-be-encrypted>";
    String result=LoginRadiusEncryptor.encryptData(dataToBeEncrypted);
    System.out.println(result);
}
```


#### <b>Decryption</b>

The encrypted data will be decrypted using the helper function `decryptData()` please use the following code to decrypt the data.


```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     String encryptedData="<encryptedData>";
     String result= LoginRadiusEncryptor.decryptData(encryptedData);
     System.out.println(result);
}
```

> `Build.VERSION.SDK_INT >= Build.VERSION_CODES.M` indicates that the above helper function should only be called on the API level 23 or higher

