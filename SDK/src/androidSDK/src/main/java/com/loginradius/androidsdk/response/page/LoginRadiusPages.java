package com.loginradius.androidsdk.response.page;

import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.page.LoginRadiusPage.Speciality;

import java.util.List;

public class LoginRadiusPages
{
	@SerializedName("ID")
	public String ID;
	@SerializedName("Name")
     public String Name;
	@SerializedName("Url")
     public String Url;
	@SerializedName("Category")
     public String Category;
	@SerializedName("Likes")
     public String Likes;
	@SerializedName("Phone")
     public String Phone;
	@SerializedName("Image")
     public String Image;
	@SerializedName("Website")
     public String Website;
	@SerializedName("About")
     public String About;
	@SerializedName("Description")
     public String Description;
	@SerializedName("Awards")
     public String Awards;
	@SerializedName("CheckinCount")
     public String CheckinCount;
	@SerializedName("Founded")
     public String Founded;
	@SerializedName("Mission")
     public String Mission;
	@SerializedName("Products")
     public String Products;
	@SerializedName("ReleaseDate")
     public String ReleaseDate;
	@SerializedName("TalkingAboutCount")
     public String TalkingAboutCount;
	@SerializedName("Published")
     public boolean Published;
	@SerializedName("UserName")
     public String UserName;
	@SerializedName("Locations")
     public List<LoginRadiusPageLocation> Locations ;
	@SerializedName("CategoryList")
     public List<LoginRadiusPageCategoryList> CategoryList;
	@SerializedName("CoverImage")
     public LoginRadiusPageCover CoverImage ;
	@SerializedName("EmployeeCountRange")
     public LoginRadiusPageCodeName EmployeeCountRange ;
	@SerializedName("Industries")
     public List<LoginRadiusPageCodeName> Industries ;
	@SerializedName("Specialties")
     public Speciality Specialties ;
	@SerializedName("Status")
     public LoginRadiusPageCodeName Status;
	@SerializedName("StockExchange")
     public LoginRadiusPageCodeName StockExchange ;
}
