package com.ellfors.dagger2.di.module;

import android.app.Activity;

import com.ellfors.dagger2.di.ActivityScope;

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
}
