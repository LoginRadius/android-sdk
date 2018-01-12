package com.loginradius.androidsdk.response.checkin;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusCheckIn
{
	@SerializedName("ID")
	public String ID;
	@SerializedName("CreatedDate")
    public String CreatedDate;
	@SerializedName("OwnerId")
    public String OwnerId;
	@SerializedName("OwnerName")
    public String OwnerName;
	@SerializedName("Latitude")
    public String Latitude;
	@SerializedName("Longitude")
    public String Longitude;
	@SerializedName("Message")
    public String Message;
	@SerializedName("PlaceTitle")
    public String PlaceTitle;
	@SerializedName("Address")
    public String Address;
	@SerializedName("Distance")
    public String Distance;
	@SerializedName("Type")
    public String Type;
	@SerializedName("ImageUrl")
    public String ImageUrl;

}
