package com.loginradius.androidsdk.handler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loginradius.androidsdk.helper.CustomHeaderInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class RestRequest {


      public static String API_V2_BASE_URL = "https://api.loginradius.com/";
       public static String API_V2_BASE_URL_CONFIG = "https://config.lrcontent.com/";
	public static Retrofit retrofit = null;
	public static Retrofit retrofitconfig = null;
	public static OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();

	public static Retrofit getClient() {
		//Add Custom Headers Interceptor
		okHttpBuilder.addInterceptor(new CustomHeaderInterceptor());

		Gson gson = new GsonBuilder().setLenient().create();
		if (retrofit==null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(API_V2_BASE_URL)
					.client(okHttpBuilder.build())
					.addConverterFactory(new JsonDeserializer(gson))
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return retrofit;
	}

	public static Retrofit getClientConfig() {
		//Add Custom Headers Interceptor
		okHttpBuilder.addInterceptor(new CustomHeaderInterceptor());

		Gson gson = new GsonBuilder().setLenient().create();
		if (retrofitconfig ==null) {
			retrofitconfig = new Retrofit.Builder()
					.baseUrl(API_V2_BASE_URL_CONFIG)
					.client(okHttpBuilder.build())
					.addConverterFactory(new JsonDeserializer(gson))
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return retrofitconfig;
	}
}

