package com.ellfors.dagger2.base;

import android.os.Bundle;

import com.ellfors.dagger2.MyApplication;
import com.ellfors.dagger2.di.component.ActivityComponent;
import com.ellfors.dagger2.di.component.DaggerActivityComponent;
import com.ellfors.dagger2.di.module.ActivityModule;
import com.ellfors.extools.base.ExBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 */
public abstract class BaseActivity extends ExBaseActivity
{
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initInject();
        initEventAndData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(unbinder != null)
            unbinder.unbind();
    }

    /**
     * 获取ActivityComponent
     */
    public ActivityComponent getActivityComponent()
    {
        return DaggerActivityComponent
                .builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * 获取ActivityModule
     */
    public ActivityModule getActivityModule()
    {
        return new ActivityModule(this);
    }

    /* 初始化注入 */
    public abstract void initInject();
    /* 绑定布局 */
    public abstract int getLayout();
    /* 初始化事件和数据 */
    public abstract void initEventAndData();
}
