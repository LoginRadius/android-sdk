package com.loginradius.androidsdk.api;


import com.loginradius.androidsdk.handler.AsyncHandler;

/**
 *LoginRadiusAPI base class
 */

public abstract class LoginRadiusAPI 
{
	public abstract void getResponse(String token, AsyncHandler<?> handler);
}
