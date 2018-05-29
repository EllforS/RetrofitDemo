package com.ellfors.dagger2.http.utils;

import android.text.TextUtils;
import android.util.Log;

import com.ellfors.extools.utils.ExLoggerUtil;

/**
 * 网络请求打印工具类
 * 2018/3/6 16:26
 */
public class HttpLogUtil
{
    private static final String[] tyeps =
            {
                    "<-- END HTTP",
                    "Connection:",
                    "Content-Length:",
                    "Content-Type:",
                    "Date:",
                    "EagleId:",
                    "Timing-Allow-Origin:",
                    "Transfer-Encoding:",
                    "Server:",
                    "Set-Cookie:",
                    "Vary:",
                    "Via:",
                    "X-Cache:",
                    "X-Swift-CacheTime:",
                    "X-Swift-SaveTime:"
            };

    public static void log(String tag, String message)
    {
        if (!filterType(message))
            return;
        if (message.substring(0, 1).equals("{"))
            ExLoggerUtil.json(message);
        else
            Log.i("AAA", message);
    }

    /**
     * 过滤类型
     */
    private static boolean filterType(String message)
    {
        if (TextUtils.isEmpty(message))
            return false;
        for (String str : tyeps)
        {
            if (message.startsWith(str))
                return false;
        }
        return true;
    }
}
