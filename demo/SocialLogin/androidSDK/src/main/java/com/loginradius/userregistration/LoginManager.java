package com.loginradius.userregistration;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.loginradius.userregistration.resource.StrResource;

public class LoginManager {
    Context context;
    SharedPreferences sharedPreferences;
    Editor editor;


    /**
     * Constructor
     * @param context
     */
    public LoginManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(StrResource.SHAREDPREFERENCEFILEKEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public boolean isLogin(){

        return sharedPreferences.getBoolean("isLogin",false);
    }
    public void setLogin(){
       // SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean("isLogin",false);
    }
    public void logout(){
      SharedPreferences.Editor editor = this.sharedPreferences.edit();
      //  editor.putString("logout","logot");
        editor.remove("isLogin");



    }
}
