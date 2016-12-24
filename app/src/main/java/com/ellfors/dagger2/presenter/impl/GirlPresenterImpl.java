package com.ellfors.dagger2.presenter.impl;

import com.ellfors.dagger2.base.BasePresenterImpl;
import com.ellfors.dagger2.http.utils.ProgressSubscriber;
import com.ellfors.dagger2.http.utils.RetrofitManager;
import com.ellfors.dagger2.http.utils.RxUtils;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.dagger2.presenter.contract.GirlContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class GirlPresenterImpl extends BasePresenterImpl<GirlContract.View> implements GirlContract.Presenter
{
    private RetrofitManager manager;

    @Inject
    public GirlPresenterImpl(RetrofitManager manager)
    {
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
                .subscribe(new ProgressSubscriber<List<Girl>>(mView.getContext())
                {
                    @Override
                    public void onSuccess(List<Girl> girls)
                    {
                        mView.showList(girls);
                    }

                    @Override
                    public void onFailed(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }
                });
        addSubscribe(sub);
    }
}
