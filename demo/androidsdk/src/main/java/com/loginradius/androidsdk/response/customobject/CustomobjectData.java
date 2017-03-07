package com.loginradius.androidsdk.response.customobject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by loginradius on 23-Nov-16.
 */

public class CustomobjectData {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("hOffset")
    @Expose
    private Integer hOffset;
    @SerializedName("vOffset")
    @Expose
    private Integer vOffset;
    @SerializedName("alignment")
    @Expose
    private String alignment;
    @SerializedName("onMouseUp")
    @Expose
    private String onMouseUp;

    /**
     *
     * @return
     * The data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The style
     */
    public String getStyle() {
        return style;
    }

    /**
     *
     * @param style
     * The style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The hOffset
     */
    public Integer getHOffset() {
        return hOffset;
    }

    /**
     *
     * @param hOffset
     * The hOffset
     */
    public void setHOffset(Integer hOffset) {
        this.hOffset = hOffset;
    }

    /**
     *
     * @return
     * The vOffset
     */
    public Integer getVOffset() {
        return vOffset;
    }

    /**
     *
     * @param vOffset
     * The vOffset
     */
    public void setVOffset(Integer vOffset) {
        this.vOffset = vOffset;
    }

    /**
     *
     * @return
     * The alignment
     */
    public String getAlignment() {
        return alignment;
    }

    /**
     *
     * @param alignment
     * The alignment
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     *
     * @return
     * The onMouseUp
     */
    public String getOnMouseUp() {
        return onMouseUp;
    }

    /**
     *
     * @param onMouseUp
     * The onMouseUp
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

}
