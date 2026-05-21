package com.loginradius.androidsdk.handler;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.loginradius.androidsdk.helper.ErrorResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Class to deserialize json response
 *
 */
public class JsonDeserializer extends Converter.Factory {

	public static JsonDeserializer create() {
		return create(new Gson());
	}

	public static JsonDeserializer create(Gson gson) {
		return new JsonDeserializer(gson);
	}

	public final Gson gson;

	public JsonDeserializer(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}


	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
															Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new JsonDeserializer.GsonResponseBodyConverter<>(gson, adapter);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
														  Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new JsonDeserializer.GsonRequestBodyConverter<>(gson, adapter);
	}


	final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
		private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
		private final Charset UTF_8 = Charset.forName("UTF-8");

		private final Gson gson;
		private final TypeAdapter<T> adapter;

		GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override
		public RequestBody convert(T value) throws IOException {
			Buffer buffer = new Buffer();
			Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
			JsonWriter jsonWriter = gson.newJsonWriter(writer);
			adapter.write(jsonWriter, value);
			jsonWriter.close();
			return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
		}
	}

	final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
		private final Gson gson;
		private final TypeAdapter<T> adapter;

		GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override
		public T convert(ResponseBody value) throws IOException {
			String dirty = value.string();
			String clean = dirty.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
					"<string xmlns=\"http://tempuri.org/\">", "").replace("</string>", "");


			if (clean.contains("loginRadiusAppJsonLoaded(")) {
				clean = clean.replace("loginRadiusAppJsonLoaded(", "");
				clean=clean.substring(0,clean.length()-1);
			}else if (clean.contains("loginRadiusAppRaasSchemaJsonLoaded(")){
				clean=clean.replace("loginRadiusAppRaasSchemaJsonLoaded(","");
				clean=clean.substring(0,clean.length()-1);
			}
			try {
				return adapter.fromJson(clean);
			} finally {
				value.close();
			}
		}
	}
	public static boolean isJSONValid(String jsonString) {
		try {
			new JSONObject(jsonString);
		} catch (JSONException ex) {
			try {
				new JSONArray(jsonString);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	private static Gson errorgson = new Gson();
	public static <T> T deserializeJson(String jsonString, Class<T> type) {
		T result = null;

		if(isJSONValid(jsonString)){
			result = errorgson.fromJson(jsonString, type);}
		else{
			ErrorResponse errorResponse =new ErrorResponse();
			errorResponse.setErrorCode(100);
			errorResponse.setDescription(jsonString);
			errorResponse.setMessage("Unable to complete the request at the moment");
			return (T) errorResponse;
		}
		return result;
	}

    public static <T> T deserializeJson(String error,String errorcode, Class<T> type) {
        T result = null;
        ErrorResponse errorResponse =new ErrorResponse();
        if(isJSONValid(error)){
            result = errorgson.fromJson(error, type);}
        else{
            if(errorcode.equals("TimeoutError")){
                errorResponse.setErrorCode(101);
                errorResponse.setDescription("Unable to reach host please check your internet connection");
                errorResponse.setMessage("TimeoutError");
                return (T) errorResponse;

            }
            else if(errorcode.equals("IllegalArgumentError")) {
                errorResponse.setErrorCode(102);
                errorResponse.setDescription(error);
                errorResponse.setMessage("IllegalArgumentError");
                return (T) errorResponse;
            }
            else if(errorcode.equals("ConversionError")){
                errorResponse.setErrorCode(103);
                errorResponse.setDescription(error);
                errorResponse.setMessage("ConversionError");
                return (T) errorResponse;
            }
            else if(errorcode.equals("ServerError")){
                errorResponse.setErrorCode(104);
                errorResponse.setDescription(error);
                errorResponse.setMessage("ServerError");
                return (T) errorResponse;
            }
            else{
                errorResponse.setErrorCode(100);
                errorResponse.setDescription(error);
                errorResponse.setMessage("Unable to complete the request at the moment");
                return (T) errorResponse;
            }
        }
        return result;
    }

}



