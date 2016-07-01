package com.loginradius.sdk.models.activity;

import java.util.List;
import com.google.gson.annotations.*;
public class LoginRadiusActivity 
{
	@SerializedName("ActivityId")
	public String ActivityId;
	@SerializedName("ActivityUrl")
    public String ActivityUrl;
	@SerializedName("Title")
    public String Title;
	@SerializedName("Published")
    public String Published;
	@SerializedName("Updated")
    public String Updated;
	@SerializedName("Name")
    public String Name;
	@SerializedName("ProfileUrl")
    public String ProfileUrl;
	@SerializedName("ImageUrl")
    public String ImageUrl;
	@SerializedName("TotalReply")
    public String TotalReply;
	@SerializedName("ReplyUrl")
    public String ReplyUrl;
	@SerializedName("TotalPlusOneRs")
    public String TotalPlusOneRs;
	@SerializedName("PlusOneRsUrl")
    public String PlusOneRsUrl;
	@SerializedName("TotalReShares")
    public String TotalReShares;
	@SerializedName("ReShareUrl")
    public String ReShareUrl;
	@SerializedName("UserId")
    public String UserId;
	@SerializedName("Source")
    public String Source;
	@SerializedName("Attchments")
    public List<Attchments> Attchments;
    public class Attchments
    {
    	@SerializedName("AttchmentType")
        public String AttchmentType;
    	@SerializedName("AttchmentName")
        public String AttchmentName;
    	@SerializedName("AttchmentUrl")
        public String AttchmentUrl;
    }
}
