package com.ellfors.dagger2.http.utils;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

/**
 * 简单的Subscriber
 */
public abstract class SimpleSubscriber<T> implements FlowableSubscriber<T>
{
    private Subscription mSubscription;

    @Override
    public void onSubscribe(Subscription s)
    {
        mSubscription = s;
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onComplete()
    {
        mSubscription.cancel();
    }
}
