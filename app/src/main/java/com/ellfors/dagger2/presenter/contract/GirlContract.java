package com.ellfors.dagger2.presenter.contract;


import com.ellfors.dagger2.base.BasePresenter;
import com.ellfors.dagger2.base.BaseView;
import com.ellfors.dagger2.model.Girl;

import java.util.List;

public interface GirlContract
{
    interface View extends BaseView
    {
        void showList(List<Girl> list);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getGirlList();
    }
}
