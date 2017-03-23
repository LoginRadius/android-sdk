# LoginRadius Android SDK
![Home Image](https://d2lvlj7xfpldmj.cloudfront.net/support/github/banner-1544x500.png)

## Introduction ##
LoginRadius is an Identity Management Platform that simplifies user registration while securing data. LoginRadius Platform simplifies and secures your user registration process, increases conversion with Social Login that combines 30 major social platforms, and offers a full solution with Traditional Customer Registration. You can gather a wealth of user profile data from Social Login or Traditional Customer Registration.

LoginRadius centralizes it all in one place, making it easy to manage and access. Easily integrate LoginRadius with all of your third-party applications, like MailChimp, Google Analytics, Livefyre and many more, making it easy to utilize the data you are capturing.

LoginRadius helps businesses boost user engagement on their web/mobile platform, manage online identities, utilize social media for marketing, capture accurate consumer data, and get unique social insight into their customer base.

Please visit [here](http://www.loginradius.com/) for more information.



#### There are two projects in the library:
a. demo - This is the demo application.    
b. androidSDK -This is the LoginRadius SDK



##### demo

a.Put the value according to your requirement in string.xml


```xml
    <string name="app_name">Demo</string>
    <string name="api_key">your loginradius api key</string>
    <string name="site_name">your loginradius sitename</string>
    <string name="verification_url">https://auth.lrcontent.com/mobile/verification/index.html</string>
    <string name="reset_password_url">https://auth.lrcontent.com/mobile/verification/index.html</string>
    <string name="email_template">your-email-Template</string>
    <string name="app_id">your facebook app_id</string>
    <string name="facebook_native">false</string>
    <string name="google_native">false</string>
    <string name="is_mobile">false</string>
    <string name="username_login">false</string>
    <string name="sms_template">your-sms-template</string>
    <string name="prompt_password_on_social_login">false</string>
```

b.Bind the OnClick listener to those buttons in the OnCreate method and get value of string.xml and put in Intent.
Important:-you must be add loginradius secure-one-time-token(sott) in your MainActivity.

#### MainActivity.java

```java
   String sott = "put your sott";
    private Button btnlogin,btnregister,btnforgotpassword,btnsocial;
    Button.OnClickListener listener;
    String apiKey,siteName,resetPasswordUrl,verificationUrl,emailTemplate,facebooknNative,googleNative,isMobile,smsTemplate,promptPasswordOnSocialLogin,usernameLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKey = getString(R.string.api_key);
        siteName = getString(R.string.site_name);
        resetPasswordUrl = getString(R.string.reset_password_url);
        verificationUrl = getString(R.string.verification_url);
        emailTemplate = getString(R.string.email_template);
        facebooknNative = getString(R.string.facebook_native);
        googleNative = getString(R.string.google_native);
        promptPasswordOnSocialLogin = getString(R.string.prompt_password_on_social_login);
        usernameLogin = getString(R.string.username_login);
        isMobile = getString(R.string.is_mobile);
        smsTemplate = getString(R.string.sms_template);
        listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.login_bt:
                        intent = new Intent(getApplication(), LoginActivity.class);
                        break;
                    case R.id.signup_bt:
                        intent = new Intent(getApplication(), RegisterActivity.class);
                        // do stuff;
                        break;
                    case R.id.social_bt:
                        intent = new Intent(getApplication(), SocialLoginActivity.class);
                        break;
                    case R.id.forgot_bt:
                        intent = new Intent(getApplication(), ForgotPasswordActivity.class);
                        break;
                    default:
                        return;
                }
                CustomizeLanguage customizeLanguage = new CustomizeLanguage();
                intent.putExtra("keyname", apiKey);
                intent.putExtra("sitename", siteName);
                intent.putExtra("ismobile", isMobile);
                intent.putExtra("smstemplate", smsTemplate);
                intent.putExtra("verificationurl", verificationUrl);
                intent.putExtra("facebooknative", facebooknNative);
                intent.putExtra("googlenative", googleNative);
                intent.putExtra("emailtemplate", emailTemplate);
                intent.putExtra("promptpasswordonsociallogin", promptPasswordOnSocialLogin);
                intent.putExtra("usernamelogin", usernameLogin);
                intent.putExtra("sott", sott);
                intent.putExtra("resetpasswordurl", resetPasswordUrl);
                //     intent.putExtra("language",customizeLanguage.getLanguage());
                startActivityForResult(intent, 2);
            }
        };
        initWidget();



    }
    private void initWidget() {
        //initialize button
        btnlogin = (Button) findViewById(R.id.login_bt);
        btnregister = (Button) findViewById(R.id.signup_bt);
        btnsocial = (Button) findViewById(R.id.social_bt);
        btnforgotpassword = (Button) findViewById(R.id.forgot_bt);
        //set on Click listener
        btnlogin.setOnClickListener(listener);
        btnregister.setOnClickListener(listener);
        btnsocial.setOnClickListener(listener);
        btnforgotpassword.setOnClickListener(listener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2) {
            if (data != null){
                String token = data.getStringExtra("accesstoken");
                String provider=data.getStringExtra("provider");
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                intent.putExtra("apikey",apiKey);
                startActivity(intent);
            }  }
    }
    
    
```
    
c.After getting token, you can easily call loginradius api in this demo we call UserData, Status, Contacts API in userProfileactivity and get all data..
####UserProfileActivity


```java

  if (intent != null) {
            String token = intent.getStringExtra("accesstoken");
            String provider = intent.getStringExtra("provider");
            String apiKey = intent.getStringExtra("apikey");
            Log.d("token", token);
            ListView listview = (ListView) findViewById(R.id.listView);
            info = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
            listview.setAdapter(adapter);
            final lrAccessToken accessToken = new lrAccessToken();
            accessToken.access_token = token;
            accessToken.provider = provider;
            accessToken.apikey=apiKey;
            getUserData(accessToken);
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
                String email = userProfile.Email.get(0).Value;
                String Uid = userProfile.getUid();
                if (userProfile.getCustomFields() != null) {
                    LinkedTreeMap linkedMap = (LinkedTreeMap) userProfile.getCustomFields();
                    linkedMap.get("CustomField Name");
                }
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
                info.add("Email: " + email);
                info.add("UID: " + Uid);
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


```
