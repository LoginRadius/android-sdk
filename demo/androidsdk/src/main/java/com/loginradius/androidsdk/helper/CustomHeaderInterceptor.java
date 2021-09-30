package com.loginradius.androidsdk.helper;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This Class implements okhttp Interceptor for adding the additional Headers in the API Request.
 * Created by loginradius on 9/27/2021.
 */
public class CustomHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request userRequest = chain.request();
        Request.Builder requestBuilder = userRequest.newBuilder();
        //Add Custom Header
        if (!LoginRadiusSDK.getCustomHeader().isEmpty()) {
            for (HashMap.Entry<String, String> entry : LoginRadiusSDK.getCustomHeader().entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}
