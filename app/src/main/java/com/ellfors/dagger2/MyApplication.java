package com.ellfors.dagger2;

import com.ellfors.dagger2.di.component.AppComponent;
import com.ellfors.dagger2.di.component.DaggerAppComponent;
import com.ellfors.dagger2.di.module.AppModule;
import com.ellfors.extools.base.ExBaseApplication;
import com.ellfors.extools.utils.L;

public class MyApplication extends ExBaseApplication
{
    private static MyApplication mContext;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mContext = this;

        L.init("AAA",2,false,0);
    }

    /**
     * 获取AppComponent
     */
    public static AppComponent getAppComponent()
    {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(mContext))
                .build();
    }

}
