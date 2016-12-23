package com.ellfors.dagger2.di.component;

import android.app.Activity;

import com.ellfors.dagger2.di.ActivityScope;
import com.ellfors.dagger2.di.module.ActivityModule;
import com.ellfors.dagger2.presenter.GirlPresenter;
import com.ellfors.dagger2.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent
{
    Activity activity();
    GirlPresenter girlPresenter();

    void inject(MainActivity activity);
}
