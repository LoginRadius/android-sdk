package com.loginradius.sdk.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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

		String url = Endpoint.GetRequestUrl(serviceUrl);

		RequestParams re_params = new RequestParams();
		if (params != null && params.size() > 0)
			for (Entry<String, String> entry : params.entrySet())
				re_params.put(entry.getKey(), entry.getValue());

		client.get(url, re_params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {

				try {
					String str =  String.valueOf(new String(bytes));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				try {
					String str =  String.valueOf(new String(bytes));
					Throwable e = new Throwable(str, throwable);
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
	public static void post(String serviceUrl, Map<String, String> getParams, final AsyncHandler<String> asyncHandler) {

		String url = Endpoint.GetRequestUrl(serviceUrl, getParams);

		RequestParams re_params = new RequestParams();

		client.post(url, re_params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {

				try {
					String str =  String.valueOf(new String(bytes));
					asyncHandler.onSuccess(str);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
				try {
					String str =  String.valueOf(new String(bytes));
					Throwable e = new Throwable(str, throwable);
					asyncHandler.onFailure(e, "lr_SERVER");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}