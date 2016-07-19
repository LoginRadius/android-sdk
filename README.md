# LoginRadius Android SDK
![Home Image](https://d2lvlj7xfpldmj.cloudfront.net/support/github/banner-1544x500.png)

## Introduction ##
LoginRadius is an Identity Management Platform that simplifies user registration while securing data. LoginRadius Platform simplifies and secures your user registration process, increases conversion with Social Login that combines 30 major social platforms, and offers a full solution with Traditional Customer Registration. You can gather a wealth of user profile data from Social Login or Traditional Customer Registration.

LoginRadius centralizes it all in one place, making it easy to manage and access. Easily integrate LoginRadius with all of your third-party applications, like MailChimp, Google Analytics, Livefyre and many more, making it easy to utilize the data you are capturing.

LoginRadius helps businesses boost user engagement on their web/mobile platform, manage online identities, utilize social media for marketing, capture accurate consumer data, and get unique social insight into their customer base.

Please visit [here](http://www.loginradius.com/) for more information.


   
#### There are two projects in the library:
a. app project - This is the demo application.    
b. androidSDK -This is the LoginRadius SDK


##### app project

1.Put the value according to your requirement in string.xml

#### string.xml

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


2.Bind the OnClick listener to those buttons in the OnCreate method and get value of string.xml and put in Intent.

#### MainActivity.java

```java

Button loginBt, signinBt, socialBt, forgetBt;
    Button.OnClickListener listener;
    
    
        final String apiKey = getString(R.string.loginradius_api_key);
        final String siteName = getString(R.string.loginradius_site_name);
        final String ApplicationActivityId = getString(R.string.ApplicationActivityId);
        final String facebook_native = getString(R.string.facebook_native);
        final String google_native = getString(R.string.google_native);
        final String recaptchakey = getString(R.string.recaptchakey);
        final String promptpassword = getString(R.string.promptPasswordOnSocialLogin);
        final String messagelogin = getString(R.string.Toast_message_for_login);
        final String messageforgotpass = getString(R.string.Toast_message_for_ForgotPassword);
    
     listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), WebviewActivity.class);

                intent.putExtra("recaptchakey", recaptchakey);
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("promptpassowrd", promptpassword);
                intent.putExtra("facebook_native", facebook_native);
                intent.putExtra("google_native", google_native);
                intent.putExtra("toast_message_for_login", messagelogin);
                intent.putExtra("toast_message_for_ForgotPassword", messageforgotpass);
                intent.putExtra("ApplicationActivityId", ApplicationActivityId);

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
                        intent.putExtra("action", "FORGET");
                        break;
                    default:
                        return;
                }
                startActivity(intent);
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
```
3.After getting token, you can easily call loginradius api in this demo we call UserData, Status, Contacts API in userProfileactivity and get all data..
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







