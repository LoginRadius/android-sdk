package com.loginradius.androidsdk.response.event;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusEvent
{
	@SerializedName("ID")
	public String ID;
	@SerializedName("OwnerId")
    public String OwnerId;
	@SerializedName("OwnerName")
    public String OwnerName;
	@SerializedName("Name")
    public String Name;
	@SerializedName("Description")
    public String Description;
	@SerializedName("RsvpStatus")
    public String RsvpStatus;
	@SerializedName("Location")
    public String Location;
	@SerializedName("StartTime")
    public String StartTime;
	@SerializedName("UpdatedDate")
    public String UpdatedDate;
	@SerializedName("EndTime")
    public String EndTime;
	@SerializedName("Privacy")
    public String Privacy;
}
