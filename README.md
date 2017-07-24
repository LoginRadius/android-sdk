# LoginRadius Android SDK
![Home Image](http://docs.lrcontent.com/resources/github/banner-1544x500.png)

## Introduction ##
LoginRadius is an Identity Management Platform that simplifies user registration while securing data. LoginRadius Platform simplifies and secures your user registration process, increases conversion with Social Login that combines 30 major social platforms, and offers a full solution with Traditional Customer Registration. You can gather a wealth of user profile data from Social Login or Traditional Customer Registration.

LoginRadius centralizes it all in one place, making it easy to manage and access. Easily integrate LoginRadius with all of your third-party applications, like MailChimp, Google Analytics, Livefyre and many more, making it easy to utilize the data you are capturing.

LoginRadius helps businesses boost user engagement on their web/mobile platform, manage online identities, utilize social media for marketing, capture accurate consumer data, and get unique social insight into their customer base.

Please visit [here](http://www.loginradius.com/) for more information.



#### There are two projects in the library:
a. demo - This is the demo application.    
b. androidSDK -This is the LoginRadius SDK



##### demo

1)SocialLogin With UserRegistration Demo

a.Put the value according to your requirement in string.xml


```xml
    <string name="app_name">LoginRadius</string>
    <string name="hello_world">Hello world!</string>
    <string name="action_settings">Settings</string>
    <string name="app_id"> your facebook app id </string>
    <string name="facebook_native"> true/false </string>
    <string name="google_native"> true/false </string>
    <string name="recaptchakey"> your recaptchakey </string>
    <string name="loginradius_api_key"> your loginradius api key </string>
    <string name="loginradius_site_name"> your loginradius sitename </string>
    <string name="promptPasswordOnSocialLogin"> true/false </string>
    <string name="Toast_message_for_login">Welcome in LoginRadius</string>
    <string name="Toast_message_for_ForgotPassword">Please check your Email</string>
    <string name="ApplicationActivityId">"your applicationId"</string>
```

b.Bind the OnClick listener to those buttons in the OnCreate method and get value of string.xml and put in Intent.

#### MainActivity.java

```java
 Button loginBt, signinBt, socialBt, forgetBt;
    Button.OnClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        /*   Apikey ,Sitename and applicationid is necessary for implementing Loginradius SDK
         *  */
        final String apiKey = getString(R.string.loginradius_api_key);
        final String siteName = getString(R.string.loginradius_site_name);


        /*  if you want native facebook login then pass app_id in string */
        final String facebook_native = getString(R.string.facebook_native);
        final String google_native = getString(R.string.google_native);

        /*
           optional for user recaptchakey,promptpassword,messagelogin,messageforgotpass
         */
        final String V2RecaptchaSiteKey = getString(R.string.V2RecaptchaSiteKey);
        final String promptpassword = getString(R.string.promptPasswordOnSocialLogin);
        final String messagelogin = getString(R.string.Toast_message_for_login);
        final String messageforgotpass = getString(R.string.Toast_message_for_ForgotPassword);

        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WebviewActivity.class);
                intent.putExtra("V2RecaptchaSiteKey", V2RecaptchaSiteKey);
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("promptpassowrd", promptpassword);
                intent.putExtra("facebook_native", facebook_native);
                intent.putExtra("google_native", google_native);
                intent.putExtra("toast_message_for_login", messagelogin);
                intent.putExtra("toast_message_for_ForgotPassword", messageforgotpass);


                switch (v.getId()) {
                    case R.id.login_bt:
                        intent.putExtra("action", "LOGIN");
                        break;
                    case R.id.signin_bt:
                        intent.putExtra("action", "SIGNIN");
                        // do stuff;
                        break;
                    case R.id.social_bt:
                        intent.putExtra("action", "SOCIAL");
                        break;
                    case R.id.forget_bt:
                        intent.putExtra("action", "FORGOT");
                        break;
                    default:
                        return;
                }
                startActivityForResult(intent, 2);
            }
        };
        initWidget();

    }

    private void initWidget() {
        //initialize button
        loginBt = (Button) findViewById(R.id.login_bt);
        signinBt = (Button) findViewById(R.id.signin_bt);
        socialBt = (Button) findViewById(R.id.social_bt);
        forgetBt = (Button) findViewById(R.id.forget_bt);
        //set on Click listener
        loginBt.setOnClickListener(listener);
        signinBt.setOnClickListener(listener);
        socialBt.setOnClickListener(listener);
        forgetBt.setOnClickListener(listener);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2){
        if(data!=null){
            String accessToken=data.getStringExtra("accesstoken");
            String provider=data.getStringExtra("provider");
            Intent intent = new Intent(getApplication(), UserProfileActivity.class);
            intent.putExtra("accesstoken", accessToken);
            intent.putExtra("provider", provider);
            startActivity(intent);
         }
        }
    }
    
    
```
    
c.After getting token, you can easily call loginradius api in this demo we call UserData, Status, Contacts API in userProfileactivity and get all data..
####UserProfileActivity


```java

 if (intent != null) {
            String token = intent.getStringExtra("accesstoken");
            Log.d("token", token);
            ListView listview = (ListView) findViewById(R.id.listView);
            info = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
            listview.setAdapter(adapter);

            lrAccessToken accessToken = new lrAccessToken();
            accessToken.access_token = token;
            accessToken.provider = "facebook"; // just for demo
            getUserData(accessToken);
            getStatus(accessToken);
            getContacts(accessToken);

        }

    }

    /**
     * Get Profile Info
     **/
    private void getUserData(lrAccessToken accessToken) {
        UserProfileAPI userAPI = new UserProfileAPI();
        userAPI.getResponse(accessToken, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                List<String> result = new ArrayList<String>();
                /**
                 * While we could easily pull any desired fields, we can also just grab every
                 * fields that is not null. Many fields are not strings, but you can extract
                 * their information manually.
                 */
                try {
                    for (Field field : userProfile.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = field.get(userProfile);
                        if (value != null && value instanceof String) {
                            result.add(field.getName() + ": " + value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                info.addAll(result);
                adapter.notifyDataSetChanged();


                nameView.setText(userProfile.FullName);
                nameView.setVisibility(View.VISIBLE);
                if (userProfile.Email != null && userProfile.Email.size() > 0) {
                    emailView.setText(userProfile.Email.get(0).Value);
                    emailView.setVisibility(View.VISIBLE);
                }
                if (userProfile.PhoneNumbers != null && userProfile.PhoneNumbers.size() > 0) {
                    mobileView.setText(userProfile.PhoneNumbers.get(0).phoneNumber);
                    mobileView.setVisibility(View.VISIBLE);
                } else {

                }
                if (userProfile.ImageUrl != null && !userProfile.ImageUrl.equals("")) {
                    ivUserProfile1.setVisibility(View.INVISIBLE);
                    new DownloadProfileImagesTask(userProfile.ImageUrl).execute();
                }
            }


            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("UserProfileAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Some APIs are Provider-specific (such as StatusAPI). If the API is not available
     * for the provider, it will just return 'onFailure' with an errorcode.
     */
    private void getStatus(lrAccessToken token) {
        StatusAPI statusAPI = new StatusAPI();
        statusAPI.getResponse(token, new AsyncHandler<LoginRadiusStatus[]>() {
            @Override
            public void onSuccess(LoginRadiusStatus[] data) {
                if (data.length == 0 || data[0] == null) return;
                info.add("Recent status: " + data[0]);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("StatusAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Load ten contacts into list
     *
     * @param token
     */
    private void getContacts(lrAccessToken token) {

        ContactAPI contactAPI = new ContactAPI();
        contactAPI.getResponse(token, new AsyncHandler<LoginRadiusContactCursorResponse>() {
            @Override
            public void onSuccess(LoginRadiusContactCursorResponse data) {
                int index = 0;
                for (LoginRadiusContact c : data.Data) {
                    if (index >= 10) break;
                    info.add("Contact " + index + ": " + c.name);
                    index++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("ContactAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


```


2)SocialLogin Demo

a.Pass LoginRadius API key and Bind the OnClick listener to those buttons in the OnCreate method.

#### MainActivity.java

```java

    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        lrLoginManager.getAppConfiguration("<api_key>",
                new AsyncHandler<AppInformation>() {
                    @Override
                    public void onSuccess(AppInformation appInfo) {
                        setupView(appInfo);
                    }

                    @Override
                    public void onFailure(Throwable error,
                                          String errorCode) {
                    }
                });
    }


    private void setupView(final AppInformation appInfo) {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /** Perform login through Yahoo **/
                lrLoginManager.performLogin(MainActivity.this,
                        Provider.findByName("facebook", appInfo.Providers),
                        new AsyncHandler<lrAccessToken>() {
                            @Override
                            public void onSuccess(lrAccessToken token) {
                                Intent intent = new Intent(getApplication(), UserProfileActivity.class);
                                intent.putExtra("accesstoken", token.access_token);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Throwable error, String errorcode) {
                            }
                        });

            }
        });
        btnLogin.setEnabled(true);

    }

```

b.After getting token, you can easily call loginradius api in this demo we call UserData, Status, Contacts API in userProfileactivity and get all data..
####UserProfileActivity

```java

 private List<String> info;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_profile);
        if(intent != null) {
            String token = intent.getStringExtra("accesstoken");
            Log.d("token",token);
            ListView listview = (ListView) findViewById(R.id.listView);
            info = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
            listview.setAdapter(adapter);

            lrAccessToken accessToken = new lrAccessToken();
            accessToken.access_token = token;
            accessToken.provider = "facebook"; // just for demo
            getUserData(accessToken);
            getStatus(accessToken);
            getContacts(accessToken);
        }

    }
    /** Get Profile Info **/
    private void getUserData(lrAccessToken accessToken) {
        UserProfileAPI userAPI = new UserProfileAPI();
        userAPI.getResponse(accessToken, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                List<String> result = new ArrayList<String>();
                /**
                 * While we could easily pull any desired fields, we can also just grab every
                 * fields that is not null. Many fields are not strings, but you can extract
                 * their information manually.
                 */
                try {
                    for (Field field : userProfile.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = field.get(userProfile);
                        if (value != null && value instanceof String) {
                            result.add(field.getName() + ": " + value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                info.addAll(result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("UserProfileAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    /**
     * Some APIs are Provider-specific (such as StatusAPI). If the API is not available
     * for the provider, it will just return 'onFailure' with an errorcode.
     */
    private void getStatus(lrAccessToken token) {
        StatusAPI statusAPI = new StatusAPI();
        statusAPI.getResponse(token, new AsyncHandler<LoginRadiusStatus[]>() {
            @Override
            public void onSuccess(LoginRadiusStatus[] data) {
                if (data.length == 0 || data[0] == null) return;
                info.add("Recent status: " + data[0]);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("StatusAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
    /**
     * Load ten contacts into list
     * @param token
     */
    private void getContacts(lrAccessToken token) {

        ContactAPI contactAPI = new ContactAPI();
        contactAPI.getResponse(token, new AsyncHandler<LoginRadiusContactCursorResponse>() {
            @Override
            public void onSuccess(LoginRadiusContactCursorResponse data) {
                int index=0;
                for (LoginRadiusContact c : data.Data) {
                    if (index>=10) break;
                    info.add("Contact " + index + ": " + c.name);
                    index++;
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("ContactAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    
```

c.if you want nativelogin for facebook and google please follow some steps.

#### MainActivity.java

```java

 private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
         btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), FacebookNativeActivity.class);
                intent.putExtra("nativelogin", "true");
                intent.putExtra("keyName", "<your loginradius api key>");
                startActivityForResult(intent, 2);
                }
        });
    }
   
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2){
            if(data!=null){
                String accessToken=data.getStringExtra("accesstoken");
                String provider=data.getStringExtra("provider");
                Intent intent = new Intent(getApplication(), UserProfileActivity.class);
                intent.putExtra("accesstoken", accessToken);
                startActivity(intent);
            }
        }
    }

```


1)After adding in MainActivity you must be define your facebook app id in string.xml
string.xml
```xml
 
 <string name="app_id"> your facebook app id </string>
 
 
```

2)Add the following activity definitions, meta data, and permissions to your Android Manifest inside the application tag:?

AndroidManifest.xml
```xml

 <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/app_id" />

        <activity android:name="com.loginradius.sdk.ui.GoogleSSO" />

        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

        <activity
            android:name="com.facebook.FacebookActivity"
            android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
 ```