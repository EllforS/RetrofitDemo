package com.ellfors.dagger2.presenter.impl;

import android.app.Activity;

import com.ellfors.dagger2.base.BasePresenterImpl;
import com.ellfors.dagger2.http.config.RetrofitConfig;
import com.ellfors.dagger2.http.utils.RetrofitManager;
import com.ellfors.dagger2.http.utils.RxException;
import com.ellfors.dagger2.http.utils.RxUtils;
import com.ellfors.dagger2.http.utils.progress.ProgressSubscriber;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.dagger2.presenter.contract.GirlContract;

import java.util.List;

import javax.inject.Inject;

public class GirlPresenterImpl extends BasePresenterImpl<GirlContract.View> implements GirlContract.Presenter
{
    private Activity mContext;
    private RetrofitManager manager;

    @Inject
    public GirlPresenterImpl(Activity activity, RetrofitManager manager)
    {
        this.mContext = activity;
        this.manager = manager;
    }

    @Override
    public void getGirlList()
    {
        //自带ProgressDialog的请求
        manager.getGsonHttpApi()
                .getGirlList(10, 1)
                .compose(RxUtils.<List<Girl>>handleResult())
                .compose(RxUtils.<List<Girl>>rxSchedulerHelper())
                .subscribe(new ProgressSubscriber<List<Girl>>(mContext)
                {
                    @Override
                    public void onSuccess(List<Girl> girls)
                    {
                        //这里一定要判断非空，因为可能回调的Context已经detachView了，这时View就会为空
                        if (mView != null)
                            mView.showList(girls);
                    }

                    @Override
                    public void onFailed(Throwable e)
                    {
                        if (mView != null)
                            mView.showError(new RxException(e.getMessage(), RetrofitConfig.HTTP_OTHER_ERROR));
                    }
                });

        //不带ProgressDialog的请求
//        manager.getGsonHttpApi()
//                .getGirlList(10, 1)
//                .compose(RxUtils.<List<Girl>>handleResult())
//                .compose(RxUtils.<List<Girl>>rxSchedulerHelper())
//                .subscribe(new BaseSubscriber<List<Girl>>()
//                {
//                    @Override
//                    public void onSuccess(List<Girl> girls)
//                    {
//                        if (mView != null)
//                            mView.showList(girls);
//                    }
//
//                    @Override
//                    public void onFailed(RxException e)
//                    {
//                        if (mView != null)
//                            mView.showError(e);
//                    }
//                });
    }
}
