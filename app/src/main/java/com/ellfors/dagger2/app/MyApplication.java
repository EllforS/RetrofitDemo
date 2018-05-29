package com.ellfors.dagger2.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.ellfors.dagger2.di.component.AppComponent;
import com.ellfors.dagger2.di.component.DaggerAppComponent;
import com.ellfors.dagger2.di.module.AppModule;
import com.ellfors.extools.base.ExBaseApplication;
import com.ellfors.extools.utils.ExLoggerUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MyApplication extends ExBaseApplication
{
    private static Stack<Activity> stack;
    private static MyApplication mContext;
    public static boolean isDebug = false;
    private boolean mIsForeProcess = false;

    public static MyApplication getInstance()
    {
        return mContext;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mContext = this;
        isDebug = isApkDebugable(this);
        ExLoggerUtil.init("AAA", 0, false, 0);
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

    /**
     * 将Activity加入栈
     */
    public void addTask(Activity activity)
    {
        if (stack == null)
            stack = new Stack();
        stack.add(activity);
    }

    /**
     * 将Activity移出栈
     */
    public void removeTask(Activity activity)
    {
        if (stack != null && activity != null && stack.contains(activity))
            stack.remove(activity);
    }

    /**
     * 获取栈顶的Activity
     */
    public Activity currentActivity()
    {
        return stack.lastElement() == null ? new Activity() : stack.lastElement();
    }

    /**
     * 销毁Acitivity
     */
    public void finishLastActivity()
    {
        Activity activity = stack.lastElement();
        if (activity != null)
            this.finishActivity(activity);
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Activity activity)
    {
        if (activity != null && stack != null)
        {
            stack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Class<?> cls)
    {
        if (cls == null || stack == null)
            return;
        for (Activity activity : stack)
        {
            if (activity.getClass().equals(cls))
            {
                this.finishActivity(activity);
            }
        }
    }

    /**
     * 判断是否含有Activity
     */
    public boolean hasActivity(Class<?> cls)
    {
        Iterator var2 = stack.iterator();
        Activity activity;
        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            activity = (Activity) var2.next();
        } while (!activity.getClass().equals(cls));
        return true;
    }

    /**
     * 销毁全部Activity
     */
    public void finishAllActivity()
    {
        int i = 0;
        for (int size = stack.size(); i < size; ++i)
        {
            if (null != stack.get(i))
            {
                stack.get(i).finish();
            }
        }
        stack.clear();
    }

    /**
     * 判断是否大于当前版本
     */
    public static boolean isMethodsCompat(int VersionCode)
    {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 校验软件是否在前台
     */
    private void checkMainProcess()
    {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = activityManager.getRunningAppProcesses();

        mIsForeProcess = false;
        String pkgName = getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : infoList)
        {
            if (appProcess.processName.equals(pkgName))
            {
                if (appProcess.pid == android.os.Process.myPid())
                {
                    mIsForeProcess = true;
                    break;
                }
            }
        }
    }

}
