package com.ellfors.dagger2.http.utils;


import com.ellfors.dagger2.http.model.BaseCallModel;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换、统一封装回调处理
 */
public class RxUtils
{
    /**
     * 统一线程处理
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper()
    {
        //compose简化线程
        return new FlowableTransformer<T, T>()
        {
            @Override
            public Publisher<T> apply(Flowable<T> upstream)
            {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     * 这里用于处理GankApi的BaseModel
     * 不同BaseUrl的请求BaseModel也不同
     */
    public static <T> FlowableTransformer<BaseCallModel<T>, T> handleResult()
    {
        //compose判断结果
        return new FlowableTransformer<BaseCallModel<T>, T>()
        {
            @Override
            public Publisher<T> apply(Flowable<BaseCallModel<T>> upstream)
            {
                return upstream.flatMap(new Function<BaseCallModel<T>, Publisher<T>>()
                {
                    @Override
                    public Publisher<T> apply(BaseCallModel<T> tBaseCallModel) throws Exception
                    {
                        if (!tBaseCallModel.isError())
                        {
                            return createData(tBaseCallModel.getResults());
                        }
                        else
                        {
                            /*
                                可以根据不同的Error_code返回不同的Exception
                                比如token过期，需要重新登录
                             */
                            return Flowable.error(new Exception("这里是自定义Error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createData(final T t)
    {
        /*
          背压模式说明
          BackpressureStrategy.ERROR       直接抛出异常 MissingBackpressureException
          BackpressureStrategy.MISSING     友好提示：缓存区满了
          BackpressureStrategy.BUFFER      将缓存区大小设置成无限大(但要注意内存情况，防止出现OOM)
          BackpressureStrategy.DROP        超过缓存区大小（128）的事件丢弃
          BackpressureStrategy.LATEST      超过缓存区大小（128）的事件丢弃,并保存最后一个事件
         */
        return Flowable.create(new FlowableOnSubscribe<T>()
        {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception
            {
                try
                {
                    emitter.onNext(t);
                    emitter.onComplete();
                }
                catch (Exception e)
                {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
