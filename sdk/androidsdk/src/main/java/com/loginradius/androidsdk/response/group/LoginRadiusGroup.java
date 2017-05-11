package com.loginradius.androidsdk.response.group;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusGroup 
{
	@SerializedName("ID")
    public String id;
    @SerializedName("Name")
    public String name;
	@SerializedName("Type")
    public String type;
    @SerializedName("Description")
    public String description;
	@SerializedName("Email")
    public String email;
    @SerializedName("Country")
    public String country;
	@SerializedName("Postal Code")
    public String postalCode;
    @SerializedName("Logo")
    public String logo;
    @SerializedName("Image")
    public String image;
    @SerializedName("MemberCount")
    public String memeberCount;
}
