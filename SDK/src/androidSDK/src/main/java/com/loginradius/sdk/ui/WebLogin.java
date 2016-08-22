package com.loginradius.sdk.ui;

import com.loginradius.sdk.R;
import com.loginradius.sdk.models.lrAccessToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Handles user login to a standard provider
 *
 */
public class WebLogin extends Activity {
    /* KEY_URL used to get the string from the weblogin activity */
    public static final String KEY_URL = "ui.WebLogin.Url";

    /* KEY_PROVIDER used to get the provider name from weblogin activity */
    public static final String KEY_PROVIDER = "ui.WebLogin.Provider";

    private static ProgressBar progressBar;
    private static WebView webView;
    private static lrAccessToken token;

    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lr_login_webview);

        final WebLogin activity = this;

        /** From intent **/
        String url = getIntent().getStringExtra(KEY_URL);
        final String provider = getIntent().getStringExtra(KEY_PROVIDER);


        /** Layout items **/
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        webView = (WebView) findViewById(R.id.login_webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.requestFocus(View.FOCUS_DOWN);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                startSpinner();
                activity.setProgress(newProgress*100);
                if (newProgress == 100) {
                    activity.setTitle(R.string.app_name);
                    stopSpinner();
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                activity.setTitle("Loading...Please wait");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                if (url.contains("?token")) {
                    int in = url.indexOf("?token");
                    token = new lrAccessToken();
                    token.access_token = url.substring(in + 7, in + 43);
                    token.provider = provider;
                    token.expires_in = "";
                    lrLoginManager.asyncHandler.onSuccess(token);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.finish();
        lrLoginManager.asyncHandler.onFailure(new Throwable("WebLogin cancelled"), "lr_LOGIN_CANCELLED");
    }

    private static void startSpinner() {progressBar.setVisibility(WebView.VISIBLE);}
    private static void stopSpinner() {progressBar.setVisibility(WebView.GONE);}

}