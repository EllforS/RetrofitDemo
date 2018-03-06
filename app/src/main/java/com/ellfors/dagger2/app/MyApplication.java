package com.ellfors.dagger2.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.ellfors.dagger2.di.component.AppComponent;
import com.ellfors.dagger2.di.component.DaggerAppComponent;
import com.ellfors.dagger2.di.module.AppModule;
import com.ellfors.extools.base.ExBaseApplication;
import com.ellfors.extools.utils.L;

public class MyApplication extends ExBaseApplication
{
    private static MyApplication mContext;
    public static boolean isDebug = false;

    @Override
    public void onCreate()
    {
        super.onCreate();

        mContext = this;
        isDebug = isApkDebugable(this);
        L.init("AAA", 0, false, 0);
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

    /**
     * apk是否是debug版本
     *
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context)
    {
        try
        {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return false;
    }

}
