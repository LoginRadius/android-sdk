package com.loginradius.androidsdk.handler;

import java.io.IOException;

import retrofit2.HttpException;

/**
 * Created by admin on 11-Jul-17.
 */

public class ExceptionResponse {
    public Throwable t;
    public String message;

    public static ExceptionResponse HandleException(Throwable e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if (e instanceof HttpException) {
            try {
                Throwable t = new Throwable(((HttpException) e).response().errorBody().string(), e);
                exceptionResponse.t = t;
                exceptionResponse.message="ApiError";
            } catch (Exception t) {
                t.printStackTrace();
            }
        }else if (e instanceof IllegalArgumentException){
            Throwable t = new Throwable(((IllegalArgumentException) e).getMessage(), e);
            exceptionResponse.t = t;
            exceptionResponse.message="IllegalArgumentError";
        }else if (e instanceof IOException){
            Throwable t = new Throwable(((IOException) e).getMessage(), e);
            exceptionResponse.t = t;
            exceptionResponse.message="TimeoutError";
        }else if (e instanceof IllegalStateException){
            Throwable t = new Throwable(((IllegalStateException) e).getMessage(), e);
            exceptionResponse.t = t;
            exceptionResponse.message="ConversionError";
        }else{
            Throwable t = new Throwable("an error occurred", e);
            exceptionResponse.t = t;
            exceptionResponse.message="ServerError";
        }
        return  exceptionResponse;
    }
}
