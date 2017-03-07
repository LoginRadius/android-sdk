package com.loginradius.androidsdk.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.resource.CustomizeLanguageResponse;
import com.loginradius.androidsdk.resource.DefaultLanguage;
import com.loginradius.androidsdk.response.socialinterface.Provider;
import com.loginradius.androidsdk.api.SocailInterfaceAPI;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;


public class SocialLoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private List<String> info;
    private List<Provider> providers;
    private LinearLayout layout;
    private CoordinatorLayout coordinatorLayout;
    private int internet=0;
    String lrsitename,google_native,facebook_native,lrapi,verificationUrl,emailTemplate,smsTemplate,isMobile,promptPasswordOnSocialLogin,CustomizeLanguage,userlogin;
    CustomizeLanguageResponse customizeLanguageResponse;
    String facebook,google,UsernameLogin,promptPassword,Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_social_login);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("keyname")) {
                lrapi = extras.getString("keyname").replaceAll("\\s+","");
                if (lrapi.contains("yourloginradiusapikey")){
                    Toast.makeText(SocialLoginActivity.this, "Please Set Your ApiKey", Toast.LENGTH_LONG).show();
                }
            }else{
                lrapi ="";
            } if (extras.containsKey("sitename")) {
                lrsitename = extras.getString("sitename").replaceAll("\\s+","");
            }else {
                lrsitename="";
            }
            if (extras.containsKey("verificationurl")) {
                verificationUrl = extras.getString("verificationurl").replaceAll("\\s+","");
            } if (extras.containsKey("emailtemplate")) {
                emailTemplate = extras.getString("emailtemplate").replaceAll("\\s+","");
            }if (extras.containsKey("ismobile")) {
                Mobile = extras.getString("ismobile").replaceAll("\\s+","");
            }if (extras.containsKey("smstemplate")) {
                smsTemplate = extras.getString("smstemplate").replaceAll("\\s+","");
            }if (extras.containsKey("facebooknative")) {
                facebook = extras.getString("facebooknative").replaceAll("\\s+","");
            }if (extras.containsKey("googlenative")) {
                google = extras.getString("googlenative").replaceAll("\\s+","");
            }if (extras.containsKey("usernamelogin")) {
                UsernameLogin = extras.getString("usernamelogin").replaceAll("\\s+","");
            }if (extras.containsKey("promptpasswordonsociallogin")) {
                promptPassword  = extras.getString("promptpasswordonsociallogin").replaceAll("\\s+","");
            }if (extras.containsKey("language")) {
                CustomizeLanguage  = extras.getString("language").replaceAll("\\s+","");
            }
        }
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);
        DefaultLanguage defaultlang=new DefaultLanguage();
        if (CustomizeLanguage==null || CustomizeLanguage.equals("")){
            customizeLanguageResponse = JsonDeserializer.deserializeJson(defaultlang.getLanguage(), CustomizeLanguageResponse.class);
        } else{
            customizeLanguageResponse = JsonDeserializer.deserializeJson(CustomizeLanguage, CustomizeLanguageResponse.class);
        }

        if (facebook != null && facebook.equals("true")) {
            facebook_native = facebook;
        } else {
            facebook_native = "false";
        }
        if (google != null && google.equals("true")) {
            google_native = google;
        } else {
            google_native = "false";
        }if (promptPassword != null && promptPassword.equals("true")) {
            promptPasswordOnSocialLogin = promptPassword;
        } else {
            promptPasswordOnSocialLogin = "false";

        } if(UsernameLogin!=null &&UsernameLogin.equals("true")){
            isMobile="false";
            userlogin=UsernameLogin;

        }
        layout = (LinearLayout)findViewById(R.id.social);
        if (lrapi.equals("your loginradius api key")||lrapi.equals("")){
            Toast.makeText(SocialLoginActivity.this, "Your LoginRadius API Key Not Valid", Toast.LENGTH_LONG).show();
        }else if(isNetworkAvailable()){
            socialLogin();
        }else{
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Check Your Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }}

    private void socialLogin(){
        LoginParams value = new LoginParams();
        value.apikey=lrapi;
        final SocailInterfaceAPI socialapi = new SocailInterfaceAPI();
        showProgressDialog();
        socialapi.getResponse(value,new AsyncHandler<SocialInterface>() {
            @Override
            public void onSuccess(SocialInterface socialInterface) {
                providers = socialInterface.getProviders();
                if(providers !=null && providers.size() >0 ) {
                    for (Provider provider : providers) {
                        switch (provider.getName()){
                            case "Facebook":
                                addButton(provider.getName(), R.drawable.facebook, R.color.facebookcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (facebook_native!=null && facebook_native.equals("true")) {
                                            Intent intent = new Intent(getApplication(), FacebookNativeActivity.class);
                                            intent.putExtra("keyName",lrapi);
                                            intent.putExtra("sitename",lrsitename);
                                            intent.putExtra("verificationurl",verificationUrl);
                                            intent.putExtra("emailtemplate",emailTemplate);
                                            intent.putExtra("ismobile",isMobile);
                                            intent.putExtra("smstemplate",smsTemplate);
                                            intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
                                            intent.putExtra("language",CustomizeLanguage);
                                            intent.putExtra("usernamelogin",userlogin);
                                            startActivityForResult(intent, 2);
                                        }else {
                                            startActivityForResult(intentValue().putExtra("action", "Facebook"), 2);
                                        }
                                    }
                                });
                                break;
                            case "Google":
                                addButton(provider.getName(), R.drawable.google, R.color.googlecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(google_native!=null && google_native.equals("true") &&checkPlayServices()){
                                            Intent intent = new Intent(getApplication(), GoogleNativeActivity.class);
                                            intent.putExtra("keyName",lrapi);
                                            intent.putExtra("sitename",lrsitename);
                                            intent.putExtra("verificationurl",verificationUrl);
                                            intent.putExtra("emailtemplate",emailTemplate);
                                            intent.putExtra("ismobile",isMobile);
                                            intent.putExtra("smstemplate",smsTemplate);
                                            intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
                                            intent.putExtra("language",CustomizeLanguage);
                                            intent.putExtra("usernamelogin",userlogin);
                                            startActivityForResult(intent, 2);
                                        }else {
                                            startActivityForResult(intentValue().putExtra("action", "Google"), 2);
                                        }
                                    }
                                });
                                break;
                            case "Twitter":
                                addButton(provider.getName(), R.drawable.twitter, R.color.twittercolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Twitter"), 2);
                                    }
                                });
                                break;
                            case "LinkedIn":
                                addButton(provider.getName(), R.drawable.linkedin,R.color.linkedincolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "LinkedIn"), 2);
                                    }
                                });
                                break;
                            case "Yahoo":
                                addButton(provider.getName(), R.drawable.yahoo,R.color.yahoocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Yahoo"), 2);
                                    }
                                });
                                break;

                            case "Instagram":
                                addButton(provider.getName(), R.drawable.instagram,R.color.instagramcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Instagram"), 2);
                                    }
                                });
                                break;
                            case "Amazon":
                                addButton(provider.getName(), R.drawable.amazon,R.color.amazoncolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Amazon"), 2);
                                    }
                                });
                                break;
                            case "Live":
                                addButton(provider.getName(), R.drawable.live,R.color.livecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Live"), 2);
                                    }
                                });
                                break;
                            case "Vkontakte":
                                addButton(provider.getName(), R.drawable.vkontakte,R.color.vkontaktecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Vkontakte"), 2);
                                    }
                                });
                                break;
                            case "Disqus":
                                addButton(provider.getName(), R.drawable.disqus,R.color.disquscolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Disqus"), 2);
                                    }
                                });
                                break;
                            case "AOL":
                                addButton(provider.getName(), R.drawable.aol,R.color.aolcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "AOL"), 2);
                                    }
                                });
                                break;
                            case "Pinterest":
                                addButton(provider.getName(), R.drawable.pinterest, R.color.pinterestcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Pinterest"), 2);
                                    }
                                });
                                break;
                            case "Mixi":
                                addButton(provider.getName(), R.drawable.mixi,R.color.mixicolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Mixi"), 2);
                                    }
                                });
                                break;
                            case "Steamcommunity":
                                addButton(provider.getName(), R.drawable.steamcommunity,R.color.steamcommunitycolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Steamcommunity"), 2);
                                    }
                                });
                                break;
                            case "Hyves":
                                addButton(provider.getName(), R.drawable.hyves, R.color.hyvescolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Hyves"), 2);
                                    }
                                });
                                break;
                            case "LiveJournal":
                                addButton(provider.getName(), R.drawable.livejournal,R.color.livejournalcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "LiveJournal"), 2);
                                    }
                                });
                                break;
                            case "Verisign":
                                addButton(provider.getName(), R.drawable.verisign,R.color.verisigncolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Verisign"), 2);
                                    }
                                });
                                break;
                            case "Virgilio":
                                addButton(provider.getName(), R.drawable.virgilio,R.color.virgiliocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Virgilio"), 2);
                                    }
                                });
                                break;
                            case "FourSquare":
                                addButton(provider.getName(), R.drawable.foursquare, R.color.foursquarecolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "FourSquare"), 2);
                                    }
                                });
                                break;
                            case "GitHub":
                                addButton(provider.getName(), R.drawable.github, R.color.githubcolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "GitHub"), 2);
                                    }
                                });
                                break;
                            case "OpenID":
                                addButton(provider.getName(), R.drawable.openid,R.color.openidcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "OpenID"), 2);
                                    }
                                });
                                break;
                            case "Renren":
                                addButton(provider.getName(), R.drawable.renren,R.color.renrencolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Renren"), 2);
                                    }
                                });
                                break;
                            case "Kaixin":
                                addButton(provider.getName(), R.drawable.kaixin, R.color.kaixincolor,new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Kaixin"), 2);
                                    }
                                });
                                break;
                            case "Qq":
                                addButton(provider.getName(), R.drawable.qq,R.color.qqcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Qq"), 2);
                                    }
                                });
                                break;
                            case "Stackexchange":
                                addButton(provider.getName(), R.drawable.stackexchange,R.color.stackexchangecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Stackexchange"), 2);
                                    }
                                });
                                break;
                            case "Salesforce":
                                addButton(provider.getName(), R.drawable.salesforce,R.color.salesforcecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Salesforce"), 2);
                                    }
                                });
                                break;
                            case "Odnoklassniki":
                                addButton(provider.getName(), R.drawable.odnoklassniki,R.color.odnoklassnikicolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Odnoklassniki"), 2);
                                    }
                                });
                                break;
                            case "Paypal":
                                addButton(provider.getName(), R.drawable.paypal,R.color.paypalcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Paypal"), 2);
                                    }
                                });
                                break;
                            case "Sinaweibo":
                                addButton(provider.getName(), R.drawable.sinaweibo,R.color.sinaweibocolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Sinaweibo"), 2);
                                    }
                                });
                                break;
                            case "Wordpress":
                                addButton(provider.getName(), R.drawable.wordpress,R.color.wordpresscolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Wordpress"), 2);
                                    }
                                });
                                break;
                            case "mailru":
                                addButton(provider.getName(), R.drawable.mailru,R.color.mailrucolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "mailru"), 2);
                                    }
                                });
                                break;
                            case "Xing":
                                addButton(provider.getName(), R.drawable.xing,R.color.xingcolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Xing"), 2);
                                    }
                                });
                                break;
                            case "Line":
                                addButton(provider.getName(), R.drawable.line,R.color.linecolor, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivityForResult(intentValue().putExtra("action", "Line"), 2);
                                    }
                                });
                                break;
                        }

                        hideProgressDialog();
                        internet=1;
                    }
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                internet=1;
                ErrorResponse errorResponse=JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);

                Object[] ad = ((ArrayList) customizeLanguageResponse.getErrorMessage()).toArray();
                for (Object i : ad) {
                    LinkedTreeMap customerror = (LinkedTreeMap) i;
                    if (customerror.containsKey("error" + errorResponse.getErrorCode())) {
                        Toast.makeText(SocialLoginActivity.this, customerror.get("error" + errorResponse.getErrorCode()).toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SocialLoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        long delayInMillis = 15000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (internet!=1) {
                    hideProgressDialog();
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Your Internet Connection Is Too Slow", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{

                }
            }
        }, delayInMillis);
    }



    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if(pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }


    private void addButton(String name, int imageIcon,int color, View.OnClickListener onClickListener) {
        View child = getLayoutInflater().inflate(R.layout.socialbuttons, null);
        LinearLayout button = (LinearLayout) child.findViewById(R.id.button);
        ImageView image=(ImageView)child.findViewById(R.id.socialimg);
        TextView text=(TextView)child.findViewById(R.id.socialtext);
        button.setOnClickListener(onClickListener);
        image.setBackgroundDrawable(getResources().getDrawable(imageIcon));
        button.setBackgroundColor(getResources().getColor(color));
        if (!(customizeLanguageResponse.getSocialprovider().equals(null))){
            text.setText(customizeLanguageResponse.getSocialprovider()+" "+name);
        }else {
            text.setText("Sign Up with "+name);
        }
        layout.addView(child);

    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
            } else {
                //  Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private Intent intentValue(){
        Intent intent = new Intent(getApplication(), WebViewActivity.class);
        intent.putExtra("keyName",lrapi);
        intent.putExtra("sitename",lrsitename);
        intent.putExtra("verificationurl",verificationUrl);
        intent.putExtra("emailtemplate",emailTemplate);
        intent.putExtra("ismobile",isMobile);
        intent.putExtra("smstemplate",smsTemplate);
        intent.putExtra("promptpasswordonsociallogin",promptPasswordOnSocialLogin);
        intent.putExtra("language",CustomizeLanguage);
        intent.putExtra("usernamelogin",userlogin);
        return intent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2) {
            if (data!=null) {
                String token = data.getStringExtra("accesstoken");
                String provider=data.getStringExtra("provider");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                setResult(2, intent);
                finish();//finishing activity

            }
        }
    }

}

