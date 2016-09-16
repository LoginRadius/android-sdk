package com.loginradius.sdk.api;

import com.loginradius.sdk.util.AsyncHandler;

/**
 *LoginRadiusAPI base class
 */

public abstract class LoginRadiusAPI 
{
	public abstract void getResponse(String token,AsyncHandler<?> handler);
}
