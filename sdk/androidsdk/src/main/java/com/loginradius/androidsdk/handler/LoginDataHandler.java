package com.loginradius.androidsdk.handler;

import com.loginradius.androidsdk.response.login.LoginData;

/**
 * Created by loginradius on 9/14/2017.
 */

public class LoginDataHandler{
    private AsyncHandler<LoginData> handler;
    private LoginData response;

    private static LoginDataHandler instance = new LoginDataHandler();

    private LoginDataHandler() {
    }

    public static LoginDataHandler getInstance(){
        return instance;
    }

    public AsyncHandler<LoginData> getHandler() {
        return handler;
    }

    public void setHandler(AsyncHandler<LoginData> handler) {
        this.handler = handler;
    }

    public LoginData getResponse() {
        return response;
    }

    public void setResponse(LoginData response) {
        this.response = response;
    }
}
