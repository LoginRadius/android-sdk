package com.loginradius.androidsdk.handler;

public interface AsyncHandler<T> {
	public void onSuccess(T data);
	public void onFailure(Throwable error, String errorcode);
}
