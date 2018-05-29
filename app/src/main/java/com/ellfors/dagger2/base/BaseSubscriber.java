package com.ellfors.dagger2.base;

import com.ellfors.dagger2.app.MyApplication;
import com.ellfors.dagger2.http.config.RetrofitConfig;
import com.ellfors.dagger2.http.utils.RxException;
import com.ellfors.extools.utils.ExViewUtil;

import org.reactivestreams.Subscription;

import java.net.SocketTimeoutException;

import io.reactivex.FlowableSubscriber;

/**
 * Rx Subscriber基类
 */
public abstract class BaseSubscriber<T> implements FlowableSubscriber<T>
{
    private T t;

    public Subscription mSubscription;

    public abstract void onSuccess(T t);

    public abstract void onFailed(RxException e);

    @Override
    public void onSubscribe(Subscription s)
    {
        mSubscription = s;
        if (!ExViewUtil.CheckNetworkState(MyApplication.getInstance().currentActivity()))
        {
            onError(new RxException("请检查您的网络", RetrofitConfig.HTTP_NET_ERROR));
            mSubscription.cancel();
            return;
        }
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t)
    {
        this.t = t;
        if (t != null)
            onSuccess(t);
    }

    @Override
    public void onError(Throwable t)
    {
        if (t instanceof SocketTimeoutException)
        {
            //超时异常
            onFailed(new RxException("网络请求超时，请重试", RetrofitConfig.HTTP_TIME_OUT));
        }
        else if (t instanceof RxException)
        {
            //这里可以自定义返回的类型，比如强制退出登录什么的
            onFailed((RxException) t);
        }
        else
        {
            //其他异常
            onFailed(new RxException(t.getMessage(), RetrofitConfig.HTTP_OTHER_ERROR));
        }
        mSubscription.cancel();
    }

    @Override
    public void onComplete()
    {
        if (t == null)
            onSuccess(null);
        mSubscription.cancel();
    }
}
