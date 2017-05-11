package com.loginradius.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.activity.WebViewActivity;

import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.phone.PhoneResponse;
import com.loginradius.androidsdk.response.register.DeleteResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.api.SocailInterfaceAPI;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.api.AddEmailAPI;
import com.loginradius.androidsdk.api.ChangePasswordAPI;
import com.loginradius.androidsdk.api.ContactAPI;
import com.loginradius.androidsdk.api.GetSocialProfileAPI;
import com.loginradius.androidsdk.api.LinkAPI;
import com.loginradius.androidsdk.api.RemoveEmailAPI;
import com.loginradius.androidsdk.api.StatusAPI;
import com.loginradius.androidsdk.api.UnlinkAPI;
import com.loginradius.androidsdk.api.UpdateProfileAPI;
import com.loginradius.androidsdk.api.UpdatephoneAPI;
import com.loginradius.androidsdk.api.UserProfileAPI;
import com.loginradius.androidsdk.api.VerifyOtpAPI;
import com.loginradius.androidsdk.response.LoginRadiusContactCursorResponse;
import com.loginradius.androidsdk.response.contact.LoginRadiusContact;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.status.LoginRadiusStatus;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.handler.AsyncHandler;


public class ProfileActivity extends AppCompatActivity {
    private List<String> info;
    private ArrayAdapter<String> adapter;
    private List<Provider> providers;
    EditText update1,update2,update3,changepass1,changepass2,updatephone,addemail,removeemail;
    Button updatebutton,changebutton,phonebutton,addemailbutton,removeemailbutton,btnlogout;
    Button unlinkFacebook,unlinkGoogle,unlinkTwitter,unlinkLinkedIn,unlinkYahoo,unlinkInstagram,unlinkAmazon,unlinkLive,unlinkVirgilio,unlinkWordpress,unlinkmailru;
    Button unlinkPinterest,unlinkVkontakte,unlinkDisqus,unlinkAOL,unlinkMixi,unlinkSteamcommunity,unlinkHyves,unlinkLiveJournal,unlinkVerisign,unlinkXing,unlinkStackexchange;
    Button unlinkFourSquare,unlinkGitHub,unlinkOpenID,unlinkRenren,unlinkKaixin,unlinkQq,unlinkSalesforce,unlinkOdnoklassniki,unlinkPaypal,unlinkSinaweibo,unlinkLine;
    ImageButton Facebook,Google,Twitter,LinkedIn,Yahoo,Instagram,Amazon,Live,Vkontakte,Disqus,AOL,Pinterest,Mixi,Steamcommunity,Hyves,mailru,Xing,Line;
    ImageButton LiveJournal,Verisign,Virgilio,FourSquare,GitHub,OpenID,Renren,Kaixin,Qq,Stackexchange,Salesforce,Odnoklassniki,Paypal,Sinaweibo,Wordpress;
    String vFacebook,vGoogle,vTwitter,vLinkedIn,vYahoo,vInstagram,vAmazon,vLive,vVkontakte,vDisqus,vAOL,vPinterest,vMixi,vSteamcommunity,vHyves,vmailru,vXing,vLine;
    String vLiveJournal,vVerisign,vVirgilio,vFourSquare,vGitHub,vOpenID,vRenren,vKaixin,vQq,vStackexchange,vSalesforce,vOdnoklassniki,vPaypal,vSinaweibo,vWordpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.activity_profile);
        update1=(EditText) findViewById(R.id.udateedit1);
        update2=(EditText) findViewById(R.id.udateedit2);
        update3=(EditText) findViewById(R.id.udateedit3);
        changepass1=(EditText) findViewById(R.id.changepass1);
        changepass2=(EditText) findViewById(R.id.changepass2);
        updatephone=(EditText) findViewById(R.id.phonenumber);
        addemail=(EditText) findViewById(R.id.addemail);
        removeemail=(EditText) findViewById(R.id.removeemail);
        updatebutton= (Button) findViewById(R.id.updatebutton);
        changebutton= (Button) findViewById(R.id.changebutton);
        phonebutton=(Button) findViewById(R.id.phonebutton);
        addemailbutton=(Button) findViewById(R.id.addemailbutton);
        removeemailbutton=(Button) findViewById(R.id.removeemailbutton);
        btnlogout=(Button)findViewById(R.id.btnlogout);
        Facebook=(ImageButton)findViewById(R.id.Facebook);
        Google=(ImageButton)findViewById(R.id.Google);
        Twitter=(ImageButton)findViewById(R.id.Twitter);
        LinkedIn=(ImageButton)findViewById(R.id.LinkedIn);
        Yahoo=(ImageButton)findViewById(R.id.Yahoo);
        Instagram=(ImageButton)findViewById(R.id.Instagram);
        Amazon=(ImageButton)findViewById(R.id.Amazon);
        Live=(ImageButton)findViewById(R.id.Live);
        Vkontakte=(ImageButton)findViewById(R.id.Vkontakte);
        Disqus=(ImageButton)findViewById(R.id.Disqus);
        AOL=(ImageButton)findViewById(R.id.AOL);
        Pinterest=(ImageButton)findViewById(R.id.Pinterest);
        Mixi=(ImageButton)findViewById(R.id.Mixi);
        Steamcommunity=(ImageButton)findViewById(R.id.Steamcommunity);
        Hyves=(ImageButton)findViewById(R.id.Hyves);
        mailru=(ImageButton)findViewById(R.id.mailru);
        Xing=(ImageButton)findViewById(R.id.Xing);
        Line=(ImageButton)findViewById(R.id.Line);
        LiveJournal=(ImageButton)findViewById(R.id.LiveJournal);
        Verisign=(ImageButton)findViewById(R.id.Verisign);
        Virgilio=(ImageButton)findViewById(R.id.Virgilio);
        FourSquare=(ImageButton)findViewById(R.id.FourSquare);
        GitHub=(ImageButton)findViewById(R.id.GitHub);
        OpenID=(ImageButton)findViewById(R.id.OpenID);
        Renren=(ImageButton)findViewById(R.id.Renren);
        Kaixin=(ImageButton)findViewById(R.id.Kaixin);
        Qq=(ImageButton)findViewById(R.id.Qq);
        Stackexchange=(ImageButton)findViewById(R.id.Stackexchange);
        Salesforce=(ImageButton)findViewById(R.id.Salesforce);
        Kaixin=(ImageButton)findViewById(R.id.Kaixin);
        Odnoklassniki=(ImageButton)findViewById(R.id.Odnoklassniki);
        Paypal=(ImageButton)findViewById(R.id.Paypal);
        Sinaweibo=(ImageButton)findViewById(R.id.Sinaweibo);
        Wordpress=(ImageButton)findViewById(R.id.Wordpress);
        unlinkFacebook=(Button)findViewById(R.id.unlinkFacebook);
        unlinkGoogle=(Button)findViewById(R.id.unlinkGoogle);
        unlinkTwitter=(Button)findViewById(R.id.unlinkTwitter);
        unlinkLinkedIn=(Button)findViewById(R.id.unlinkLinkedIn);
        unlinkYahoo=(Button)findViewById(R.id.unlinkYahoo);
        unlinkInstagram=(Button)findViewById(R.id.unlinkInstagram);
        unlinkAmazon=(Button)findViewById(R.id.unlinkAmazon);
        unlinkLive=(Button)findViewById(R.id.unlinkLive);
        unlinkPinterest=(Button)findViewById(R.id.unlinkPinterest);
        unlinkVkontakte=(Button)findViewById(R.id.unlinkVkontakte);
        unlinkDisqus=(Button)findViewById(R.id.unlinkDisqus);
        unlinkAOL=(Button)findViewById(R.id.unlinkAOL);
        unlinkMixi=(Button)findViewById(R.id.unlinkMixi);
        unlinkSteamcommunity=(Button)findViewById(R.id.unlinkSteamcommunity);
        unlinkHyves=(Button)findViewById(R.id.unlinkHyves);
        unlinkLiveJournal=(Button)findViewById(R.id.unlinkLiveJournal);
        unlinkVerisign=(Button)findViewById(R.id.unlinkVerisign);
        unlinkVirgilio=(Button)findViewById(R.id.unlinkVirgilio);
        unlinkFourSquare=(Button)findViewById(R.id.unlinkFourSquare);
        unlinkGitHub=(Button)findViewById(R.id.unlinkGitHub);
        unlinkOpenID=(Button)findViewById(R.id.unlinkOpenID);
        unlinkRenren=(Button)findViewById(R.id.unlinkRenren);
        unlinkKaixin=(Button)findViewById(R.id.unlinkKaixin);
        unlinkQq=(Button)findViewById(R.id.unlinkQq);
        unlinkSalesforce=(Button)findViewById(R.id.unlinkSalesforce);
        unlinkOdnoklassniki=(Button)findViewById(R.id.unlinkOdnoklassniki);
        unlinkPaypal=(Button)findViewById(R.id.unlinkPaypal);
        unlinkSinaweibo=(Button)findViewById(R.id.unlinkSinaweibo);
        unlinkWordpress=(Button)findViewById(R.id.unlinkWordpress);
        unlinkmailru=(Button)findViewById(R.id.unlinkmailru);
        unlinkXing=(Button)findViewById(R.id.unlinkXing);
        unlinkLine=(Button)findViewById(R.id.unlinkLine);
        unlinkStackexchange=(Button)findViewById(R.id.unlinkStackexchange);
        Toast.makeText(ProfileActivity.this,"Welcome in Loginradius", Toast.LENGTH_LONG).show();
        LinearLayout liemail=(LinearLayout)findViewById(R.id.lichangeemail);
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


            //  getStatus(accessToken);
            //   getContacts(accessToken);
            CheckIdentites(accessToken);
            final String is_mobile = "true";
            if (is_mobile!=null && is_mobile.equals("true")) {
                liemail.setVisibility(View.GONE);
            }
            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateProfile(accessToken);
                }
            });
            changebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Changepassword(accessToken);
                }
            });
            phonebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Updatephone(accessToken);
                }
            });

            addemailbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddEmail(accessToken);
                }
            });

            removeemailbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveEmail(accessToken);
                }
            });




            btnlogout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    try {
                        Context   con = createPackageContext("com.loginradius.link", 0);
                        SharedPreferences pref = con.getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE);
                        pref.edit().remove("ssoaccesstoken").apply();
                        SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
                        editor.remove("ssoaccesstoken");
                        editor.apply();
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    Intent intent  = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

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
                if (userProfile.Email!=null) {
                     String email = userProfile.Email.get(0).Value;
                }
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
              //  info.add("Email: " + email);
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
                for (int i = 0; i < data.length; i++){
                    info.add("Status: " + data[i].text);
                    info.add("Status Id: " + data[i].id);
                }
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
                    info.add("Email " + index + ": " + c.emailId);
                    info.add("PhoneNumber " + index + ": " + c.phoneNumber);
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




    private void UpdateProfile(lrAccessToken token) {
        String lrapikey = getString(R.string.api_key);                 //get loginradius api key from string.xml
        String verificationUrl = getString(R.string.verification_url);   //get verificationUrl from string.xml(optional)
        String emailTemplate = getString(R.string.email_template);       //get emailTemplate url from string.xml(optional)
        LoginParams value = new LoginParams();
        value.apikey = lrapikey;
        value.verificationUrl = verificationUrl;  //optional if update email
        value.emailTemplate = emailTemplate;     // optional if update email
        JsonObject update = new JsonObject();
        update.addProperty("firstname", update1.getText().toString());
        update.addProperty("lastname", update2.getText().toString());
        update.addProperty("username", update3.getText().toString());
        final UpdateProfileAPI updateProfileAPI = new UpdateProfileAPI();
        updateProfileAPI.getResponse(value, token, update, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    Toast.makeText(ProfileActivity.this, "You have successfully Update your Profile", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Changepassword(lrAccessToken token) {
        String lrapikey = getString(R.string.api_key);                 //get loginradius api key from string.xml
        LoginParams value = new LoginParams();
        value.apikey = lrapikey;
        JsonObject change = new JsonObject();
        change.addProperty("OldPassword", changepass1.getText().toString());  // put your old password
        change.addProperty("NewPassword", changepass2.getText().toString());     // put your new password
        final ChangePasswordAPI changepasswordAPI = new ChangePasswordAPI();
        changepasswordAPI.getResponse(value, token, change, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    Toast.makeText(ProfileActivity.this, "You have successfully Change your Password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }





    private void Updatephone(final lrAccessToken token) {
        final String lrapikey = getString(R.string.api_key);                 //get loginradius api key from string.xml
        final LoginParams value = new LoginParams();
        value.apikey = lrapikey;
        JsonObject update = new JsonObject();
        update.addProperty("Phone", updatephone.getText().toString());  // put your phone number
        final Context context = this;
        final UpdatephoneAPI updatephoneAPI = new UpdatephoneAPI();
        updatephoneAPI.getResponse(value, token, update, new AsyncHandler<PhoneResponse>() {
            @Override
            public void onSuccess(PhoneResponse updtephone) {
                if (updtephone.getIsPosted()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Otp Verification"); //Set Alert dialog title here
                    alert.setMessage("Enter Your otp Here"); //Message here
                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    alert.setView(input);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //You will get as string input data in this variable.
                            // here we convert the input to a string and show in a toast.
                            String otp = input.getEditableText().toString();
                            VerifyOTP(token, value, otp);
                        } // End of onClick(DialogInterface dialog, int whichButton)
                    }); //End of alert.setPositiveButton
                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                            dialog.cancel();
                        }
                    }); //End of alert.setNegativeButton
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    /* Alert Dialog Code End*/
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void VerifyOTP(lrAccessToken token, LoginParams value, String otp) {
        String smsTemplate = getString(R.string.sms_template); //get smsTemplate from string.xml(optional)
        final VerifyOtpAPI verifyotpAPI = new VerifyOtpAPI();
        verifyotpAPI.getResponse(value, token, otp, smsTemplate, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    Toast.makeText(ProfileActivity.this, "your phone is update successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void AddEmail(lrAccessToken token) {
        String lrapikey = getString(R.string.api_key);                 //get loginradius api key from string.xml
        String verificationUrl = getString(R.string.verification_url);   //get verificationUrl from string.xml(optional)
        String emailTemplate = getString(R.string.email_template);       //get emailTemplate url from string.xml(optional)
        LoginParams value = new LoginParams();
        value.apikey = lrapikey;
        value.verificationUrl = verificationUrl;
        value.emailTemplate = emailTemplate;
        JsonObject update = new JsonObject();
        update.addProperty("Email", addemail.getText().toString());  // put your Email Address
        final AddEmailAPI addEmailAPI = new AddEmailAPI();
        addEmailAPI.getResponse(value, token, update, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    Toast.makeText(ProfileActivity.this, "Please verify your Email", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void RemoveEmail(lrAccessToken token) {
        String apikey = getString(R.string.api_key);    //get loginradius api key from string.xml
        LoginParams value = new LoginParams();
        value.apikey = apikey;
        JsonObject delete = new JsonObject();
        delete.addProperty("Email", removeemail.getText().toString());  // put your Email Address
        final RemoveEmailAPI removeEmailAPI = new RemoveEmailAPI();
        removeEmailAPI.getResponse(value, token, delete, new AsyncHandler<DeleteResponse>() {
            @Override
            public void onSuccess(DeleteResponse deleteResponse) {
                if (deleteResponse.getIsDeleted()) {
                    Toast.makeText(ProfileActivity.this, "You have successfully RemoveEmail ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }








    private void CheckIdentites(final lrAccessToken token){
        UserProfileAPI userAPI = new UserProfileAPI();
        userAPI.getResponse(token, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                if (userProfile.getIdentities()!=null) {
                    Object[] ad = ((ArrayList) userProfile.getIdentities()).toArray();
                    for (Object i : ad) {
                        LinkedTreeMap linkprovider = (LinkedTreeMap) i;
                        switch (linkprovider.get("Provider").toString()) {

                            case "facebook":
                                vFacebook="VISIBLE";
                                break;
                            case "google":
                                vGoogle="VISIBLE";
                                break;
                            case "twitter":
                                vTwitter ="VISIBLE";
                                break;
                            case "linkedin":
                                vLinkedIn="VISIBLE";
                                break;
                            case "yahoo":
                                vYahoo="VISIBLE";
                                break;
                            case "instagram":
                                vInstagram="VISIBLE";
                                break;
                            case "amazon":
                                vAmazon="VISIBLE";
                                break;
                            case "live":
                                vLive="VISIBLE";
                                break;
                            case "vkontakte":
                                vVkontakte="VISIBLE";
                                break;
                            case "disqus":
                                vDisqus="VISIBLE";
                                break;
                            case "aol":
                                vAOL="VISIBLE";
                                break;
                            case "pinterest":
                                vPinterest="VISIBLE";
                                break;
                            case "mixi":
                                vMixi="VISIBLE";
                                break;
                            case "steamcommunity":
                                vSteamcommunity="VISIBLE";
                                break;
                            case "hyves":
                                vHyves="VISIBLE";
                                break;
                            case "livejournal":
                                vLiveJournal="VISIBLE";
                                break;
                            case "verisign":
                                vVerisign="VISIBLE";
                                break;
                            case "virgilio":
                                vVirgilio="VISIBLE";
                                break;
                            case "foursquare":
                                vFourSquare="VISIBLE";
                                break;
                            case "github":
                                vGitHub="VISIBLE";
                                break;
                            case "openid":
                                vOpenID="VISIBLE";
                                break;
                            case "renren":
                                vRenren="VISIBLE";
                                break;
                            case "kaixin":
                                vKaixin="VISIBLE";
                                break;
                            case "qq":
                                vQq="VISIBLE";
                                break;
                            case "stackexchange":
                                vStackexchange="VISIBLE";
                                break;
                            case "salesforce":
                                vSalesforce="VISIBLE";
                                break;
                            case "odnoklassniki":
                                vOdnoklassniki="VISIBLE";
                                break;
                            case "paypal":
                                vPaypal="VISIBLE";
                                break;
                            case "sinaweibo":
                                vSinaweibo="VISIBLE";
                                break;
                            case "wordpress":
                                vWordpress="VISIBLE";
                                break;
                            case "mailru":
                                vmailru="VISIBLE";
                                break;
                            case "xing":
                                vXing="VISIBLE";
                                break;
                            case "line":
                                vLine="VISIBLE";
                                break;
                        }

                        SocialIdentities(token);
                    }
                }else {
                    SocialIdentities(token);
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
            }
        });
    }

    private void SocialIdentities(final lrAccessToken token) {
        final SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
        editor.putString("login_token", token.access_token);
        editor.putString("login_provider",token.provider);
        editor.commit();
        LoginParams value = new LoginParams();
        value.apikey=getString(R.string.api_key);
        final SocailInterfaceAPI socialapi = new SocailInterfaceAPI();
        socialapi.getResponse(value,new AsyncHandler<SocialInterface>() {
            @Override
            public void onSuccess(SocialInterface socialInterface) {
                providers = socialInterface.getProviders();
                if(providers !=null && providers.size() >0 ) {
                    for (final Provider provider : providers) {
                        final Intent intent = new Intent(getApplication(), WebViewActivity.class);
                        intent.putExtra("keyName", getString(R.string.api_key));
                        intent.putExtra("sitename", getString(R.string.site_name));
                        intent.putExtra("verificationUrl", getString(R.string.verification_url));
                        intent.putExtra("emailTemplate", getString(R.string.email_template));
                        intent.putExtra("smsTemplate", getString(R.string.sms_template));

                        switch (provider.getName()){
                            case "Facebook":
                                if (vFacebook!=null &&vFacebook.equals("VISIBLE")) {
                                    unlinkFacebook.setVisibility(View.VISIBLE);
                                    unlinkFacebook.setText("Unlink Facebook Click Hear");
                                    Facebook.setVisibility(View.GONE);
                                    unlinkFacebook.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFacebookid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Facebook";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Facebook.setVisibility(View.VISIBLE);
                                    Facebook.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Facebook");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "Google":
                                if (vGoogle!=null&&vGoogle.equals("VISIBLE")) {
                                    Google.setVisibility(View.GONE);
                                    unlinkGoogle.setVisibility(View.VISIBLE);
                                    unlinkGoogle.setText("Unlink Google Click Hear");
                                    unlinkGoogle.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGoogleid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Google";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Google.setVisibility(View.VISIBLE);
                                    Google.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Google");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "Twitter":
                                if (vTwitter!=null &&vTwitter.equals("VISIBLE")) {
                                    Twitter.setVisibility(View.GONE);
                                    unlinkTwitter.setVisibility(View.VISIBLE);
                                    unlinkTwitter.setText("Unlink Twitter Click Hear");
                                    unlinkTwitter.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkTwitterid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Twitter";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Twitter.setVisibility(View.VISIBLE);
                                    Twitter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Twitter");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "LinkedIn":
                                if (vLinkedIn!=null &&vLinkedIn.equals("VISIBLE")) {
                                    LinkedIn.setVisibility(View.GONE);
                                    unlinkLinkedIn.setVisibility(View.VISIBLE);
                                    unlinkLinkedIn.setText("Unlink LinkedIn Click Hear");
                                    unlinkLinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLinkedInid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="LinkedIn";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    LinkedIn.setVisibility(View.VISIBLE);
                                    LinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "LinkedIn");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "Yahoo":
                                if (vYahoo!=null && vYahoo.equals("VISIBLE")) {
                                    Yahoo.setVisibility(View.GONE);
                                    unlinkYahoo.setVisibility(View.VISIBLE);
                                    unlinkYahoo.setText("Unlink Yahoo Click Hear");
                                    unlinkYahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkYahooid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Yahoo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Yahoo.setVisibility(View.VISIBLE);
                                    Yahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Yahoo");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;

                            case "Instagram":
                                if (vInstagram!=null &&vInstagram.equals("VISIBLE")) {
                                    Instagram.setVisibility(View.GONE);
                                    unlinkInstagram.setVisibility(View.VISIBLE);
                                    unlinkInstagram.setText("Unlink Instagram Click Hear");
                                    unlinkInstagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkInstagramid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Instagram";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });

                                }else {
                                    Instagram.setVisibility(View.VISIBLE);
                                    Instagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Instagram");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Amazon":
                                if (vAmazon!=null&&vAmazon.equals("VISIBLE")) {
                                    Amazon.setVisibility(View.GONE);
                                    unlinkAmazon.setVisibility(View.VISIBLE);
                                    unlinkAmazon.setText("Unlink Amazon Click Hear");
                                    unlinkAmazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAmazonid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Amazon";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Amazon.setVisibility(View.VISIBLE);
                                    Amazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Amazon");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Live":
                                if (vLive!=null&&vLive.equals("VISIBLE")) {
                                    Live.setVisibility(View.GONE);
                                    unlinkLive.setVisibility(View.VISIBLE);
                                    unlinkLive.setText("Unlink Live Click Hear");
                                    unlinkLive.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Live";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Live.setVisibility(View.VISIBLE);
                                    Live.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Live");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Vkontakte":
                                if (vVkontakte!=null&&vVkontakte.equals("VISIBLE")) {
                                    Vkontakte.setVisibility(View.GONE);
                                    unlinkVkontakte.setVisibility(View.VISIBLE);
                                    unlinkVkontakte.setText("Unlink Vkontakte Click Hear");
                                    unlinkVkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVkontakteid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Vkontakte";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Vkontakte.setVisibility(View.VISIBLE);
                                    Vkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Vkontakte");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Disqus":
                                if (vDisqus!=null&&vDisqus.equals("VISIBLE")) {
                                    Disqus.setVisibility(View.GONE);
                                    unlinkDisqus.setVisibility(View.VISIBLE);
                                    unlinkDisqus.setText("Unlink Disqus Click Hear");
                                    unlinkDisqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkDisqusid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Disqus";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Disqus.setVisibility(View.VISIBLE);
                                    Disqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Disqus");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "AOL":
                                if (vAOL!=null&&vAOL.equals("VISIBLE")) {
                                    AOL.setVisibility(View.GONE);
                                    unlinkAOL.setVisibility(View.VISIBLE);
                                    unlinkAOL.setText("Unlink AOL Click Hear");
                                    unlinkAOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAOLid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="AOL";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    AOL.setVisibility(View.VISIBLE);
                                    AOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "AOL");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Pinterest":
                                if (vPinterest!=null&&vPinterest.equals("VISIBLE")) {
                                    Pinterest.setVisibility(View.GONE);
                                    unlinkPinterest.setVisibility(View.VISIBLE);
                                    unlinkPinterest.setText("Unlink Pinterest Click Hear");
                                    unlinkPinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPinterestid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Pinterest";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Pinterest.setVisibility(View.VISIBLE);
                                    Pinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Pinterest");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Mixi":
                                if (vMixi!=null&&vMixi.equals("VISIBLE")) {
                                    Mixi.setVisibility(View.GONE);
                                    unlinkMixi.setVisibility(View.VISIBLE);
                                    unlinkMixi.setText("Unlink Mixi Click Hear");
                                    unlinkMixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkMixiid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Mixi";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Mixi.setVisibility(View.VISIBLE);
                                    Mixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Mixi");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Steamcommunity":
                                if (vSteamcommunity!=null&&vSteamcommunity.equals("VISIBLE")) {
                                    Steamcommunity.setVisibility(View.GONE);
                                    unlinkSteamcommunity.setVisibility(View.VISIBLE);
                                    unlinkSteamcommunity.setText("Unlink Steamcommunity Click Hear");
                                    unlinkSteamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSteamcommunityid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Steamcommunity";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Steamcommunity.setVisibility(View.VISIBLE);
                                    Steamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Steamcommunity");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Hyves":
                                if (vHyves!=null&&vHyves.equals("VISIBLE")) {
                                    Hyves.setVisibility(View.GONE);
                                    unlinkHyves.setVisibility(View.VISIBLE);
                                    unlinkHyves.setText("Unlink Hyves Click Hear");
                                    unlinkHyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkHyvesid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Hyves";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Hyves.setVisibility(View.VISIBLE);
                                    Hyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Hyves");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "LiveJournal":
                                if (vLiveJournal!=null&&vLiveJournal.equals("VISIBLE")) {
                                    LiveJournal.setVisibility(View.GONE);
                                    unlinkLiveJournal.setVisibility(View.VISIBLE);
                                    unlinkLiveJournal.setText("Unlink LiveJournal Click Hear");
                                    unlinkLiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveJournalid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="LiveJournal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    LiveJournal.setVisibility(View.VISIBLE);
                                    LiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "LiveJournal");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Verisign":
                                if (vVerisign!=null&&vVerisign.equals("VISIBLE")) {
                                    Verisign.setVisibility(View.GONE);
                                    unlinkVerisign.setVisibility(View.VISIBLE);
                                    unlinkVerisign.setText("Unlink Verisign Click Hear");
                                    unlinkVerisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVerisignid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Verisign";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Verisign.setVisibility(View.VISIBLE);
                                    Verisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Verisign");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Virgilio":
                                if (vVirgilio!=null&&vVirgilio.equals("VISIBLE")) {
                                    Virgilio.setVisibility(View.GONE);
                                    unlinkVirgilio.setVisibility(View.VISIBLE);
                                    unlinkVirgilio.setText("Unlink Virgilio Click Hear");
                                    unlinkVirgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilioid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Virgilio";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });

                                }else {
                                    Virgilio.setVisibility(View.VISIBLE);
                                    Virgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Virgilio");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "FourSquare":
                                if (vFourSquare!=null&&vFourSquare.equals("VISIBLE")) {
                                    FourSquare.setVisibility(View.GONE);
                                    unlinkFourSquare.setVisibility(View.VISIBLE);
                                    unlinkFourSquare.setText("Unlink FourSquare Click Hear");
                                    unlinkFourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFourSquareid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="FourSquare";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    FourSquare.setVisibility(View.VISIBLE);
                                    FourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "FourSquare");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "GitHub":
                                if (vGitHub!=null&&vGitHub.equals("VISIBLE")) {
                                    GitHub.setVisibility(View.GONE);
                                    unlinkGitHub.setVisibility(View.VISIBLE);
                                    unlinkGitHub.setText("Unlink GitHub Click Hear");
                                    unlinkGitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGitHubid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="GitHub";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    GitHub.setVisibility(View.VISIBLE);
                                    GitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "GitHub");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "OpenID":
                                if (vOpenID!=null&&vOpenID.equals("VISIBLE")) {
                                    OpenID.setVisibility(View.GONE);
                                    unlinkOpenID.setVisibility(View.VISIBLE);
                                    unlinkOpenID.setText("Unlink OpenID Click Hear");
                                    unlinkOpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOpenIDid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="OpenID";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    OpenID.setVisibility(View.VISIBLE);
                                    OpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "OpenID");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "Renren":
                                if (vRenren!=null&&vRenren.equals("VISIBLE")) {
                                    Renren.setVisibility(View.GONE);
                                    unlinkRenren.setVisibility(View.VISIBLE);
                                    unlinkRenren.setText("Unlink Renren Click Hear");
                                    unlinkRenren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkRenrenid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Renren";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Renren.setVisibility(View.VISIBLE);
                                    Renren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Renren");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Kaixin":
                                if (vKaixin!=null&&vKaixin.equals("VISIBLE")) {
                                    Kaixin.setVisibility(View.GONE);
                                    unlinkKaixin.setVisibility(View.VISIBLE);
                                    unlinkKaixin.setText("Unlink Kaixin Click Hear");
                                    unlinkKaixin.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkKaixinid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Kaixin";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Kaixin.setVisibility(View.VISIBLE);
                                    Renren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Kaixin");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Qq":
                                if (vQq!=null&&vQq.equals("VISIBLE")) {
                                    Qq.setVisibility(View.GONE);
                                    unlinkQq.setVisibility(View.VISIBLE);
                                    unlinkQq.setText("Unlink Qq Click Hear");
                                    unlinkQq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkQqid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Qq";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Qq.setVisibility(View.VISIBLE);
                                    Qq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Qq");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Stackexchange":
                                if (vStackexchange!=null&&vStackexchange.equals("VISIBLE")) {
                                    Stackexchange.setVisibility(View.GONE);
                                    unlinkStackexchange.setVisibility(View.VISIBLE);
                                    unlinkStackexchange.setText("Unlink Stackexchange Click Hear");
                                    unlinkStackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkStackexchangeid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Stackexchange";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Stackexchange.setVisibility(View.VISIBLE);
                                    Stackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Stackexchange");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Salesforce":
                                if (vSalesforce!=null&&vSalesforce.equals("VISIBLE")) {
                                    Salesforce.setVisibility(View.GONE);
                                    unlinkSalesforce.setVisibility(View.VISIBLE);
                                    unlinkSalesforce.setText("Unlink Salesforce Click Hear");
                                    unlinkSalesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSalesforceid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Salesforce";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Salesforce.setVisibility(View.VISIBLE);
                                    Salesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Salesforce");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Odnoklassniki":
                                if (vOdnoklassniki!=null&&vOdnoklassniki.equals("VISIBLE")) {
                                    Odnoklassniki.setVisibility(View.GONE);
                                    unlinkOdnoklassniki.setVisibility(View.VISIBLE);
                                    unlinkOdnoklassniki.setText("Unlink Odnoklassniki Click Hear");
                                    unlinkOdnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOdnoklassnikiid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Odnoklassniki";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Odnoklassniki.setVisibility(View.VISIBLE);
                                    Odnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Odnoklassniki");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Paypal":
                                if (vPaypal!=null&&vPaypal.equals("VISIBLE")) {
                                    Paypal.setVisibility(View.GONE);
                                    unlinkPaypal.setVisibility(View.VISIBLE);
                                    unlinkPaypal.setText("Unlink Paypal Click Hear");
                                    unlinkPaypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPaypalid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Paypal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Paypal.setVisibility(View.VISIBLE);
                                    Paypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Paypal");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Sinaweibo":
                                if (vSinaweibo!=null&&vSinaweibo.equals("VISIBLE")) {
                                    Sinaweibo.setVisibility(View.GONE);
                                    unlinkSinaweibo.setVisibility(View.VISIBLE);
                                    unlinkSinaweibo.setText("Unlink Sinaweibo Click Hear");
                                    unlinkSinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSinaweiboid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Sinaweibo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Sinaweibo.setVisibility(View.VISIBLE);
                                    Sinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Sinaweibo");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Wordpress":
                                if (vWordpress!=null&&vWordpress.equals("VISIBLE")) {
                                    Wordpress.setVisibility(View.GONE);
                                    unlinkWordpress.setVisibility(View.VISIBLE);
                                    unlinkWordpress.setText("Unlink Wordpress Click Hear");
                                    unlinkWordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkWordpressid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Wordpress";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Wordpress.setVisibility(View.VISIBLE);
                                    Wordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Wordpress");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "mailru":
                                if (vmailru!=null&&vmailru.equals("VISIBLE")) {
                                    mailru.setVisibility(View.GONE);
                                    unlinkmailru.setVisibility(View.VISIBLE);
                                    unlinkmailru.setText("Unlink mailru Click Hear");
                                    unlinkmailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkmailruid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="mailru";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    mailru.setVisibility(View.VISIBLE);
                                    mailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "mailru");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Xing":
                                if (vXing!=null&&vXing.equals("VISIBLE")) {
                                    Xing.setVisibility(View.GONE);
                                    unlinkXing.setText("Unlink Xing Click Hear");
                                    unlinkXing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkXingid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Xing";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Xing.setVisibility(View.VISIBLE);
                                    Xing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Xing");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "Line":
                                if (vLine!=null&&vLine.equals("VISIBLE")) {
                                    Line.setVisibility(View.GONE);
                                    unlinkLine.setVisibility(View.VISIBLE);
                                    unlinkLine.setText("Unlink Line Click Hear");
                                    unlinkLine.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLineid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Line";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Line.setVisibility(View.VISIBLE);
                                    Line.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("action", "Line");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            default:
                                return;
                        }


                    }
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2) {
            if (data != null){
                String token = data.getStringExtra("accesstoken");
                String provider = data.getStringExtra("provider");
                lrAccessToken accessToken = new lrAccessToken();
                accessToken.access_token = token;
                accessToken.provider = provider;
                Link(accessToken);

            }  }
    }

    private void Link(final lrAccessToken token){
        String apikey=getString(R.string.api_key);    //get loginradius api key from string.xml
        final LoginParams value=new LoginParams();
        value.apikey=apikey;
        GetSocialProfileAPI getsocialprofileAPI = new GetSocialProfileAPI();
        getsocialprofileAPI.getResponse(value,token,new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(final LoginRadiusUltimateUserProfile ultimateUserProfile) {
                final String logintoken = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("login_token", "");
                JsonObject change = new JsonObject();
                change.addProperty("candidateToken",token.access_token);  // put your new provider token
                final LinkAPI linkAPI = new LinkAPI();
                linkAPI.getResponse(value,logintoken,change,new AsyncHandler<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse registerResponse) {
                        if (registerResponse.getIsPosted()){
                            Toast.makeText(ProfileActivity.this, "you have successfully link with " +token.provider, Toast.LENGTH_LONG).show();
                            final SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
                            switch (token.provider){
                                case "Facebook":
                                    Facebook.setVisibility(View.GONE);
                                    editor.putString("linkFacebookid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkFacebook.setVisibility(View.VISIBLE);
                                    unlinkFacebook.setText("Unlink Facebook Click Hear");
                                    unlinkFacebook.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFacebookid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Facebook";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Google":
                                    Google.setVisibility(View.GONE);
                                    editor.putString("linkGoogleid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkGoogle.setVisibility(View.VISIBLE);
                                    unlinkGoogle.setText("Unlink Google Click Hear");
                                    unlinkGoogle.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGoogleid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Google";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Twitter":
                                    Twitter.setVisibility(View.GONE);
                                    editor.putString("linkTwitterid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkTwitter.setVisibility(View.VISIBLE);
                                    unlinkTwitter.setText("Unlink Twitter Click Hear");
                                    unlinkTwitter.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkTwitterid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Twitter";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "LinkedIn":
                                    LinkedIn.setVisibility(View.GONE);
                                    editor.putString("linkLinkedInid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLinkedIn.setVisibility(View.VISIBLE);
                                    unlinkLinkedIn.setText("Unlink LinkedIn Click Hear");
                                    unlinkLinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLinkedInid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="LinkedIn";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Yahoo":
                                    Yahoo.setVisibility(View.GONE);
                                    editor.putString("linkYahooid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkYahoo.setVisibility(View.VISIBLE);
                                    unlinkYahoo.setText("Unlink Yahoo Click Hear");
                                    unlinkYahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkYahooid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Yahoo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Instagram":
                                    Instagram.setVisibility(View.GONE);
                                    editor.putString("linkInstagramid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkInstagram.setVisibility(View.VISIBLE);
                                    unlinkInstagram.setText("Unlink Instagram Click Hear");
                                    unlinkInstagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkInstagramid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Instagram";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Amazon":
                                    Amazon.setVisibility(View.GONE);
                                    editor.putString("linkAmazonid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkAmazon.setVisibility(View.VISIBLE);
                                    unlinkAmazon.setText("Unlink Amazon Click Hear");
                                    unlinkAmazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAmazonid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Amazon";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Live":
                                    Live.setVisibility(View.GONE);
                                    editor.putString("linkLiveid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLive.setVisibility(View.VISIBLE);
                                    unlinkLive.setText("Unlink Live Click Hear");
                                    unlinkLive.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Live";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Vkontakte":
                                    Vkontakte.setVisibility(View.GONE);
                                    editor.putString("linkVkontakteid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVkontakte.setVisibility(View.VISIBLE);
                                    unlinkVkontakte.setText("Unlink Vkontakte Click Hear");
                                    unlinkVkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVkontakteid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Vkontakte";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Disqus":
                                    Disqus.setVisibility(View.GONE);
                                    editor.putString("linkDisqusid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkDisqus.setVisibility(View.VISIBLE);
                                    unlinkDisqus.setText("Unlink Disqus Click Hear");
                                    unlinkDisqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkDisqusid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Disqus";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "AOL":
                                    AOL.setVisibility(View.GONE);
                                    editor.putString("linkAOLid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkAOL.setVisibility(View.VISIBLE);
                                    unlinkAOL.setText("Unlink AOL Click Hear");
                                    unlinkAOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAOLid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="AOL";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Pinterest":
                                    Pinterest.setVisibility(View.GONE);
                                    editor.putString("linkPinterestid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkPinterest.setVisibility(View.VISIBLE);
                                    unlinkPinterest.setText("Unlink Pinterest Click Hear");
                                    unlinkPinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPinterestid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Pinterest";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Mixi":
                                    Mixi.setVisibility(View.GONE);
                                    editor.putString("linkMixiid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkMixi.setVisibility(View.VISIBLE);
                                    unlinkMixi.setText("Unlink Mixi Click Hear");
                                    unlinkMixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkMixiid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Mixi";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Steamcommunity":
                                    Steamcommunity.setVisibility(View.GONE);
                                    editor.putString("linkSteamcommunityid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkSteamcommunity.setVisibility(View.VISIBLE);
                                    unlinkSteamcommunity.setText("Unlink Steamcommunity Click Hear");
                                    unlinkSteamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSteamcommunityid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Steamcommunity";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Hyves":
                                    Hyves.setVisibility(View.GONE);
                                    editor.putString("linkHyvesid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkHyves.setVisibility(View.VISIBLE);
                                    unlinkHyves.setText("Unlink Hyves Click Hear");
                                    unlinkHyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkHyvesid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Hyves";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "LiveJournal":
                                    LiveJournal.setVisibility(View.GONE);
                                    editor.putString("linkLiveJournalid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLiveJournal.setVisibility(View.VISIBLE);
                                    unlinkLiveJournal.setText("Unlink LiveJournal Click Hear");
                                    unlinkLiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveJournalid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="LiveJournal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Verisign":
                                    Verisign.setVisibility(View.GONE);
                                    editor.putString("linkVerisignid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVerisign.setVisibility(View.VISIBLE);
                                    unlinkVerisign.setText("Unlink Verisign Click Hear");
                                    unlinkVerisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVerisignid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Verisign";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Virgilio":
                                    Virgilio.setVisibility(View.GONE);
                                    editor.putString("linkVirgilioid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVirgilio.setVisibility(View.VISIBLE);
                                    unlinkVirgilio.setText("Unlink Virgilio Click Hear");
                                    unlinkVirgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linktonke = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilio", "");
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilioid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.access_token=linktonke;
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "FourSquare":
                                    FourSquare.setVisibility(View.GONE);
                                    editor.putString("linkFourSquareid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkFourSquare.setVisibility(View.VISIBLE);
                                    unlinkFourSquare.setText("Unlink FourSquare Click Hear");
                                    unlinkFourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFourSquareid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="FourSquare";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "GitHub":
                                    GitHub.setVisibility(View.GONE);
                                    editor.putString("linkGitHubid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkGitHub.setVisibility(View.VISIBLE);
                                    unlinkGitHub.setText("Unlink GitHub Click Hear");
                                    unlinkGitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGitHubid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="GitHub";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "OpenID":
                                    OpenID.setVisibility(View.GONE);
                                    editor.putString("linkOpenIDid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkOpenID.setVisibility(View.VISIBLE);
                                    unlinkOpenID.setText("Unlink OpenID Click Hear");
                                    unlinkOpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOpenIDid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="OpenID";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Renren":
                                    Renren.setVisibility(View.GONE);
                                    editor.putString("linkRenrenid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkRenren.setVisibility(View.VISIBLE);
                                    unlinkRenren.setText("Unlink Renren Click Hear");
                                    unlinkRenren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkRenrenid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Renren";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Kaixin":
                                    Kaixin.setVisibility(View.GONE);
                                    editor.putString("linkKaixinid", token.id);
                                    editor.commit();
                                    unlinkKaixin.setVisibility(View.VISIBLE);
                                    unlinkKaixin.setText("Unlink Kaixin Click Hear");
                                    unlinkKaixin.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkKaixinid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Kaixin";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Qq":
                                    Qq.setVisibility(View.GONE);
                                    editor.putString("linkQqid", token.id);
                                    editor.commit();
                                    unlinkQq.setVisibility(View.VISIBLE);
                                    unlinkQq.setText("Unlink Qq Click Hear");
                                    unlinkQq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkQqid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Qq";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Stackexchange":
                                    Stackexchange.setVisibility(View.GONE);
                                    editor.putString("linkStackexchangeid", token.id);
                                    editor.commit();
                                    unlinkStackexchange.setVisibility(View.VISIBLE);
                                    unlinkStackexchange.setText("Unlink Stackexchange Click Hear");
                                    unlinkStackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkStackexchangeid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Stackexchange";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Salesforce":
                                    Salesforce.setVisibility(View.GONE);
                                    editor.putString("linkSalesforceid", token.id);
                                    editor.commit();
                                    unlinkSalesforce.setVisibility(View.VISIBLE);
                                    unlinkSalesforce.setText("Unlink Salesforce Click Hear");
                                    unlinkSalesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSalesforceid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Salesforce";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Odnoklassniki":
                                    Odnoklassniki.setVisibility(View.GONE);
                                    editor.putString("linkOdnoklassnikiid", token.id);
                                    editor.commit();
                                    unlinkOdnoklassniki.setVisibility(View.VISIBLE);
                                    unlinkOdnoklassniki.setText("Unlink Odnoklassniki Click Hear");
                                    unlinkOdnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOdnoklassnikiid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Odnoklassniki";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Paypal":
                                    Paypal.setVisibility(View.GONE);
                                    editor.putString("linkPaypalid", token.id);
                                    editor.commit();
                                    unlinkPaypal.setVisibility(View.VISIBLE);
                                    unlinkPaypal.setText("Unlink Paypal Click Hear");
                                    unlinkPaypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPaypalid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Paypal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Sinaweibo":
                                    Sinaweibo.setVisibility(View.GONE);
                                    editor.putString("linkSinaweiboid", token.id);
                                    editor.commit();
                                    unlinkSinaweibo.setVisibility(View.VISIBLE);
                                    unlinkSinaweibo.setText("Unlink Sinaweibo Click Hear");
                                    unlinkSinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSinaweiboid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Sinaweibo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Wordpress":
                                    Wordpress.setVisibility(View.GONE);
                                    editor.putString("linkWordpressid", token.id);
                                    editor.commit();
                                    unlinkWordpress.setVisibility(View.VISIBLE);
                                    unlinkWordpress.setText("Unlink Wordpress Click Hear");
                                    unlinkWordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkWordpressid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Wordpress";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "mailru":
                                    mailru.setVisibility(View.GONE);
                                    editor.putString("linkmailruid", token.id);
                                    editor.commit();
                                    unlinkmailru.setVisibility(View.VISIBLE);
                                    unlinkmailru.setText("Unlink mailru Click Hear");
                                    unlinkmailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkmailruid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="mailru";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Xing":
                                    Xing.setVisibility(View.GONE);
                                    editor.putString("linkXingid", token.id);
                                    editor.commit();
                                    unlinkXing.setVisibility(View.VISIBLE);
                                    unlinkXing.setText("Unlink Xing Click Hear");
                                    unlinkXing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkXingid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Xing";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "Line":
                                    Line.setVisibility(View.GONE);
                                    editor.putString("linkLineid", token.id);
                                    editor.commit();
                                    unlinkLine.setVisibility(View.VISIBLE);
                                    unlinkLine.setText("Unlink Line Click Hear");
                                    unlinkLine.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLineid", "");
                                            lrAccessToken accessToken = new lrAccessToken();
                                            accessToken.provider="Line";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                            }

                        }
                    }
                    @Override
                    public void onFailure(Throwable error, String errorcode) {
                        Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }





    private void Unlink(final lrAccessToken token){
        String lrapikey = getString(R.string.api_key);                 //get loginradius api key from string.xml
        LoginParams value = new LoginParams();
        value.apikey=lrapikey;
        final String logintoken = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("login_token", "");
        JsonObject change = new JsonObject();
        change.addProperty("Provider", token.provider.toLowerCase());
        change.addProperty("ProviderId", token.id); // put your new provider token
        final UnlinkAPI unlinkAPI = new UnlinkAPI();
        unlinkAPI.getResponse(value,logintoken,change,new AsyncHandler<DeleteResponse>() {
            @Override
            public void onSuccess(DeleteResponse deleteResponse) {
                if (deleteResponse.getIsDeleted()){
                    Toast.makeText(ProfileActivity.this, "you have successfully Unlink with " +token.provider, Toast.LENGTH_LONG).show();
                    switch (token.provider){
                        case "Facebook":
                            Facebook.setVisibility(View.VISIBLE);
                            unlinkFacebook.setVisibility(View.GONE);
                            break;
                        case "Google":
                            Google.setVisibility(View.VISIBLE);
                            unlinkGoogle.setVisibility(View.GONE);
                            break;
                        case "Twitter":
                            Twitter.setVisibility(View.VISIBLE);
                            unlinkTwitter.setVisibility(View.GONE);

                            break;
                        case "LinkedIn":
                            LinkedIn.setVisibility(View.VISIBLE);
                            unlinkLinkedIn.setVisibility(View.GONE);

                            break;
                        case "Yahoo":
                            Yahoo.setVisibility(View.VISIBLE);
                            unlinkYahoo.setVisibility(View.GONE);

                            break;
                        case "Instagram":
                            Instagram.setVisibility(View.VISIBLE);
                            unlinkInstagram.setVisibility(View.GONE);
                            break;
                        case "Amazon":
                            Amazon.setVisibility(View.VISIBLE);
                            unlinkAmazon.setVisibility(View.GONE);
                            break;
                        case "Live":
                            Live.setVisibility(View.VISIBLE);
                            unlinkLive.setVisibility(View.GONE);
                            break;
                        case "Vkontakte":
                            Vkontakte.setVisibility(View.VISIBLE);
                            unlinkVkontakte.setVisibility(View.GONE);
                            break;
                        case "Disqus":
                            Disqus.setVisibility(View.VISIBLE);
                            unlinkDisqus.setVisibility(View.GONE);
                            break;
                        case "AOL":
                            AOL.setVisibility(View.VISIBLE);
                            unlinkAOL.setVisibility(View.GONE);
                            break;
                        case "Pinterest":
                            Pinterest.setVisibility(View.VISIBLE);
                            unlinkPinterest.setVisibility(View.GONE);
                            break;
                        case "Mixi":
                            Mixi.setVisibility(View.VISIBLE);
                            unlinkMixi.setVisibility(View.GONE);
                            break;
                        case "Steamcommunity":
                            Steamcommunity.setVisibility(View.VISIBLE);
                            unlinkSteamcommunity.setVisibility(View.GONE);
                            break;
                        case "Hyves":
                            Hyves.setVisibility(View.VISIBLE);
                            unlinkHyves.setVisibility(View.GONE);
                            break;
                        case "LiveJournal":
                            LiveJournal.setVisibility(View.VISIBLE);
                            unlinkLiveJournal.setVisibility(View.GONE);
                            break;
                        case "Verisign":
                            Verisign.setVisibility(View.VISIBLE);
                            unlinkVerisign.setVisibility(View.GONE);
                            break;
                        case "Virgilio":
                            Virgilio.setVisibility(View.VISIBLE);
                            unlinkVirgilio.setVisibility(View.GONE);
                            break;
                        case "FourSquare":
                            FourSquare.setVisibility(View.VISIBLE);
                            unlinkFourSquare.setVisibility(View.GONE);
                            break;
                        case "GitHub":
                            GitHub.setVisibility(View.VISIBLE);
                            unlinkGitHub.setVisibility(View.GONE);
                            break;
                        case "OpenID":
                            OpenID.setVisibility(View.VISIBLE);
                            unlinkOpenID.setVisibility(View.GONE);
                            break;
                        case "Renren":
                            Renren.setVisibility(View.VISIBLE);
                            unlinkRenren.setVisibility(View.GONE);
                            break;
                        case "Kaixin":
                            Kaixin.setVisibility(View.VISIBLE);
                            unlinkKaixin.setVisibility(View.GONE);
                            break;
                        case "Qq":
                            Qq.setVisibility(View.VISIBLE);
                            unlinkQq.setVisibility(View.GONE);
                            break;
                        case "Stackexchange":
                            Stackexchange.setVisibility(View.VISIBLE);
                            unlinkStackexchange.setVisibility(View.GONE);
                            break;
                        case "Salesforce":
                            Salesforce.setVisibility(View.VISIBLE);
                            unlinkSalesforce.setVisibility(View.GONE);
                            break;
                        case "Odnoklassniki":
                            Odnoklassniki.setVisibility(View.VISIBLE);
                            unlinkOdnoklassniki.setVisibility(View.GONE);
                            break;
                        case "Paypal":
                            Paypal.setVisibility(View.VISIBLE);
                            unlinkPaypal.setVisibility(View.GONE);
                            break;
                        case "Sinaweibo":
                            Sinaweibo.setVisibility(View.VISIBLE);
                            unlinkSinaweibo.setVisibility(View.GONE);
                            break;
                        case "Wordpress":
                            Wordpress.setVisibility(View.VISIBLE);
                            unlinkWordpress.setVisibility(View.GONE);
                            break;
                        case "mailru":
                            mailru.setVisibility(View.VISIBLE);
                            unlinkmailru.setVisibility(View.GONE);
                            break;
                        case "Xing":
                            Xing.setVisibility(View.VISIBLE);
                            unlinkXing.setVisibility(View.GONE);
                            break;
                        case "Line":
                            Line.setVisibility(View.VISIBLE);
                            unlinkLine.setVisibility(View.GONE);
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }



}

