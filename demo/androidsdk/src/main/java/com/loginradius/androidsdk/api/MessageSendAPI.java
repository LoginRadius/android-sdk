package com.loginradius.androidsdk.api;

import android.content.Context;

import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.PostAPIResponse;
import com.loginradius.androidsdk.response.lrAccessToken;

import java.util.HashMap;
import java.util.Map;

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
	public void sendMessage(lrAccessToken token, final AsyncHandler<PostAPIResponse> handler)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token.access_token);
		params.put("to", to);
		params.put("subject", subject);
		params.put("message", message);
		RestRequest.post(null, Endpoint.getV2_MESSAGE(), params,"{}",new AsyncHandler<String>()
				{
			@Override
			public void onSuccess(String response)
			{
				PostAPIResponse postResponse = JsonDeserializer.deserializeJson(response,PostAPIResponse.class);
				handler.onSuccess(postResponse);
			}

			@Override
			public void onFailure(Throwable error, String response) {
				handler.onFailure(error, response);
			}
				});
	}
	
	

}
