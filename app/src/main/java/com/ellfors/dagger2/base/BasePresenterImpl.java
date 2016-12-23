package com.ellfors.dagger2.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于Rxjava的PresenterImpl基类
 */
public class BasePresenterImpl
{
    public CompositeSubscription subscriptions;

    /* 绑定SubScriber */
    public void addSubscribe(Subscription sub)
    {
        if(subscriptions == null)
        {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(sub);
    }

    /* 取消绑定 */
    public void unSubscribe()
    {
        if(subscriptions != null)
            subscriptions.unsubscribe();
    }
}
