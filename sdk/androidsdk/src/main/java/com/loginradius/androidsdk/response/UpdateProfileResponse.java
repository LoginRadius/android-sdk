package com.loginradius.androidsdk.response;

import com.google.gson.annotations.SerializedName;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

// <summary>
//	Response containing Definition of Complete Validation and UserProfile data
// </summary>
public class UpdateProfileResponse {

        @SerializedName("Data")
        private LoginRadiusUltimateUserProfile data;

        @SerializedName("IsPosted")
        private Boolean isPosted;

        // <summary>
        //	Data
        // </summary>
        public LoginRadiusUltimateUserProfile getData() {
            return data;
        }
        // <summary>
        //	Data
        // </summary>
        public void setData(LoginRadiusUltimateUserProfile data) {
            this.data = data;
        }
        // <summary>
        //	check data is posted
        // </summary>
        public Boolean getIsPosted() {
            return isPosted;
        }
        // <summary>
        //	check data is posted
        // </summary>
        public void setIsPosted(Boolean isPosted) {
            this.isPosted = isPosted;
        }
}
