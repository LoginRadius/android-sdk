package com.loginradius.userregistration.module;

public class Option {
        private String API_KEY;
        private String APP_Name;
    private String Prompt_password;
    private String recaptchakey;
    private String facebooknativetoken;

        public String getAPI_KEY() {
            return API_KEY;
        }

        public void setAPI_KEY(String API_KEY) {
            this.API_KEY = API_KEY;
        }

        public String getAPP_Name() {

            return APP_Name;
        }

        public void setAPP_Name(String APP_Name) {
            this.APP_Name = APP_Name;
        }

    public String getPrompt_password() {

        return Prompt_password;
    }

    public void setPrompt_password(String Prompt_password) {
        this.Prompt_password =Prompt_password;
    }
    public String getrecaptchakey() {

        return recaptchakey;
    }

    public void setrecaptchakey(String recaptchakey) {
        this.recaptchakey =recaptchakey;
    }

    public String getfacebooknativetoken() {

        return facebooknativetoken;
    }

    public void setfacebooknativetoken(String facebooknativetoken) {
        this.facebooknativetoken = facebooknativetoken;
    }

}

