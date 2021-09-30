package com.loginradius.androidsdk.resource;
import java.util.HashMap;
import java.util.Map;

public class CustomHeaderMapHelper {

    private Map<String,String> customHeader=new HashMap<String, String>();

    public Map<String,String> getCustomHeader() {
        return customHeader;
    }

    public void setCustomHeader(Map<String,String> customHeader) {
        this.customHeader = customHeader;
    }
}
