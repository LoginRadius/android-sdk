package com.loginradius.androidsdk.resource;

public class BiometricPromptBuilder {

    private String title;
    private String subtitle;
    private String negativeButtonName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNegativeButtonName() {
        return negativeButtonName;
    }

    public void setNegativeButtonName(String negativeButtonName) {
        this.negativeButtonName = negativeButtonName;
    }
}