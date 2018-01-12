package com.loginradius.androidsdk.handler;

import java.io.Serializable;

public interface AsyncHandler<T> extends Serializable{
	public void onSuccess(T data);
	public void onFailure(Throwable error, String errorcode);
}
