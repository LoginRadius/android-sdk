package com.loginradius.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.loginradius.androidsdk.activity.WebViewActivity;
import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.api.ConfigurationAPI;
import com.loginradius.androidsdk.api.SocialAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.LoginUtil;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.LoginRadiusContactCursorResponse;
import com.loginradius.androidsdk.response.UpdateProfileResponse;
import com.loginradius.androidsdk.response.config.ConfigResponse;
import com.loginradius.androidsdk.response.contact.LoginRadiusContact;
import com.loginradius.androidsdk.response.phone.PhoneResponse;
import com.loginradius.androidsdk.response.register.DeleteResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.response.status.LoginRadiusStatus;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.response.userprofile.identity.Identity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    private ScrollView svContent;
    private AccessTokenResponse accessToken;
    private LinearLayout linearPhone;
    private List<String> info;
    private ArrayAdapter<String> adapter;
    private ListView listview;
    private List<Provider> providers;
    private ProgressBar pbLoad,pbInitLoad;
    private LoginRadiusUltimateUserProfile userProfile;
    EditText update1,update2,update3,changepass1,changepass2,confirm_pwd,updatephone,addemail,removeemail,addtype;
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
        svContent = (ScrollView)findViewById(R.id.svContent);
        linearPhone = (LinearLayout)findViewById(R.id.linearPhone);
        update1=(EditText) findViewById(R.id.udateedit1);
        update2=(EditText) findViewById(R.id.udateedit2);
        update3=(EditText) findViewById(R.id.udateedit3);
        changepass1=(EditText) findViewById(R.id.changepass1);
        changepass2=(EditText) findViewById(R.id.changepass2);
        confirm_pwd = (EditText)findViewById(R.id.confirm_pwd);
        updatephone=(EditText) findViewById(R.id.phonenumber);
        addemail=(EditText) findViewById(R.id.addemail);
        removeemail=(EditText) findViewById(R.id.removeemail);
        addtype=(EditText)findViewById(R.id.addtype);
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
        pbLoad = (ProgressBar)findViewById(R.id.pbLoad);
        pbInitLoad = (ProgressBar)findViewById(R.id.pbInitLoad);
        NotifyToastUtil.showNotify(this,"Welcome in Loginradius");
        if (intent != null) {
            LoginUtil loginUtil = new LoginUtil(this);
            String token = loginUtil.getAccessToken();
            String provider = intent.getStringExtra("provider");
            Log.d("token", token);
            listview = (ListView) findViewById(R.id.listView);
            info = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
            listview.setAdapter(adapter);
            accessToken = new AccessTokenResponse();
            accessToken.access_token = token;
            accessToken.provider = provider;

            getConfiguration();


            //  getStatus(accessToken);
            //   getContacts(accessToken);
            final String is_mobile = "true";

            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateProfile(accessToken);
                }
            });
            changebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String oldPassword = changepass1.getText().toString().trim();
                    String newPassword = changepass2.getText().toString().trim();
                    String confirmPassword = confirm_pwd.getText().toString().trim();
                    if(oldPassword.length()==0){
                        changepass1.setError("Required");
                    }else if(newPassword.length()==0){
                        changepass2.setError("Required");
                    } else if(confirmPassword.equals(newPassword)){
                        Changepassword(accessToken);
                    }else{
                        confirm_pwd.setError("Passwords don't match");
                    }
                }
            });
            phonebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdatePhone(accessToken);
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

                    // For Android SSO Logout
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

                    // For Android Local Session Logout
                    LoginUtil session =new LoginUtil(getApplicationContext());
                    session.logout(accessToken.access_token, new LoginUtil.LogoutCallBack() {
                        @Override
                        public void Response(Boolean isLogout, Boolean isError, ErrorResponse error) {
                            // do your code heare
                            if(isLogout){
                                Log.d("logout",String.valueOf(isLogout));
                                Intent intent  = new Intent(getBaseContext(), MainActivity.class);
                                //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });

                }
            });

        }

    }

    private void getConfiguration() {
        ConfigurationAPI api = new ConfigurationAPI();
        api.getResponse(new AsyncHandler<ConfigResponse>() {
            @Override
            public void onSuccess(ConfigResponse data) {
                if(data.getIsPhoneLogin()){
                    linearPhone.setVisibility(View.VISIBLE);
                }
                getUserData(accessToken);
                CheckIdentites(accessToken);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1500);
            }
        });
    }

    /**
     * Get Profile Info
     **/
    private void getUserData(AccessTokenResponse accessToken) {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(accessToken.access_token);
        api.readAllUserProfile(queryParams,new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                ProfileActivity.this.userProfile = userProfile;
                List<String> result = new ArrayList<String>();
                String email = "";
                if (userProfile.Email!=null) {
                    for(int i=0;i<userProfile.Email.size();i++){
                        email += userProfile.Email.get(i).Value+", ";
                    }
                    email = email.substring(0,email.length()-2);
                }else {
                    email ="Email Field Empty";
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
                info.add("Email: " + email);
                info.add("UID: " + Uid);
                info.addAll(result);
                listview.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listview);
                if(userProfile.FirstName!=null){
                    update1.setText(userProfile.FirstName);
                }
                if(userProfile.LastName!=null){
                    update2.setText(userProfile.LastName);
                }
                if(userProfile.getUserName()!=null){
                    update3.setText(userProfile.getUserName().toString());
                }
                pbLoad.setVisibility(View.GONE);
                pbInitLoad.setVisibility(View.GONE);
                svContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("UserProfileAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                    pbLoad.setVisibility(View.GONE);
                    pbInitLoad.setVisibility(View.GONE);
                }
            }
        });
    }








    /**
     * Some APIs are Provider-specific (such as StatusAPI). If the API is not available
     * for the provider, it will just return 'onFailure' with an errorcode.
     */
    private void getStatus(AccessTokenResponse token) {
        SocialAPI api = new SocialAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        api.getStatus(queryParams, new AsyncHandler<LoginRadiusStatus[]>() {
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
    private void getContacts(AccessTokenResponse token) {
        SocialAPI api = new SocialAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        api.getContact(queryParams, new AsyncHandler<LoginRadiusContactCursorResponse>() {
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




    private void UpdateProfile(AccessTokenResponse token) {
        String verificationUrl = getString(R.string.verification_url);   //get verificationUrl from string.xml(optional)
        String emailTemplate = getString(R.string.email_template);       //get emailTemplate url from string.xml(optional)
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        queryParams.setEmailTemplate(emailTemplate);        //optional if update email
        JsonObject update = new JsonObject();
        update.addProperty("firstname", update1.getText().toString());
        update.addProperty("lastname", update2.getText().toString());
        update.addProperty("username", update3.getText().toString());
        api.updateProfile(queryParams, update, new AsyncHandler<UpdateProfileResponse>() {
            @Override
            public void onSuccess(UpdateProfileResponse response) {
                if (response.getIsPosted()) {
                    NotifyToastUtil.showNotify(ProfileActivity.this,"You have successfully Update your Profile");
                }

            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }

    private void Changepassword(AccessTokenResponse token) {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        JsonObject change = new JsonObject();
        change.addProperty("OldPassword", changepass1.getText().toString());  // put your old password
        change.addProperty("NewPassword", changepass2.getText().toString());     // put your new password
        change.addProperty("ConfirmPassword",confirm_pwd.getText().toString());
        api.changePassword(queryParams, change, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    NotifyToastUtil.showNotify(ProfileActivity.this,"You have successfully Change your Password");
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }





    private void UpdatePhone(final AccessTokenResponse token) {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        JsonObject update = new JsonObject();
        update.addProperty("Phone", updatephone.getText().toString());  // put your phone number
        final Context context = this;
        api.updatePhone(queryParams, update, new AsyncHandler<PhoneResponse>() {
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
                            VerifyOTP(token,otp);
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
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }

    private void VerifyOTP(AccessTokenResponse token, String otp) {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        queryParams.setSmsTemplate(""); //put your smstemplate(optional)
        queryParams.setOtp(otp);
        api.verifyOtpByToken(queryParams, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    NotifyToastUtil.showNotify(ProfileActivity.this,"your phone is update successfully");
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }

    private void AddEmail(AccessTokenResponse token) {
        String verificationUrl = getString(R.string.verification_url);   //get verificationUrl from string.xml(optional)
        String emailTemplate = getString(R.string.email_template);       //get emailTemplate url from string.xml(optional)
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        queryParams.setEmailTemplate(emailTemplate);
        JsonObject update = new JsonObject();
        update.addProperty("Email", addemail.getText().toString().trim());  // put your Email Address
        update.addProperty("Type",addtype.getText().toString().trim());
        api.addEmail(queryParams, update, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse registerResponse) {
                if (registerResponse.getIsPosted()) {
                    NotifyToastUtil.showNotify(ProfileActivity.this,"Please verify your Email");
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });

    }

    private void RemoveEmail(AccessTokenResponse token) {
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        JsonObject delete = new JsonObject();
        delete.addProperty("Email", removeemail.getText().toString());  // put your Email Address
        api.removeEmail(queryParams, delete, new AsyncHandler<DeleteResponse>() {
            @Override
            public void onSuccess(DeleteResponse deleteResponse) {
                if (deleteResponse.getIsDeleted()) {
                    NotifyToastUtil.showNotify(ProfileActivity.this,"You have successfully RemoveEmail ");
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }








    private void CheckIdentites(final AccessTokenResponse token){
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        final SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
        editor.putString("login_token", token.access_token);
        editor.putString("login_provider",token.provider);
        editor.commit();
        api.readAllUserProfile(queryParams, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                if (userProfile.getIdentities()!=null) {
                    List<Identity> identities = userProfile.getIdentities();
                    for (Identity i : identities) {
                        switch (i.getProvider().toLowerCase()) {

                            case "facebook":
                                vFacebook="VISIBLE";
                                editor.putString("linkFacebookid",i.getID());
                                break;
                            case "google":
                                vGoogle="VISIBLE";
                                editor.putString("linkGoogleid",i.getID());
                                break;
                            case "twitter":
                                vTwitter ="VISIBLE";
                                editor.putString("linkTwitterid",i.getID());
                                break;
                            case "linkedin":
                                vLinkedIn="VISIBLE";
                                editor.putString("linkLinkedInid",i.getID());
                                break;
                            case "yahoo":
                                vYahoo="VISIBLE";
                                editor.putString("linkYahooid",i.getID());
                                break;
                            case "instagram":
                                vInstagram="VISIBLE";
                                editor.putString("linkInstagramid",i.getID());
                                break;
                            case "amazon":
                                vAmazon="VISIBLE";
                                editor.putString("linkAmazonid",i.getID());
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

                        editor.commit();
                        SocialIdentities();
                    }
                }else {
                    SocialIdentities();
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
            }
        });
    }

    private void SocialIdentities() {

        ConfigurationAPI api = new ConfigurationAPI();
        api.getResponse(new AsyncHandler<ConfigResponse>() {
            @Override
            public void onSuccess(ConfigResponse data) {
                providers = data.getSocialSchema().getProviders();
                if(providers !=null && providers.size() >0 ) {
                    for (final Provider provider : providers) {
                        final Intent intent = new Intent(getApplication(), WebViewActivity.class);
                        intent.putExtra("verificationUrl", getString(R.string.verification_url));
                        intent.putExtra("emailTemplate", getString(R.string.email_template));
                        intent.putExtra("smsTemplate", getString(R.string.sms_template));

                        switch (provider.getName().toLowerCase()){
                            case "facebook":
                                if (vFacebook!=null &&vFacebook.equals("VISIBLE")) {
                                    unlinkFacebook.setVisibility(View.VISIBLE);
                                    unlinkFacebook.setText("Unlink Facebook Click Here");
                                    Facebook.setVisibility(View.GONE);
                                    unlinkFacebook.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFacebookid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Facebook";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Facebook.setImageResource(R.drawable.ic_facebook);
                                    Facebook.setVisibility(View.VISIBLE);
                                    Facebook.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Facebook");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "google":
                                if (vGoogle!=null&&vGoogle.equals("VISIBLE")) {
                                    Google.setVisibility(View.GONE);
                                    unlinkGoogle.setVisibility(View.VISIBLE);
                                    unlinkGoogle.setText("Unlink Google Click Here");
                                    unlinkGoogle.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGoogleid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Google";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Google.setImageResource(R.drawable.ic_google);
                                    Google.setVisibility(View.VISIBLE);
                                    Google.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Google");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "twitter":
                                if (vTwitter!=null &&vTwitter.equals("VISIBLE")) {
                                    Twitter.setVisibility(View.GONE);
                                    unlinkTwitter.setVisibility(View.VISIBLE);
                                    unlinkTwitter.setText("Unlink Twitter Click Here");
                                    unlinkTwitter.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkTwitterid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Twitter";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Twitter.setImageResource(R.drawable.ic_twitter);
                                    Twitter.setVisibility(View.VISIBLE);
                                    Twitter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Twitter");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "linkedin":
                                if (vLinkedIn!=null &&vLinkedIn.equals("VISIBLE")) {
                                    LinkedIn.setVisibility(View.GONE);
                                    unlinkLinkedIn.setVisibility(View.VISIBLE);
                                    unlinkLinkedIn.setText("Unlink LinkedIn Click Here");
                                    unlinkLinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLinkedInid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="LinkedIn";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    LinkedIn.setImageResource(R.drawable.ic_linkedin);
                                    LinkedIn.setVisibility(View.VISIBLE);
                                    LinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "LinkedIn");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;
                            case "yahoo":
                                if (vYahoo!=null && vYahoo.equals("VISIBLE")) {
                                    Yahoo.setVisibility(View.GONE);
                                    unlinkYahoo.setVisibility(View.VISIBLE);
                                    unlinkYahoo.setText("Unlink Yahoo Click Here");
                                    unlinkYahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkYahooid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Yahoo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Yahoo.setImageResource(R.drawable.ic_yahoo);
                                    Yahoo.setVisibility(View.VISIBLE);
                                    Yahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Yahoo");
                                            startActivityForResult(intent, 2);
                                        }
                                    });
                                }
                                break;

                            case "instagram":
                                if (vInstagram!=null &&vInstagram.equals("VISIBLE")) {
                                    Instagram.setVisibility(View.GONE);
                                    unlinkInstagram.setVisibility(View.VISIBLE);
                                    unlinkInstagram.setText("Unlink Instagram Click Here");
                                    unlinkInstagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkInstagramid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Instagram";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });

                                }else {
                                    Instagram.setImageResource(R.drawable.ic_instagram);
                                    Instagram.setVisibility(View.VISIBLE);
                                    Instagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Instagram");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "amazon":
                                if (vAmazon!=null&&vAmazon.equals("VISIBLE")) {
                                    Amazon.setVisibility(View.GONE);
                                    unlinkAmazon.setVisibility(View.VISIBLE);
                                    unlinkAmazon.setText("Unlink Amazon Click Here");
                                    unlinkAmazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAmazonid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Amazon";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Amazon.setImageResource(R.drawable.ic_amazon);
                                    Amazon.setVisibility(View.VISIBLE);
                                    Amazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Amazon");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "live":
                                if (vLive!=null&&vLive.equals("VISIBLE")) {
                                    Live.setVisibility(View.GONE);
                                    unlinkLive.setVisibility(View.VISIBLE);
                                    unlinkLive.setText("Unlink Live Click Here");
                                    unlinkLive.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Live";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Live.setImageResource(R.drawable.ic_live);
                                    Live.setVisibility(View.VISIBLE);
                                    Live.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Live");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "vkontakte":
                                if (vVkontakte!=null&&vVkontakte.equals("VISIBLE")) {
                                    Vkontakte.setVisibility(View.GONE);
                                    unlinkVkontakte.setVisibility(View.VISIBLE);
                                    unlinkVkontakte.setText("Unlink Vkontakte Click Here");
                                    unlinkVkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVkontakteid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Vkontakte";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Vkontakte.setImageResource(R.drawable.ic_vkontakte);
                                    Vkontakte.setVisibility(View.VISIBLE);
                                    Vkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Vkontakte");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "disqus":
                                if (vDisqus!=null&&vDisqus.equals("VISIBLE")) {
                                    Disqus.setVisibility(View.GONE);
                                    unlinkDisqus.setVisibility(View.VISIBLE);
                                    unlinkDisqus.setText("Unlink Disqus Click Here");
                                    unlinkDisqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkDisqusid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Disqus";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Disqus.setImageResource(R.drawable.ic_disqus);
                                    Disqus.setVisibility(View.VISIBLE);
                                    Disqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Disqus");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "aol":
                                if (vAOL!=null&&vAOL.equals("VISIBLE")) {
                                    AOL.setVisibility(View.GONE);
                                    unlinkAOL.setVisibility(View.VISIBLE);
                                    unlinkAOL.setText("Unlink AOL Click Here");
                                    unlinkAOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAOLid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="AOL";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    AOL.setImageResource(R.drawable.ic_aol);
                                    AOL.setVisibility(View.VISIBLE);
                                    AOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "AOL");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "pinterest":
                                if (vPinterest!=null&&vPinterest.equals("VISIBLE")) {
                                    Pinterest.setVisibility(View.GONE);
                                    unlinkPinterest.setVisibility(View.VISIBLE);
                                    unlinkPinterest.setText("Unlink Pinterest Click Here");
                                    unlinkPinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPinterestid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Pinterest";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Pinterest.setImageResource(R.drawable.ic_pinterest);
                                    Pinterest.setVisibility(View.VISIBLE);
                                    Pinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Pinterest");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "mixi":
                                if (vMixi!=null&&vMixi.equals("VISIBLE")) {
                                    Mixi.setVisibility(View.GONE);
                                    unlinkMixi.setVisibility(View.VISIBLE);
                                    unlinkMixi.setText("Unlink Mixi Click Here");
                                    unlinkMixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkMixiid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Mixi";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Mixi.setImageResource(R.drawable.ic_mixi);
                                    Mixi.setVisibility(View.VISIBLE);
                                    Mixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Mixi");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "steamcommunity":
                                if (vSteamcommunity!=null&&vSteamcommunity.equals("VISIBLE")) {
                                    Steamcommunity.setVisibility(View.GONE);
                                    unlinkSteamcommunity.setVisibility(View.VISIBLE);
                                    unlinkSteamcommunity.setText("Unlink Steamcommunity Click Here");
                                    unlinkSteamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSteamcommunityid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Steamcommunity";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Steamcommunity.setImageResource(R.drawable.ic_steamcommunity);
                                    Steamcommunity.setVisibility(View.VISIBLE);
                                    Steamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Steamcommunity");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "hyves":
                                if (vHyves!=null&&vHyves.equals("VISIBLE")) {
                                    Hyves.setVisibility(View.GONE);
                                    unlinkHyves.setVisibility(View.VISIBLE);
                                    unlinkHyves.setText("Unlink Hyves Click Here");
                                    unlinkHyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkHyvesid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Hyves";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Hyves.setImageResource(R.drawable.ic_hyves);
                                    Hyves.setVisibility(View.VISIBLE);
                                    Hyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Hyves");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "livejournal":
                                if (vLiveJournal!=null&&vLiveJournal.equals("VISIBLE")) {
                                    LiveJournal.setVisibility(View.GONE);
                                    unlinkLiveJournal.setVisibility(View.VISIBLE);
                                    unlinkLiveJournal.setText("Unlink LiveJournal Click Here");
                                    unlinkLiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveJournalid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="LiveJournal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    LiveJournal.setImageResource(R.drawable.ic_livejournal);
                                    LiveJournal.setVisibility(View.VISIBLE);
                                    LiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "LiveJournal");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "verisign":
                                if (vVerisign!=null&&vVerisign.equals("VISIBLE")) {
                                    Verisign.setVisibility(View.GONE);
                                    unlinkVerisign.setVisibility(View.VISIBLE);
                                    unlinkVerisign.setText("Unlink Verisign Click Here");
                                    unlinkVerisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVerisignid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Verisign";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Verisign.setImageResource(R.drawable.ic_verisign);
                                    Verisign.setVisibility(View.VISIBLE);
                                    Verisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Verisign");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "virgilio":
                                if (vVirgilio!=null&&vVirgilio.equals("VISIBLE")) {
                                    Virgilio.setVisibility(View.GONE);
                                    unlinkVirgilio.setVisibility(View.VISIBLE);
                                    unlinkVirgilio.setText("Unlink Virgilio Click Here");
                                    unlinkVirgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilioid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Virgilio";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });

                                }else {
                                    Virgilio.setImageResource(R.drawable.ic_virgilio);
                                    Virgilio.setVisibility(View.VISIBLE);
                                    Virgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Virgilio");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "foursquare":
                                if (vFourSquare!=null&&vFourSquare.equals("VISIBLE")) {
                                    FourSquare.setVisibility(View.GONE);
                                    unlinkFourSquare.setVisibility(View.VISIBLE);
                                    unlinkFourSquare.setText("Unlink FourSquare Click Here");
                                    unlinkFourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFourSquareid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="FourSquare";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    FourSquare.setImageResource(R.drawable.ic_foursquare);
                                    FourSquare.setVisibility(View.VISIBLE);
                                    FourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "FourSquare");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "github":
                                if (vGitHub!=null&&vGitHub.equals("VISIBLE")) {
                                    GitHub.setVisibility(View.GONE);
                                    unlinkGitHub.setVisibility(View.VISIBLE);
                                    unlinkGitHub.setText("Unlink GitHub Click Here");
                                    unlinkGitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGitHubid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="GitHub";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    GitHub.setImageResource(R.drawable.ic_github);
                                    GitHub.setVisibility(View.VISIBLE);
                                    GitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "GitHub");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "openid":
                                if (vOpenID!=null&&vOpenID.equals("VISIBLE")) {
                                    OpenID.setVisibility(View.GONE);
                                    unlinkOpenID.setVisibility(View.VISIBLE);
                                    unlinkOpenID.setText("Unlink OpenID Click Here");
                                    unlinkOpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOpenIDid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="OpenID";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    OpenID.setImageResource(R.drawable.ic_openid);
                                    OpenID.setVisibility(View.VISIBLE);
                                    OpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "OpenID");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            case "renren":
                                if (vRenren!=null&&vRenren.equals("VISIBLE")) {
                                    Renren.setVisibility(View.GONE);
                                    unlinkRenren.setVisibility(View.VISIBLE);
                                    unlinkRenren.setText("Unlink Renren Click Here");
                                    unlinkRenren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkRenrenid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Renren";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Renren.setImageResource(R.drawable.ic_renren);
                                    Renren.setVisibility(View.VISIBLE);
                                    Renren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Renren");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "kaixin":
                                if (vKaixin!=null&&vKaixin.equals("VISIBLE")) {
                                    Kaixin.setVisibility(View.GONE);
                                    unlinkKaixin.setVisibility(View.VISIBLE);
                                    unlinkKaixin.setText("Unlink Kaixin Click Here");
                                    unlinkKaixin.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkKaixinid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Kaixin";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Kaixin.setImageResource(R.drawable.ic_kaixin);
                                    Kaixin.setVisibility(View.VISIBLE);
                                    Kaixin.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Kaixin");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "qq":
                                if (vQq!=null&&vQq.equals("VISIBLE")) {
                                    Qq.setVisibility(View.GONE);
                                    unlinkQq.setVisibility(View.VISIBLE);
                                    unlinkQq.setText("Unlink Qq Click Here");
                                    unlinkQq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkQqid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Qq";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Qq.setImageResource(R.drawable.ic_qq);
                                    Qq.setVisibility(View.VISIBLE);
                                    Qq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Qq");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "stackexchange":
                                if (vStackexchange!=null&&vStackexchange.equals("VISIBLE")) {
                                    Stackexchange.setVisibility(View.GONE);
                                    unlinkStackexchange.setVisibility(View.VISIBLE);
                                    unlinkStackexchange.setText("Unlink Stackexchange Click Here");
                                    unlinkStackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkStackexchangeid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Stackexchange";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Stackexchange.setImageResource(R.drawable.ic_stackexchange);
                                    Stackexchange.setVisibility(View.VISIBLE);
                                    Stackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Stackexchange");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "salesforce":
                                if (vSalesforce!=null&&vSalesforce.equals("VISIBLE")) {
                                    Salesforce.setVisibility(View.GONE);
                                    unlinkSalesforce.setVisibility(View.VISIBLE);
                                    unlinkSalesforce.setText("Unlink Salesforce Click Here");
                                    unlinkSalesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSalesforceid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Salesforce";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Salesforce.setImageResource(R.drawable.ic_saleforce);
                                    Salesforce.setVisibility(View.VISIBLE);
                                    Salesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Salesforce");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "odnoklassniki":
                                if (vOdnoklassniki!=null&&vOdnoklassniki.equals("VISIBLE")) {
                                    Odnoklassniki.setVisibility(View.GONE);
                                    unlinkOdnoklassniki.setVisibility(View.VISIBLE);
                                    unlinkOdnoklassniki.setText("Unlink Odnoklassniki Click Here");
                                    unlinkOdnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOdnoklassnikiid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Odnoklassniki";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Odnoklassniki.setImageResource(R.drawable.ic_odnoklassniki);
                                    Odnoklassniki.setVisibility(View.VISIBLE);
                                    Odnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Odnoklassniki");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "paypal":
                                if (vPaypal!=null&&vPaypal.equals("VISIBLE")) {
                                    Paypal.setVisibility(View.GONE);
                                    unlinkPaypal.setVisibility(View.VISIBLE);
                                    unlinkPaypal.setText("Unlink Paypal Click Here");
                                    unlinkPaypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPaypalid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Paypal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Paypal.setImageResource(R.drawable.ic_paypal);
                                    Paypal.setVisibility(View.VISIBLE);
                                    Paypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Paypal");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "sinaweibo":
                                if (vSinaweibo!=null&&vSinaweibo.equals("VISIBLE")) {
                                    Sinaweibo.setVisibility(View.GONE);
                                    unlinkSinaweibo.setVisibility(View.VISIBLE);
                                    unlinkSinaweibo.setText("Unlink Sinaweibo Click Here");
                                    unlinkSinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSinaweiboid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Sinaweibo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Sinaweibo.setImageResource(R.drawable.ic_sina_weibo);
                                    Sinaweibo.setVisibility(View.VISIBLE);
                                    Sinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Sinaweibo");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "wordpress":
                                if (vWordpress!=null&&vWordpress.equals("VISIBLE")) {
                                    Wordpress.setVisibility(View.GONE);
                                    unlinkWordpress.setVisibility(View.VISIBLE);
                                    unlinkWordpress.setText("Unlink Wordpress Click Here");
                                    unlinkWordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkWordpressid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Wordpress";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Wordpress.setImageResource(R.drawable.ic_wordpress);
                                    Wordpress.setVisibility(View.VISIBLE);
                                    Wordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Wordpress");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "mailru":
                                if (vmailru!=null&&vmailru.equals("VISIBLE")) {
                                    mailru.setVisibility(View.GONE);
                                    unlinkmailru.setVisibility(View.VISIBLE);
                                    unlinkmailru.setText("Unlink mailru Click Here");
                                    unlinkmailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkmailruid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="mailru";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    mailru.setImageResource(R.drawable.ic_mail_ru);
                                    mailru.setVisibility(View.VISIBLE);
                                    mailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "mailru");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "xing":
                                if (vXing!=null&&vXing.equals("VISIBLE")) {
                                    Xing.setVisibility(View.GONE);
                                    unlinkXing.setText("Unlink Xing Click Here");
                                    unlinkXing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkXingid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Xing";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Xing.setImageResource(R.drawable.ic_xing);
                                    Xing.setVisibility(View.VISIBLE);
                                    Xing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Xing");
                                            startActivityForResult(intent, 2);

                                        }
                                    });}
                                break;
                            case "line":
                                if (vLine!=null&&vLine.equals("VISIBLE")) {
                                    Line.setVisibility(View.GONE);
                                    unlinkLine.setVisibility(View.VISIBLE);
                                    unlinkLine.setText("Unlink Line Click Here");
                                    unlinkLine.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLineid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Line";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                }else {
                                    Line.setImageResource(R.drawable.ic_line);
                                    Line.setVisibility(View.VISIBLE);
                                    Line.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            intent.putExtra("provider", "Line");
                                            startActivityForResult(intent, 2);
                                        }
                                    });}
                                break;
                            default:
                                break;
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
                AccessTokenResponse accessToken = new AccessTokenResponse();
                accessToken.access_token = token;
                accessToken.provider = provider;
                Link(accessToken);

            }  }
    }

    private void Link(final AccessTokenResponse token){
        final AuthenticationAPI api = new AuthenticationAPI();
        final QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(token.access_token);
        api.getSocialProfile(queryParams,new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(final LoginRadiusUltimateUserProfile ultimateUserProfile) {
                final String logintoken = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("login_token", "");
                JsonObject change = new JsonObject();
                change.addProperty("candidateToken",token.access_token);  // put your new provider token
                queryParams.setAccess_token(logintoken);
                api.linkAccount(queryParams,change,new AsyncHandler<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse registerResponse) {
                        if (registerResponse.getIsPosted()){
                            NotifyToastUtil.showNotify(ProfileActivity.this,"you have successfully link with " +token.provider);
                            final SharedPreferences.Editor editor = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).edit();
                            switch (token.provider.toLowerCase()){
                                case "facebook":
                                    Facebook.setVisibility(View.GONE);
                                    editor.putString("linkFacebookid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkFacebook.setVisibility(View.VISIBLE);
                                    unlinkFacebook.setText("Unlink Facebook Click Here");
                                    unlinkFacebook.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFacebookid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Facebook";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "google":
                                    Google.setVisibility(View.GONE);
                                    editor.putString("linkGoogleid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkGoogle.setVisibility(View.VISIBLE);
                                    unlinkGoogle.setText("Unlink Google Click Here");
                                    unlinkGoogle.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGoogleid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Google";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "twitter":
                                    Twitter.setVisibility(View.GONE);
                                    editor.putString("linkTwitterid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkTwitter.setVisibility(View.VISIBLE);
                                    unlinkTwitter.setText("Unlink Twitter Click Here");
                                    unlinkTwitter.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkTwitterid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Twitter";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "linkedin":
                                    LinkedIn.setVisibility(View.GONE);
                                    editor.putString("linkLinkedInid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLinkedIn.setVisibility(View.VISIBLE);
                                    unlinkLinkedIn.setText("Unlink LinkedIn Click Here");
                                    unlinkLinkedIn.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLinkedInid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="LinkedIn";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "yahoo":
                                    Yahoo.setVisibility(View.GONE);
                                    editor.putString("linkYahooid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkYahoo.setVisibility(View.VISIBLE);
                                    unlinkYahoo.setText("Unlink Yahoo Click Here");
                                    unlinkYahoo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkYahooid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Yahoo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "instagram":
                                    Instagram.setVisibility(View.GONE);
                                    editor.putString("linkInstagramid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkInstagram.setVisibility(View.VISIBLE);
                                    unlinkInstagram.setText("Unlink Instagram Click Here");
                                    unlinkInstagram.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkInstagramid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Instagram";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "amazon":
                                    Amazon.setVisibility(View.GONE);
                                    editor.putString("linkAmazonid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkAmazon.setVisibility(View.VISIBLE);
                                    unlinkAmazon.setText("Unlink Amazon Click Here");
                                    unlinkAmazon.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAmazonid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Amazon";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "live":
                                    Live.setVisibility(View.GONE);
                                    editor.putString("linkLiveid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLive.setVisibility(View.VISIBLE);
                                    unlinkLive.setText("Unlink Live Click Here");
                                    unlinkLive.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Live";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "vkontakte":
                                    Vkontakte.setVisibility(View.GONE);
                                    editor.putString("linkVkontakteid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVkontakte.setVisibility(View.VISIBLE);
                                    unlinkVkontakte.setText("Unlink Vkontakte Click Here");
                                    unlinkVkontakte.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVkontakteid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Vkontakte";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "disqus":
                                    Disqus.setVisibility(View.GONE);
                                    editor.putString("linkDisqusid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkDisqus.setVisibility(View.VISIBLE);
                                    unlinkDisqus.setText("Unlink Disqus Click Here");
                                    unlinkDisqus.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkDisqusid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Disqus";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "aol":
                                    AOL.setVisibility(View.GONE);
                                    editor.putString("linkAOLid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkAOL.setVisibility(View.VISIBLE);
                                    unlinkAOL.setText("Unlink AOL Click Here");
                                    unlinkAOL.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkAOLid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="AOL";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "pinterest":
                                    Pinterest.setVisibility(View.GONE);
                                    editor.putString("linkPinterestid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkPinterest.setVisibility(View.VISIBLE);
                                    unlinkPinterest.setText("Unlink Pinterest Click Here");
                                    unlinkPinterest.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPinterestid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Pinterest";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "mixi":
                                    Mixi.setVisibility(View.GONE);
                                    editor.putString("linkMixiid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkMixi.setVisibility(View.VISIBLE);
                                    unlinkMixi.setText("Unlink Mixi Click Here");
                                    unlinkMixi.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkMixiid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Mixi";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "steamcommunity":
                                    Steamcommunity.setVisibility(View.GONE);
                                    editor.putString("linkSteamcommunityid",ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkSteamcommunity.setVisibility(View.VISIBLE);
                                    unlinkSteamcommunity.setText("Unlink Steamcommunity Click Here");
                                    unlinkSteamcommunity.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSteamcommunityid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Steamcommunity";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "hyves":
                                    Hyves.setVisibility(View.GONE);
                                    editor.putString("linkHyvesid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkHyves.setVisibility(View.VISIBLE);
                                    unlinkHyves.setText("Unlink Hyves Click Here");
                                    unlinkHyves.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkHyvesid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Hyves";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "livejournal":
                                    LiveJournal.setVisibility(View.GONE);
                                    editor.putString("linkLiveJournalid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkLiveJournal.setVisibility(View.VISIBLE);
                                    unlinkLiveJournal.setText("Unlink LiveJournal Click Here");
                                    unlinkLiveJournal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLiveJournalid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="LiveJournal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "verisign":
                                    Verisign.setVisibility(View.GONE);
                                    editor.putString("linkVerisignid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVerisign.setVisibility(View.VISIBLE);
                                    unlinkVerisign.setText("Unlink Verisign Click Here");
                                    unlinkVerisign.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVerisignid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Verisign";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "virgilio":
                                    Virgilio.setVisibility(View.GONE);
                                    editor.putString("linkVirgilioid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkVirgilio.setVisibility(View.VISIBLE);
                                    unlinkVirgilio.setText("Unlink Virgilio Click Here");
                                    unlinkVirgilio.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linktonke = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilio", "");
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkVirgilioid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.access_token=linktonke;
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "foursquare":
                                    FourSquare.setVisibility(View.GONE);
                                    editor.putString("linkFourSquareid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkFourSquare.setVisibility(View.VISIBLE);
                                    unlinkFourSquare.setText("Unlink FourSquare Click Here");
                                    unlinkFourSquare.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkFourSquareid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="FourSquare";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "github":
                                    GitHub.setVisibility(View.GONE);
                                    editor.putString("linkGitHubid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkGitHub.setVisibility(View.VISIBLE);
                                    unlinkGitHub.setText("Unlink GitHub Click Here");
                                    unlinkGitHub.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkGitHubid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="GitHub";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "openid":
                                    OpenID.setVisibility(View.GONE);
                                    editor.putString("linkOpenIDid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkOpenID.setVisibility(View.VISIBLE);
                                    unlinkOpenID.setText("Unlink OpenID Click Here");
                                    unlinkOpenID.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOpenIDid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="OpenID";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "renren":
                                    Renren.setVisibility(View.GONE);
                                    editor.putString("linkRenrenid", ultimateUserProfile.ID);
                                    editor.commit();
                                    unlinkRenren.setVisibility(View.VISIBLE);
                                    unlinkRenren.setText("Unlink Renren Click Here");
                                    unlinkRenren.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkRenrenid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Renren";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "kaixin":
                                    Kaixin.setVisibility(View.GONE);
                                    editor.putString("linkKaixinid", token.id);
                                    editor.commit();
                                    unlinkKaixin.setVisibility(View.VISIBLE);
                                    unlinkKaixin.setText("Unlink Kaixin Click Here");
                                    unlinkKaixin.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkKaixinid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Kaixin";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "qq":
                                    Qq.setVisibility(View.GONE);
                                    editor.putString("linkQqid", token.id);
                                    editor.commit();
                                    unlinkQq.setVisibility(View.VISIBLE);
                                    unlinkQq.setText("Unlink Qq Click Here");
                                    unlinkQq.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkQqid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Qq";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "stackexchange":
                                    Stackexchange.setVisibility(View.GONE);
                                    editor.putString("linkStackexchangeid", token.id);
                                    editor.commit();
                                    unlinkStackexchange.setVisibility(View.VISIBLE);
                                    unlinkStackexchange.setText("Unlink Stackexchange Click Here");
                                    unlinkStackexchange.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkStackexchangeid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Stackexchange";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "salesforce":
                                    Salesforce.setVisibility(View.GONE);
                                    editor.putString("linkSalesforceid", token.id);
                                    editor.commit();
                                    unlinkSalesforce.setVisibility(View.VISIBLE);
                                    unlinkSalesforce.setText("Unlink Salesforce Click Here");
                                    unlinkSalesforce.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSalesforceid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Salesforce";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "odnoklassniki":
                                    Odnoklassniki.setVisibility(View.GONE);
                                    editor.putString("linkOdnoklassnikiid", token.id);
                                    editor.commit();
                                    unlinkOdnoklassniki.setVisibility(View.VISIBLE);
                                    unlinkOdnoklassniki.setText("Unlink Odnoklassniki Click Here");
                                    unlinkOdnoklassniki.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkOdnoklassnikiid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Odnoklassniki";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "paypal":
                                    Paypal.setVisibility(View.GONE);
                                    editor.putString("linkPaypalid", token.id);
                                    editor.commit();
                                    unlinkPaypal.setVisibility(View.VISIBLE);
                                    unlinkPaypal.setText("Unlink Paypal Click Here");
                                    unlinkPaypal.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkPaypalid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Paypal";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "sinaweibo":
                                    Sinaweibo.setVisibility(View.GONE);
                                    editor.putString("linkSinaweiboid", token.id);
                                    editor.commit();
                                    unlinkSinaweibo.setVisibility(View.VISIBLE);
                                    unlinkSinaweibo.setText("Unlink Sinaweibo Click Here");
                                    unlinkSinaweibo.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkSinaweiboid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Sinaweibo";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "wordpress":
                                    Wordpress.setVisibility(View.GONE);
                                    editor.putString("linkWordpressid", token.id);
                                    editor.commit();
                                    unlinkWordpress.setVisibility(View.VISIBLE);
                                    unlinkWordpress.setText("Unlink Wordpress Click Here");
                                    unlinkWordpress.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkWordpressid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
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
                                    unlinkmailru.setText("Unlink mailru Click Here");
                                    unlinkmailru.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkmailruid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="mailru";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "xing":
                                    Xing.setVisibility(View.GONE);
                                    editor.putString("linkXingid", token.id);
                                    editor.commit();
                                    unlinkXing.setVisibility(View.VISIBLE);
                                    unlinkXing.setText("Unlink Xing Click Here");
                                    unlinkXing.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkXingid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
                                            accessToken.provider="Xing";
                                            accessToken.id = linkid;
                                            Unlink(accessToken);
                                        }
                                    });
                                    break;
                                case "line":
                                    Line.setVisibility(View.GONE);
                                    editor.putString("linkLineid", token.id);
                                    editor.commit();
                                    unlinkLine.setVisibility(View.VISIBLE);
                                    unlinkLine.setText("Unlink Line Click Here");
                                    unlinkLine.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view) {
                                            final String linkid = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("linkLineid", "");
                                            AccessTokenResponse accessToken = new AccessTokenResponse();
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
                        NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());

                    }
                });
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());
            }
        });
    }





    private void Unlink(final AccessTokenResponse token){
        final String logintoken = getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, MODE_PRIVATE).getString("login_token", "");
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(logintoken);
        JsonObject change = new JsonObject();
        change.addProperty("Provider", token.provider.toLowerCase());
        change.addProperty("ProviderId", token.id); // put your new provider token
        api.unlinkAccount(queryParams, change,new AsyncHandler<DeleteResponse>() {
            @Override
            public void onSuccess(DeleteResponse deleteResponse) {
                if (deleteResponse.getIsDeleted()){
                    NotifyToastUtil.showNotify(ProfileActivity.this,"you have successfully Unlink with " +token.provider);
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
                NotifyToastUtil.showNotify(ProfileActivity.this,error.getMessage());

            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int addHeight = 0;
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            addHeight = listItem.getMeasuredHeight()*2;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + addHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}

