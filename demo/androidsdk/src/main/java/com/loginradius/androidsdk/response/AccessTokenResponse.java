package com.loginradius.androidsdk.response;

public class AccessTokenResponse
{
	public String access_token;
	//public Provider provider;
	public String provider;

	public String expires_in;
	public String id;
	public String refresh_token;



	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() { return refresh_token; }

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

}
 