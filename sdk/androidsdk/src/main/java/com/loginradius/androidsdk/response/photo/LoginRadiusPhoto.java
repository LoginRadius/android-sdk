package com.loginradius.androidsdk.response.photo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginRadiusPhoto
{
	 @SerializedName("ID")
	 public String ID;
	 @SerializedName("AlbumId")
     public String AlbumId;
	 @SerializedName("OwnerId")
     public String OwnerId;
	 @SerializedName("OwnerName")
     public String OwnerName;
	 @SerializedName("Name")
     public String Name;
	 @SerializedName("DirectUrl")
     public String DirectUrl;
	 @SerializedName("Location")
     public String Location;
	 @SerializedName("Link")
     public String Link;
	 @SerializedName("Description")
     public String Description;
	 @SerializedName("Height")
     public String Height;
     @SerializedName("Width")
     public String Width;
     @SerializedName("CreatedDate")
     public String CreatedDate;
     @SerializedName("UpdatedDate")
     public String UpdatedDate;
     @SerializedName("Images")
     private List<Image> images = null;
     public List<Image> getImages() {
        return images;
     }
     public void setImages(List<Image> images) {
        this.images = images;
    }
}
