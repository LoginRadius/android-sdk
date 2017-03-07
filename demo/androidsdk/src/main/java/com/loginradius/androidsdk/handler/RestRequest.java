package com.loginradius.androidsdk.handler;

import android.content.Context;

import com.loginradius.androidsdk.resource.Endpoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Sends requests to LoginRadius server
 *
 */
public class RestRequest {

	private static AsyncHttpClient client = new AsyncHttpClient();
	/**
	 * 'Get' function to handle get http requests
	 * @param serviceUrl Url where the request will send
	 * @param params params that will be send with url
	 * @param asyncHandler callback handler
	 */
	public static void get(String serviceUrl, Map<String, String> params, final AsyncHandler<String> asyncHandler) {
		RequestParams re_params = new RequestParams();

		if (params != null && params.size() > 0)
			for (Entry<String, String> entry : params.entrySet())
				re_params.put(entry.getKey(), entry.getValue());
		client.get(serviceUrl, re_params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
				try {
					String str =  String.valueOf(new String(responseBody));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
				try {
					String str =  String.valueOf(new String(responseBody));
					Throwable e = new Throwable(str, error);
					asyncHandler.onFailure(e, "lr_SERVER");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});
	}

	/**
	 * 'Post' function to handle post requests
	 * @param serviceUrl Url where the request will send
	 * @param getParams params that will be send with url
	 * @param asyncHandler callback handler
	 */
	public static void post(Context context, String serviceUrl, Map<String, String> getParams, String payload, final AsyncHandler<String> asyncHandler) {

		String url = Endpoint.GetRequestUrl(serviceUrl, getParams);

		StringEntity entity = null;
		try {
			entity = new StringEntity(payload);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		client.post(context,url, (HttpEntity) entity, "application/json", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
				try {
					String str =  String.valueOf(new String(responseBody));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
				try {
					String str =  String.valueOf(new String(responseBody));
					Throwable e = new Throwable(str, error);
					asyncHandler.onFailure(e, "lr_SERVER");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	public static void put(Context context, String serviceUrl, Map<String, String> getParams, String payload, final AsyncHandler<String> asyncHandler) {

		String url = Endpoint.GetRequestUrl(serviceUrl, getParams);
		StringEntity entity = null;
		try {
			entity = new StringEntity(payload);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		client.put(context,url, (HttpEntity) entity, "application/json", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
				try {
					String str =  String.valueOf(new String(responseBody));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
				try {
					String str =  String.valueOf(new String(responseBody));
					Throwable e = new Throwable(str, error);
					asyncHandler.onFailure(e, "lr_SERVER");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});
	}

	public static void delete(Context context, String serviceUrl, Map<String, String> getParams, String payload, final AsyncHandler<String>asyncHandler){

		String url = Endpoint.GetRequestUrl(serviceUrl, getParams);
		StringEntity entity = null;
		try {
			entity = new StringEntity(payload);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		client.delete(context,url,(HttpEntity) entity, "application/json", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
				try {
					String str =  String.valueOf(new String(responseBody));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
				try {
					String str =  String.valueOf(new String(responseBody));
					Throwable e = new Throwable(str, error);
					asyncHandler.onFailure(e, "lr_SERVER");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});



	}


}