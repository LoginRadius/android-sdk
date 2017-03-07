package com.loginradius.androidsdk.response.audio;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusAudio 
{ 
	@SerializedName("ID")
	public String id;
	@SerializedName("ownerId")
    public String ownerId;
	@SerializedName("OwnerName")
    public String ownerName;
	@SerializedName("Artist")
    public String artist;
	@SerializedName("Title")
    public String title;
	@SerializedName("Duration")
    public String duration;
	@SerializedName("Url")
    public String url;
	@SerializedName("CreatedDate")
    public String createdDate;
	@SerializedName("UpdatedDate")
    public String updatedDate;
}
