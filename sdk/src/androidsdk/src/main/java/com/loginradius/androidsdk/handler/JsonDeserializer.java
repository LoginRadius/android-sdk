package com.loginradius.androidsdk.handler;

import com.google.gson.Gson;

/**
 * Class to deserialize json response
 *
 */
public class JsonDeserializer {
	
	private static Gson gson = new Gson();
	public static <T> T deserializeJson(String jsonString, Class<T> type) {
		T result = null;
		result = gson.fromJson(jsonString, type);
		return result;
	}


}
