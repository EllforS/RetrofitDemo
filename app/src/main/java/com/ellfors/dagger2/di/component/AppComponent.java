package com.ellfors.dagger2.di.component;

import com.ellfors.dagger2.MyApplication;
import com.ellfors.dagger2.di.module.AppModule;
import com.ellfors.dagger2.http.utils.RetrofitManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{
    MyApplication myApplication();
    RetrofitManager retrofitManager();

    void inject(MyApplication myApplication);
}
