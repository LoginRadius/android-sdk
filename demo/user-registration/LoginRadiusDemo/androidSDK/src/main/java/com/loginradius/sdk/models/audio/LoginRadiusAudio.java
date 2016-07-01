package com.loginradius.sdk.models.audio;

import com.google.gson.annotations.*;

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
