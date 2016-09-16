package com.loginradius.userregistration.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
public class URLHelper {
    public static String URLBuilder(String baseURL,Map<String,String> parameters){
        StringBuilder sb = new StringBuilder();
        sb.append(baseURL);
        sb.append('?');
        boolean isFirst = true;
        for (Map.Entry<String,String> entry : parameters.entrySet()) {
            if (!isFirst) {
                sb.append('&');
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            isFirst = false;
        }
        return sb.toString();
    }
    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = url.getQuery();
        if(query == null)
            return query_pairs;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;

    }
}
