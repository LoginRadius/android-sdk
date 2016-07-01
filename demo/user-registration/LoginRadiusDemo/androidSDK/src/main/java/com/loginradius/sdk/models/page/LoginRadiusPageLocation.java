package com.loginradius.sdk.models.page;

import com.google.gson.annotations.SerializedName;
public class LoginRadiusPageLocation
{
	@SerializedName("Street")
	public String Street ;
	@SerializedName("City")
    public String City ;
	@SerializedName("State")
    public String State ;
	@SerializedName("Country")
    public LoginRadiusCountryCodeName Country ;
	@SerializedName("Zip")
    public String Zip ;
	@SerializedName("Latitude")
    public double Latitude;
	@SerializedName("Longitude")
    public double Longitude ;
	@SerializedName("Phone")
    public String Phone ;
}
