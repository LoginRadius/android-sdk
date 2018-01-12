package com.loginradius.androidsdk.resource;

/**
 * Created by loginradius on 9/19/2017.
 */

public enum SocialProviderConstant {
    FACEBOOK("facebook"),
    GOOGLE("google"),
    LINKEDIN("linkedin"),
    TWITTER("twitter"),
    YAHOO("yahoo"),
    AMAZON("amazon"),
    AOL("aol"),
    DISQUS("disqus"),
    FOURSQUARE("foursquare"),
    GITHUB("github"),
    HYVES("hyves"),
    INSTAGRAM("instagram"),
    KAIXIN("kaixin"),
    LINE("line"),
    LIVE("live"),
    LIVEJOURNAL("livejournal"),
    MIXI("mixi"),
    ODNOKLASSNIKI("odnoklassniki"),
    OPENID("openid"),
    PAYPAL("paypal"),
    PINTEREST("pinterest"),
    QQ("qq"),
    RENREN("renren"),
    SALESFORCE("salesforce"),
    SINAWEIBO("sinaweibo"),
    STACKEXCHANGE("stackexchange"),
    STEAMCOMMUNITY("steamcommunity"),
    VERISIGN("verisign"),
    VIRGILIO("virgilio"),
    VKONTAKTE("vkontakte"),
    WORDPRESS("wordpress"),
    MAILRU("mailru"),
    XING("xing");


    public String value;

    SocialProviderConstant(String value) {
        this.value = value;
    }
}