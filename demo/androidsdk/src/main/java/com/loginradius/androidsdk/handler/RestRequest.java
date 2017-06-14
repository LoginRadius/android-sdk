package com.loginradius.androidsdk.handler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class RestRequest {


	public static String API_V2_BASE_URL = "https://api.loginradius.com/";
	public static String API_V2_BASE_URL_CDN = "http://cdn.loginradius.com/";
	public static Retrofit retrofit = null;
	public static Retrofit retrofitcdn = null;

	public static Retrofit getClient() {
		Gson gson = new GsonBuilder().setLenient().create();
		if (retrofit==null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(API_V2_BASE_URL)
					.addConverterFactory(new JsonDeserializer(gson))
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return retrofit;
	}


	public static Retrofit getClientCdn() {
		Gson gson = new GsonBuilder().setLenient().create();
		if (retrofitcdn==null) {
			retrofitcdn = new Retrofit.Builder()
					.baseUrl(API_V2_BASE_URL_CDN)
					.addConverterFactory(new JsonDeserializer(gson))
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
		return retrofitcdn;
	}


}

