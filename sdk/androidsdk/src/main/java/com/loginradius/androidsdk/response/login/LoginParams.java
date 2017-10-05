package com.loginradius.androidsdk.response.login;

/**
 * Created by loginradius on 8/1/2016.
 */
public class LoginParams {

    public String sott;
    public String email;
    public String phone;
    public String username;
    //public Provider provider;
    public String otp;
    public String password;
    public String apikey;
    public String emailTemplate;

    public String resetPasswordUrl;
    public String smsTemplate;
    public String loginUrl;
    public String verificationUrl;
    public String clientGuid;

    public String autoLoginEmailTemplate;
    public String welcomeEmailTemplate;
    public String redirecturl;
    public String noregistrationemailtemplate;
    public String name;

    public String objectname;
    public String objectRecordId;
    public Boolean updatetype;

    public String access_token;

    public String vtoken;

    public String getSott() {
        return sott;
    }

    public void setSott(String sott) {
        this.sott = sott;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;

    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;

    }


    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;

    }

    public String getResetPasswordUrl() {
        return resetPasswordUrl;
    }

    public void setResetPasswordUrl(String resetPasswordUrl) {
        this.resetPasswordUrl = resetPasswordUrl;

    }


    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;

    }


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;

    }

    public String getVerificationUrl() {
        return verificationUrl;
    }

    public void setVerificationUrl(String verificationUrl) {
        this.verificationUrl = verificationUrl;

    }

    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }


    public String getObjectRecordId() {
        return objectRecordId;
    }

    public void setObjectRecordId(String objectRecordId) {
        this.objectRecordId = objectRecordId;
    }


    public Boolean getUpdatetype() {
        return updatetype;
    }

    public void setUpdatetype(Boolean updatetype) {
        this.updatetype = updatetype;
    }


    public String getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }


    public String getAutoLoginEmailTemplate() {
        return autoLoginEmailTemplate;
    }

    public void setAutoLoginEmailTemplate(String autoLoginEmailTemplate) {
        this.autoLoginEmailTemplate = autoLoginEmailTemplate;
    }


    public String getWelcomeEmailTemplate() {
        return welcomeEmailTemplate ;
    }

    public void setWelcomeEmailTemplate(String welcomeEmailTemplate ) {
        this.welcomeEmailTemplate  = welcomeEmailTemplate ;
    }


    public String getNoregistrationemailtemplate() {
        return noregistrationemailtemplate ;
    }

    public void setNoregistrationemailtemplate(String noregistrationemailtemplate ) {
        this.noregistrationemailtemplate  = noregistrationemailtemplate ;
    }

    public String getRedirecturl() {
        return redirecturl ;
    }

    public void setRedirecturl(String redirecturl ) {
        this.redirecturl  = redirecturl ;
    }

    public String getName() {
        return welcomeEmailTemplate ;
    }

    public void setName(String name ) {
        this.name  = name ;
    }


    public String getAccess_token() {
        return access_token ;
    }

    public void setAccess_token(String access_token ) {
        this.access_token  = access_token ;
    }

    public String getVtoken() {
        return vtoken;
    }

    public void setVtoken(String vtoken) {
        this.vtoken = vtoken;
    }
}
