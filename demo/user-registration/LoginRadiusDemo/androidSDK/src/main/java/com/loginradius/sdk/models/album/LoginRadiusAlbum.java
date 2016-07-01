package com.loginradius.sdk.models.album;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusAlbum 
{
	@SerializedName("ID")
	public String id;
	 @SerializedName("OwnerId")
    public String ownerId;
	 @SerializedName("OwnerName")
    public String ownerName;
	 @SerializedName("Title")
    public String title;
	 @SerializedName("Description")
    public String description;
	 @SerializedName("Location")
    public String location;
	 @SerializedName("Type")
    public String type;
	 @SerializedName("CreatedDate")
    public String createdDate;
	 @SerializedName("UpdatedDate")
    public String updatedDate;
	 @SerializedName("CoverImageUrl")
    public String coverImageUrl;
	 @SerializedName("ImageCount")
    public String imageCount;
	 @SerializedName("DirectoryUrl")
	    public String DirectoryUrl;

}
