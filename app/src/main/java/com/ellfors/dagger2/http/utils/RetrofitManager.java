package com.ellfors.dagger2.http.utils;


import android.support.annotation.NonNull;

import com.ellfors.dagger2.app.MyApplication;
import com.ellfors.dagger2.http.config.HttpApi;
import com.ellfors.dagger2.http.config.RetrofitConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit操纵类
 */
public class RetrofitManager
{

    private static volatile RetrofitManager instance;
    public static String BASE_URL;
    public static int DEFAULT_TIME;

    private static HttpApi httpApi;
    private HttpLoggingInterceptor loggingInterceptor;
    private Interceptor requestInterceptor;

    public RetrofitManager()
    {
        BASE_URL = RetrofitConfig.BASE_URL;
        DEFAULT_TIME = RetrofitConfig.OUTTIME;
        //打印拦截
        loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(@NonNull String message)
            {
                //Debug模式下才打印
                if (MyApplication.isDebug)
                    HttpLogUtil.log("AAA", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Head拦截
        requestInterceptor = new Interceptor()
        {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException
            {
                Request request = chain.request().newBuilder()
                        .addHeader("test", "test")
                        .addHeader("aaa", "aaa")
                        .addHeader("bbb", "bbb")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 单例构造
     */
    public static RetrofitManager getInstance()
    {
        if (instance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if (instance == null)
                {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取Gson解析的HttpApi
     *
     * @return httpApi
     */
    public HttpApi getGsonHttpApi()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);
        builder.addInterceptor(loggingInterceptor);
//        builder.addInterceptor(requestInterceptor);

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        httpApi = retrofit.create(HttpApi.class);

        return httpApi;
    }

    /**
     * 获取原始String的HttpApi
     *
     * @return httpApi
     */
    public HttpApi getStringHttpApi()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, java.util.concurrent.TimeUnit.SECONDS);
        builder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        httpApi = retrofit.create(HttpApi.class);

        return httpApi;
    }

}
