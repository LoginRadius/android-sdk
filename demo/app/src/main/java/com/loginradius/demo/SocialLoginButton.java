package com.loginradius.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by loginradius on 9/18/2017.
 */

public class SocialLoginButton extends LinearLayout{

    private LinearLayout linearSocial;
    private TextView tvTitle;
    private ImageView ivIcon;

    public SocialLoginButton(Context context) {
        super(context);
    }

    public SocialLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SocialLoginButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public SocialLoginButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()){
            linearSocial = (LinearLayout)findViewById(R.id.linearSocial);
            tvTitle = (TextView)findViewById(R.id.tvTitle);
            ivIcon = (ImageView)findViewById(R.id.ivIcon);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        linearSocial.setOnClickListener(l);
    }

    public void setContent(String provider){
        switch (provider.toLowerCase()){
            case "facebook":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.facebookcolor));
                ivIcon.setImageResource(R.drawable.ic_facebook);
                break;
            case "google":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.googlecolor));
                ivIcon.setImageResource(R.drawable.ic_google);
                break;
            case "linkedin":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.linkedincolor));
                ivIcon.setImageResource(R.drawable.ic_linkedin);
                break;
            case "twitter":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.twittercolor));
                ivIcon.setImageResource(R.drawable.ic_twitter);
                break;
            case "yahoo":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.yahoocolor));
                ivIcon.setImageResource(R.drawable.ic_yahoo);
                break;
            case "amazon":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.amazoncolor));
                ivIcon.setImageResource(R.drawable.ic_amazon);
                break;
            case "aol":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.aolcolor));
                ivIcon.setImageResource(R.drawable.ic_aol);
                break;
            case "disqus":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.disquscolor));
                ivIcon.setImageResource(R.drawable.ic_disqus);
                break;
            case "foursquare":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.foursquarecolor));
                ivIcon.setImageResource(R.drawable.ic_foursquare);
                break;
            case "github":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.githubcolor));
                ivIcon.setImageResource(R.drawable.ic_github);
                break;
            case "hyves":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.hyvescolor));
                ivIcon.setImageResource(R.drawable.ic_hyves);
                break;
            case "instagram":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.instagramcolor));
                ivIcon.setImageResource(R.drawable.ic_instagram);
                break;
            case "kaixin":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.kaixincolor));
                ivIcon.setImageResource(R.drawable.ic_kaixin);
                break;
            case "line":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.linecolor));
                ivIcon.setImageResource(R.drawable.ic_line);
                break;
            case "live":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.livecolor));
                ivIcon.setImageResource(R.drawable.ic_live);
                break;
            case "livejournal":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.livejournalcolor));
                ivIcon.setImageResource(R.drawable.ic_livejournal);
                break;
            case "mixi":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.mixicolor));
                ivIcon.setImageResource(R.drawable.ic_mixi);
                break;
            case "odnoklassniki":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.odnoklassnikicolor));
                ivIcon.setImageResource(R.drawable.ic_odnoklassniki);
                break;
            case "openid":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.openidcolor));
                ivIcon.setImageResource(R.drawable.ic_openid);
                break;
            case "paypal":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.paypalcolor));
                ivIcon.setImageResource(R.drawable.ic_paypal);
                break;
            case "pinterest":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.pinterestcolor));
                ivIcon.setImageResource(R.drawable.ic_pinterest);
                break;
            case "qq":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.qqcolor));
                ivIcon.setImageResource(R.drawable.ic_qq);
                break;
            case "renren":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.renrencolor));
                ivIcon.setImageResource(R.drawable.ic_renren);
                break;
            case "salesforce":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.salesforcecolor));
                ivIcon.setImageResource(R.drawable.ic_saleforce);
                break;
            case "sinaweibo":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.sinaweibocolor));
                ivIcon.setImageResource(R.drawable.ic_sina_weibo);
                break;
            case "stackexchange":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.stackexchangecolor));
                ivIcon.setImageResource(R.drawable.ic_stackexchange);
                break;
            case "steamcommunity":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.steamcommunitycolor));
                ivIcon.setImageResource(R.drawable.ic_steamcommunity);
                break;
            case "verisign":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.verisigncolor));
                ivIcon.setImageResource(R.drawable.ic_verisign);
                break;
            case "virgilio":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.virgiliocolor));
                ivIcon.setImageResource(R.drawable.ic_virgilio);
                break;
            case "vkontakte":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.vkontaktecolor));
                ivIcon.setImageResource(R.drawable.ic_vkontakte);
                break;
            case "wordpress":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.wordpresscolor));
                ivIcon.setImageResource(R.drawable.ic_wordpress);
                break;
            case "mailru":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.mailrucolor));
                ivIcon.setImageResource(R.drawable.ic_mail_ru);
                break;
            case "xing":
                linearSocial.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.xingcolor));
                ivIcon.setImageResource(R.drawable.ic_xing);
                break;
        }
        tvTitle.setText("SIGN IN WITH "+provider.toUpperCase());
    }
}
