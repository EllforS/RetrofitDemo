package com.ellfors.dagger2.http.utils.interceptor;

import android.support.annotation.NonNull;

import com.ellfors.dagger2.http.utils.RetrofitManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 判断Token是否过期拦截器
 * 2018/4/16 10:14
 */
public class TokenInterceptor implements Interceptor
{
    private static volatile TokenInterceptor instance;
    private RetrofitManager manager;

    public TokenInterceptor()
    {
        manager = RetrofitManager.getInstanceStatic();
    }

    public static TokenInterceptor getInstance()
    {
        if (instance == null)
        {
            synchronized (TokenInterceptor.class)
            {
                if (instance == null)
                {
                    instance = new TokenInterceptor();
                }
            }
        }
        return instance;
    }

    //
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException
    {
        Request newRequest;
        //获取Token
        String token = getToken();
        //添加公共请求头、需要令牌
        newRequest = chain.request()
                .newBuilder()
                .header("", "")    //参数
                .header("", "")    //参数
                .header("", "")    //参数
                .header("", token) //令牌
                .build();
        //重新请求
        return chain.proceed(newRequest);
    }

    /**
     * 获取Token
     */
    private synchronized String getToken() throws IOException
    {
        //如果失效，则同步请求方式，获取最新的Token
        if (isTokenExpired())
        {
            Object resp = "1";
            if (checkLoginStatus(resp))
            {
                //正确返回新的Token
                return "";
            }
            else
            {
                //错误则跳登录
                //LoginActivity.start(context);
                return "";
            }
        }
        //如果未失效，则使用原始Token
        else
        {
            return "";
        }
    }

    /**
     * 判断Token是否失效
     */
    private boolean isTokenExpired()
    {
        //这里写具体的判断方法
        return true;
    }

    /**
     * 判断是否登录成功
     */
    private boolean checkLoginStatus(Object resp)
    {
        //这里写具体的判断方法
        return true;
    }

}