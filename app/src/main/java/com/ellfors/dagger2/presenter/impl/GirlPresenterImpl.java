package com.ellfors.dagger2.presenter.impl;


import com.ellfors.dagger2.base.BasePresenterImpl;
import com.ellfors.dagger2.http.utils.ProgressSubscriber;
import com.ellfors.dagger2.http.utils.RetrofitManager;
import com.ellfors.dagger2.http.utils.RxUtils;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.dagger2.presenter.GirlPresenter;
import com.ellfors.dagger2.view.GirlView;
import com.ellfors.extools.utils.L;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class GirlPresenterImpl extends BasePresenterImpl implements GirlPresenter
{
    private GirlView girlView;
    private RetrofitManager manager;

    @Inject
    public GirlPresenterImpl(GirlView girlView, RetrofitManager manager)
    {
        this.girlView = girlView;
        this.manager = manager;
    }

    @Override
    public void getGirlList()
    {
        Subscription sub = manager
                .getGsonHttpApi()
                .getGirlList(10,1)
                .compose(RxUtils.<List<Girl>>handleResult())
                .compose(RxUtils.<List<Girl>>rxSchedulerHelper())
                .subscribe(new ProgressSubscriber<List<Girl>>(girlView.getContext())
                {
                    @Override
                    public void onSuccess(List<Girl> girls)
                    {
                        girlView.showList(girls);
                    }

                    @Override
                    public void onFailed(Throwable e)
                    {
                        L.e("获取列表 Error = " + e.getMessage());
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void unSub()
    {
        unSubscribe();
    }
}
