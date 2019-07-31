package com.loginradius.androidsdk.resource;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;



public class LoginUtil {
    Context context;
    Gson gson = new Gson();
    SharedPreferences sharedPreferences;

    public interface LogoutCallBack{
        public void Response(Boolean isLogout,Boolean isError,ErrorResponse error);
    }

    /**
     * Constructor
     * @param context
     */
    public LoginUtil(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(Endpoint.SHAREDPREFERENCEFILEKEY, Context.MODE_PRIVATE);
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean("isLogin",false);
    }

    public Boolean setLogin(String accessToken){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("access_token",accessToken);
        editor.apply();
        return true;
    }

    public Boolean setLogin(String accessToken,Object profile){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("access_token",accessToken);
        editor.putString("profile",gson.toJson(profile));
        editor.apply();
        return true;
    }

    public String getAccessToken(){
        return sharedPreferences.getString("access_token","");
    }

    public LoginRadiusUltimateUserProfile getProfile(){
        LoginRadiusUltimateUserProfile profile = new LoginRadiusUltimateUserProfile();
        String strProfile = sharedPreferences.getString("profile","");
        if(strProfile !=""){
            profile = JsonDeserializer.deserializeJson(strProfile,LoginRadiusUltimateUserProfile.class);
        }
        return profile;
    }


    public void logout(String accessToken, final LogoutCallBack handler) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("isLogin");
        editor.remove("access_token");
        editor.remove("profile");
        editor.apply();
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(accessToken);
        api.invalidateAccessToken(queryParams, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse data) {
                if(data.getIsPosted()){
                    ErrorResponse errorResponse =new ErrorResponse();
                    handler.Response(true,false,errorResponse);
                }
            }
            @Override
            public void onFailure(Throwable error, String errorcode) {
                ErrorResponse errorResponse =JsonDeserializer.deserializeJson(error.getMessage(),ErrorResponse.class);
                handler.Response(true,true,errorResponse);
            }
        });
    }
}

