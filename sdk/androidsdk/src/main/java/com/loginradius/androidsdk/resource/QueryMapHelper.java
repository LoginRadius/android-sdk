package com.loginradius.androidsdk.resource;

import com.loginradius.androidsdk.helper.LoginRadiusSDK;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 13-Oct-17.
 */

public class QueryMapHelper {

    public static Map<String, String> getMapStatusUpdate(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", queryParams.getAccess_token());
        params.put("title", queryParams.getTitle());
        params.put("url", queryParams.getUrl());
        params.put("imageurl", queryParams.getImageUrl());
        params.put("status", queryParams.getStatus());
        params.put("caption", queryParams.getCaption());
        params.put("description", queryParams.getDescription());
        return params;
    }

    public static Map<String, String> getMapMessage(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", queryParams.getAccess_token());
        params.put("to", queryParams.getReceiver());
        params.put("subject", queryParams.getSubject());
        params.put("message", queryParams.getMessage());
        return params;
    }

    public static Map<String, String> getMapPage(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", queryParams.getAccess_token());
        params.put("pagename", queryParams.getPageName());
        return params;
    }

    public static Map<String, String> getMapPhoto(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", queryParams.getAccess_token());
        params.put("albumId", queryParams.getAlbumId());
        return params;
    }

    public static Map<String,String> getMapAddEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("verificationUrl",LoginRadiusSDK.getVerificationUrl());
        params.put("emailTemplate", (queryParams.getEmailTemplate()!=null?queryParams.getEmailTemplate():""));
        return params;
    }

    public static Map<String,String> getMapChangePassword(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapDeleteAccount(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("deletetoken", queryParams.getDeleteToken());
        return params;
    }

    public static Map<String,String> getMapDeleteAccountByConfirmEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("deleteurl",(queryParams.getDeleteUrl()!=null ? queryParams.getDeleteUrl():""));
        params.put("emailtemplate",queryParams.getEmailTemplate()!=null ? queryParams.getEmailTemplate():"");
        return params;
    }

    public static Map<String,String> getMapEmailAvailability(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("email", queryParams.getEmail());
        return params;
    }

    public static Map<String,String> getMapForgotPasswordByEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if(queryParams!=null){
            params.put("resetPasswordUrl", LoginRadiusSDK.getResetPasswordUrl());
            params.put("emailTemplate", (queryParams.getEmailTemplate()!=null?queryParams.getEmailTemplate():""));
        }
        return params;
    }

    public static Map<String,String> getMapForgotPasswordByPhone(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if(queryParams!=null){
            params.put("smsTemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        }
        return params;
    }

    public static Map<String,String> getMapSecurityQuestions(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",(queryParams.getAccess_token()!=null?queryParams.getAccess_token():""));
        if (queryParams.getPhone()!=null) {
            params.put("phone", queryParams.getPhone());
        }else if(queryParams.getUsername()!=null){
            params.put("username", queryParams.getUsername());
        }else if (queryParams.getEmail()!=null){
            params.put("email", queryParams.getEmail());
        }else {
            params.put("access_token", queryParams.getAccess_token());
        }
        return params;
    }

    public static Map<String,String> getMapSocialProfile(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        if(queryParams.getFields()!=null && queryParams.getFields().length>0){
            String fields[] = queryParams.getFields();
            String strFields = "";
            for(int i=0;i<fields.length;i++){
                if(i == (fields.length-1)){
                    strFields = strFields + fields[i];
                }else{
                    strFields = strFields + fields[i] + ",";
                }
            }
            params.put("fields",strFields);
        }
        return params;
    }

    public static Map<String,String> getMapInvalidateAccessToken(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapLink(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapLogin(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if(queryParams.getPhone()!=null){
            params.put("loginurl",(queryParams.getLoginUrl()!=null?queryParams.getLoginUrl():""));
            params.put("smstemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        }else if(queryParams.getUsername()!=null){
            params.put("verificationUrl", LoginRadiusSDK.getVerificationUrl());
            params.put("emailTemplate", (queryParams.getEmailTemplate()!=null?queryParams.getEmailTemplate():""));
        }else {
            params.put("verificationUrl", LoginRadiusSDK.getVerificationUrl());
            params.put("emailTemplate", (queryParams.getEmailTemplate()!=null?queryParams.getEmailTemplate():""));
        }
        return params;
    }

    public static Map<String,String> getMapOtpVerification(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("otp",queryParams.getOtp());
        return params;
    }

    public static Map<String,String> getMapOtpVerifyForgotPassword(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        return params;
    }

    public static Map<String,String> getMapPhoneLoginUsingOtp(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("smstemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        params.put("phone",queryParams.getPhone());
        params.put("otp",queryParams.getOtp());
        return params;
    }

    public static Map<String,String> getMapPhoneAvailability(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("phone",queryParams.getPhone());
        return params;
    }

    public static Map<String,String> getMapPhoneSendOtp(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("phone", queryParams.getPhone());
        params.put("smstemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        return params;
    }

    public static Map<String,String> getMapRegistration(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if (queryParams.getSmsTemplate()!=null){
            params.put("smsTemplate",queryParams.getSmsTemplate());
        }else {
            params.put("verificationUrl", LoginRadiusSDK.getVerificationUrl());
            params.put("emailTemplate", (queryParams.getEmailTemplate()!=null?queryParams.getEmailTemplate():""));
        }
        return params;
    }

    public static Map<String,String> getMapRemoveEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapResendEmailVerification(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("verificationUrl",LoginRadiusSDK.getVerificationUrl());
        if(queryParams!=null){
            if (queryParams.getEmailTemplate()!=null) {
                params.put("emailTemplate", queryParams.getEmailTemplate());
            }
        }
        return params;
    }

    public static Map<String,String> getMapResendOtp(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if(queryParams!=null && queryParams.getSmsTemplate()!=null){
            params.put("smstemplate",queryParams.getSmsTemplate());
        }
        return params;
    }

    public static Map<String,String> getMapResendOtpByToken(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token", queryParams.getAccess_token());
        params.put("smstemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        return params;
    }

    public static Map<String,String> getMapResetPasswordToken(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        return params;
    }

    public static Map<String,String> getMapResetPasswordSecurityQuestion(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        return params;
    }

    public static Map<String,String> getMapReadAllUserProfile(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        if(queryParams.getFields()!=null && queryParams.getFields().length>0){
            String fields[] = queryParams.getFields();
            String strFields = "";
            for(int i=0;i<fields.length;i++){
                if(i == (fields.length-1)){
                    strFields = strFields + fields[i];
                }else{
                    strFields = strFields + fields[i] + ",";
                }
            }
            params.put("fields",strFields);
        }
        return params;
    }

    public static Map<String,String> getMapUnlink(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapUpdatePhone(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapUpdateProfile(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("verificationUrl",LoginRadiusSDK.getVerificationUrl());
        if (queryParams.getEmailTemplate()!=null) {
            params.put("emailTemplate", queryParams.getEmailTemplate());
        }
        return params;
    }

    public static Map<String,String> getMapUpdateSecurityQuestionToken(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapUpdateUsername(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapCheckUsername(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("username", queryParams.getUsername());
        return params;
    }

    public static Map<String,String> getMapValidateAccessToken(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        return params;
    }

    public static Map<String,String> getMapVerifyEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("verificationtoken",queryParams.getVtoken());
        params.put("welcomeEmailTemplate", (queryParams.getWelcomeEmailTemplate()!=null?queryParams.getWelcomeEmailTemplate():""));
        params.put("url", LoginRadiusSDK.getVerificationUrl());
        return params;
    }

    public static Map<String,String> getMapVerifyOtpByToken(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token",queryParams.getAccess_token());
        params.put("smsTemplate",(queryParams.getSmsTemplate()!=null?queryParams.getSmsTemplate():""));
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("otp",queryParams.getOtp());
        return params;
    }

    public static Map<String, String> getMapSimplifiedRegistrationByEmail(QueryParams queryParams){
        String name = (queryParams.getName()!=null) ? queryParams.getName() : "";
        String redirecturl = (queryParams.getRedirecturl()!=null) ? queryParams.getRedirecturl() : "";
        String noregistrationemailtemplate=(queryParams.getNoregistrationemailtemplate()!=null) ? queryParams.getNoregistrationemailtemplate() : "";
        String welcomeemailtemplate=(queryParams.getWelcomeEmailTemplate()!=null) ? queryParams.getWelcomeEmailTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("email", queryParams.getEmail());
        params.put("clientguid", queryParams.getClientGuid());
        params.put("name", name);
        params.put("redirecturl", redirecturl);
        params.put("noregistrationemailtemplate", noregistrationemailtemplate);
        params.put("welcomeemailtemplate", welcomeemailtemplate);
        return params;
    }

    public static Map<String, String> getMapSimplifiedRegistrationByPhone(QueryParams queryParams){
        String name = (queryParams.getName()!=null) ? queryParams.getName() : "";
        String smstemplate = (queryParams.getSmsTemplate()!=null) ? queryParams.getSmsTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("phone", queryParams.getPhone());
        params.put("name", name);
        params.put("smstemplate", smstemplate);
        return params;
    }

    public static Map<String, String> getMapSimplifiedRegistrationOtpVerification(QueryParams queryParams){
        String smstemplate = (queryParams.getSmsTemplate()!=null) ? queryParams.getSmsTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey",LoginRadiusSDK.getApiKey());
        params.put("otp", queryParams.getOtp());
        params.put("smstemplate", smstemplate);
        return params;
    }

    public static Map<String, String> getMapCreateCustomObject(QueryParams queryParams){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("objectname",queryParams.getObjectname());
        return params;
    }

    public static Map<String, String> getMapDeleteCustomObject(QueryParams queryParams){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("objectname",queryParams.getObjectname());
        return params;
    }

    public static Map<String, String> getMapReadCustomObjectbyId(QueryParams queryParams){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("objectname",queryParams.getObjectname());
        return params;
    }

    public static Map<String, String> getMapReadCustomobjectByToken(QueryParams queryParams){
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("objectname",queryParams.getObjectname());
        return params;
    }

    public static Map<String, String> getMapUpdateCustomObject(QueryParams queryParams){
        Boolean updatetype = (queryParams.getUpdatetype()!=null) ? queryParams.getUpdatetype() : false;
        HashMap<String,String> params = new LinkedHashMap<>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("access_token",queryParams.getAccess_token());
        params.put("objectname",queryParams.getObjectname());
        if (updatetype){
            params.put("updatetype","replace");
        }else{
            params.put("updatetype","partialreplace");
        }
        return params;
    }

    public static Map<String, String> getMapEmailPromptAutoLogin(QueryParams queryParams){
        String autoLoginEmailTemplate = (queryParams.getAutoLoginEmailTemplate()!=null) ? queryParams.getAutoLoginEmailTemplate() : "";
        String welcomeEmailTemplate = (queryParams.getWelcomeEmailTemplate()!=null) ? queryParams.getWelcomeEmailTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        if (queryParams.getUsername()!=null){
            params.put("username",queryParams.getUsername());
        }else {params.put("email", queryParams.getEmail());}

        params.put("clientGuid", queryParams.getClientGuid());
        params.put("autoLoginEmailTemplate", autoLoginEmailTemplate);
        params.put("welcomeEmailTemplate", welcomeEmailTemplate);

        return  params;
    }

    public static Map<String, String> getMapEmailPromptAutoLoginPing(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("clientGuid", queryParams.getClientGuid());
        return  params;
    }

    public static Map<String, String> getMapVerifyAutoLoginEmail(QueryParams queryParams){
        String welcomeEmailTemplate = (queryParams.getWelcomeEmailTemplate()!=null) ? queryParams.getWelcomeEmailTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", LoginRadiusSDK.getApiKey());
        params.put("vtoken",queryParams.getVtoken());
        params.put("welcomeEmailTemplate", welcomeEmailTemplate);
        return  params;
    }

    public static Map<String,String> getMapInstantLoginByEmail(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey",LoginRadiusSDK.getApiKey());
        params.put("email",queryParams.getEmail());
        params.put("oneclicksignintemplate",(queryParams.getOneClickSignInTemplate()!=null?queryParams.getOneClickSignInTemplate():""));
        params.put("verificationurl",LoginRadiusSDK.getVerificationUrl());
        return params;
    }

    public static Map<String,String> getMapInstantLoginByUsername(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey",LoginRadiusSDK.getApiKey());
        params.put("username",queryParams.getUsername());
        params.put("oneclicksignintemplate",(queryParams.getOneClickSignInTemplate()!=null?queryParams.getOneClickSignInTemplate():""));
        params.put("verificationurl",LoginRadiusSDK.getVerificationUrl());
        return params;
    }

    public static Map<String,String> getMapInstantLoginVerify(QueryParams queryParams){
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey",LoginRadiusSDK.getApiKey());
        params.put("verificationtoken",queryParams.getVtoken());
        params.put("welcomeemailtemplate",(queryParams.getWelcomeEmailTemplate()!=null?queryParams.getWelcomeEmailTemplate():""));
        return params;
    }

    public static Map<String,String> getMapUserProfile(QueryParams queryParams){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",queryParams.getAccess_token());
        if(queryParams.getFields()!=null && queryParams.getFields().length>0){
            String fields[] = queryParams.getFields();
            String strFields = "";
            for(int i=0;i<fields.length;i++){
                if(i == (fields.length-1)){
                    strFields = strFields + fields[i];
                }else{
                    strFields = strFields + fields[i] + ",";
                }
            }
            params.put("fields",strFields);
        }
        return params;
    }
}