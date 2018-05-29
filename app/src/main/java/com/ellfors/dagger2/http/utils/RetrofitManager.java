package com.ellfors.dagger2.http.utils;


import android.support.annotation.NonNull;

import com.ellfors.dagger2.http.config.HttpApi;
import com.ellfors.dagger2.http.config.RetrofitConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit操纵类
 */
public class RetrofitManager
{
    private static volatile RetrofitManager instance;
    private static volatile RetrofitManager instanceStatic;

    private OkHttpClient.Builder okHttpClientBuilder;
    private HttpLoggingInterceptor loggingInterceptor;

    private RetrofitManager(boolean isStatic)
    {
        //构造OkHttpClient
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(RetrofitConfig.OUTTIME, java.util.concurrent.TimeUnit.SECONDS);
        okHttpClientBuilder.addInterceptor(getLoggingInterceptor());
//        okHttpClientBuilder.addInterceptor(new RequestInterceptor(isStatic));
//        if (!isStatic)
//            okHttpClientBuilder.addInterceptor(TokenInterceptor.getInstance());
    }

    /**
     * 动态令牌
     */
    public static RetrofitManager getInstance()
    {
        if (instance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (instance == null)
                {
                    instance = new RetrofitManager(false);
                }
            }
        }
        return instance;
    }

    /**
     * 静态令牌
     */
    public static RetrofitManager getInstanceStatic()
    {
        if (instanceStatic == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (instanceStatic == null)
                {
                    instanceStatic = new RetrofitManager(true);
                }
            }
        }
        return instanceStatic;
    }

    /**
     * 获取Gson解析的HttpApi
     */
    public HttpApi getGsonHttpApi()
    {
        return new Retrofit
                .Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(RetrofitConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HttpApi.class);
    }

    /**
     * 获取打印拦截器
     */
    private HttpLoggingInterceptor getLoggingInterceptor()
    {
        if (loggingInterceptor == null)
        {
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
            {
                @Override
                public void log(@NonNull String message)
                {
                    HttpLogUtil.log(RetrofitConfig.TAG, message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return loggingInterceptor;
    }

}
