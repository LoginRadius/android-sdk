package com.loginradius.androidsdk.handler;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

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
	private static Gson errorgson = new Gson();
	public static <T> T deserializeJson(String jsonString, Class<T> type) {
		T result = null;
		result = errorgson.fromJson(jsonString, type);
		return result;
	}
}



