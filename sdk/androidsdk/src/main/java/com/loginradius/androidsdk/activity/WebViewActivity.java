package com.loginradius.androidsdk.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.handler.URLHelper;


public class WebViewActivity extends ActionBarActivity {
    private WebView webView;
    String apikey, sitename, provider, spinview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        spinview = "false";
        webView = (WebView) findViewById(R.id.webView1);
        Intent i = getIntent();
        provider = i.getStringExtra("provider");
        apikey = getIntent().getExtras().getString("apikey");
        sitename = getIntent().getExtras().getString("sitename");
        String sitebuilder = "https://" + sitename;
        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("apikey", apikey);
        params.put("provider", provider.toLowerCase());
        params.put("is_access_token", "true");
        params.put("ismobile", "true");
        String encodedURL = sitebuilder + URLHelper.URLBuilder(Endpoint.webviewlogin, params);
        if (i != null) {
            startWebView(encodedURL);
        }

    }

   private void startWebView(String url) {
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("?token")) {
                    Uri uri = Uri.parse(url);
                    String Token = uri.getQueryParameter("token");
                    if (Token != null) {
                        String[] temp = Token.split("#");
                        Token = temp[0];
                        final lrAccessToken accessToken = new lrAccessToken();
                        accessToken.access_token = Token;
                        accessToken.provider = provider;
                        accessToken.apikey = apikey;
                        sendAccessToken(accessToken);
                    }
                } else if (url.contains("error=access_denied"))
                {
                    finish();
                }else
                {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
    
    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        // Let the system handle the back button
        super.onBackPressed();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sendAccessToken(lrAccessToken accessToken) {
        Intent intent = new Intent();
        intent.putExtra("accesstoken", accessToken.access_token);
        intent.putExtra("provider", accessToken.provider);
        setResult(2, intent);
        finish();//finishing activity

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (data != null) {
                String token = data.getStringExtra("accesstoken");
                String provider = data.getStringExtra("provider");
                Intent intent = new Intent();
                intent.putExtra("accesstoken", token);
                intent.putExtra("provider", provider);
                setResult(2, intent);
                finish();//finishing activity
            }
        }
    }

}

