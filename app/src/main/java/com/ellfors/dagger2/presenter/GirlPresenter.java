package com.ellfors.dagger2.presenter;


import com.ellfors.dagger2.base.BasePresenter;
import com.ellfors.dagger2.view.GirlView;

public interface GirlPresenter extends BasePresenter<GirlView>
{
    void getGirlList();
}
