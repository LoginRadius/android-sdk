package com.loginradius.androidsdk.response.customobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loginradius on 23-Nov-16.
 */

public class ReadCustomObject {

    @SerializedName("data")
    @Expose
    private List<CreateCustomObject> data = new ArrayList<CreateCustomObject>();
    @SerializedName("Count")
    @Expose
    private Integer count;

    /**
     *
     * @return
     * The data
     */
    public List<CreateCustomObject> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CreateCustomObject> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The Count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
