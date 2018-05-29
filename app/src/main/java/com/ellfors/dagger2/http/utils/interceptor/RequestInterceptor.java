package com.ellfors.dagger2.http.utils.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 批量添加请求头、请求参数拦截器
 * 2018/4/16 10:19
 */
public class RequestInterceptor implements Interceptor
{
    private boolean isStatic;    //是否添加公共请求参数
    private HttpUrl.Builder authorizedUrlBuilder;
    private Request newRequest;

    public RequestInterceptor(boolean isStatic)
    {
        this.isStatic = isStatic;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException
    {
        Request oldRequest = chain.request();

        if (!isStatic)
        {
            //添加公共参数
            authorizedUrlBuilder = oldRequest
                    .url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("", "")       //参数
                    .addQueryParameter("", "")       //参数
                    .addQueryParameter("", "");      //参数
            //添加公共请求头
            newRequest = oldRequest
                    .newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .build();
        }
        else
        {
            //添加公共请求头
            newRequest = oldRequest
                    .newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .addHeader("", "")     //参数
                    .build();
        }
        return chain.proceed(newRequest);
    }
}
