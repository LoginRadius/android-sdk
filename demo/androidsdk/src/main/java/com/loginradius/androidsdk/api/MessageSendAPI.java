package com.loginradius.androidsdk.api;



import com.google.gson.JsonObject;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.PostAPIResponse;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Used to post messages to the user's contacts.
 * After using the Contact API, you can send messages to the retrieved contacts
 */
public class MessageSendAPI 
{
	private String to;
	private String subject;
	private String message;
	public String getTo() { return to;}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {return subject;}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {return message;}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Send email to target. For Google/Yahoo
	 * @param c Application context used
	 * @param sender Mail sender
	 * @param handler Used to handle the success and failure events
	 */


	/**
	 * Used to 'get' send direct messages. For Twitter/LinkedIn
	 * @param token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void sendMessage(lrAccessToken token, final AsyncHandler<PostAPIResponse> handler) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("to", to);
		params.put("subject", subject);
		params.put("message", message);

		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getMessage(Endpoint.API_V2_MESSAGE,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<PostAPIResponse>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						if (e instanceof HttpException) {
							try {
								Throwable t = new Throwable(((HttpException) e).response().errorBody().string(), e);
								handler.onFailure(t, "lr_SERVER");
							} catch (Exception t) {
								t.printStackTrace();
							}

						}

					}

					@Override
					public void onNext(PostAPIResponse response) {
						handler.onSuccess(response);
					}

				});
	}
	
	

}
