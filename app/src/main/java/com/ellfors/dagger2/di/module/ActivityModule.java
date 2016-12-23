package com.ellfors.dagger2.di.module;

import android.app.Activity;

import com.ellfors.dagger2.di.ActivityScope;
import com.ellfors.dagger2.http.utils.RetrofitManager;
import com.ellfors.dagger2.presenter.GirlPresenter;
import com.ellfors.dagger2.presenter.impl.GirlPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity()
    {
        return activity;
    }

    @Provides
    @ActivityScope
    GirlPresenter provideGirlPresenter(RetrofitManager manager)
    {
        return new GirlPresenterImpl(manager);
    }
}
