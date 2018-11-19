package com.loginradius.androidsdk.resource;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loginradius.androidsdk.api.AuthenticationAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.response.AccessTokenResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;


public class LoginUtil {
    Context context;
    SharedPreferences sharedPreferences;
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
    public void setLogin(String accessToken){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("access_token",accessToken);
        editor.apply();
    }
    public String getAccessToken(){
        return sharedPreferences.getString("access_token","");
    }
    public void logout(String accessToken){
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(accessToken);
        api.invalidateAccessToken(queryParams, new AsyncHandler<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse data) {
                if(data.getIsPosted()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("isLogin");
                    editor.remove("access_token");
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("LoginManagerError",error.getMessage());
            }
        });
    }

    public void validateAccessToken(String accessToken){
        AuthenticationAPI api = new AuthenticationAPI();
        QueryParams queryParams = new QueryParams();
        queryParams.setAccess_token(accessToken);
        api.validateAccessToken(queryParams, new AsyncHandler<AccessTokenResponse>() {
            @Override
            public void onSuccess(AccessTokenResponse data) {
                setLogin(data.access_token);
                Log.i("refreshtoken",data.refresh_token);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                Log.i("LoginManagerError",error.getMessage());
            }
        });
    }
}
