package com.loginradius.androidsdk.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 10/9/2017.
 */

public class DeleteAccountResponse {
    @SerializedName("IsDeleteRequestAccepted")
    @Expose
    private Boolean isDeleteRequestAccepted;

    /**
     *
     * @return
     * The isDeleteRequestAccepted
     */
    public Boolean getDeleteRequestAccepted() {
        return isDeleteRequestAccepted;
    }

    /**
     *
     * @param isDeleteRequestAccepted
     * The isDeleteRequestAccepted
     */
    public void setDeleteRequestAccepted(Boolean isDeleteRequestAccepted) {
        this.isDeleteRequestAccepted = isDeleteRequestAccepted;
    }
}
