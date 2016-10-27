package com.xhsx.service.core.common.util;

import android.util.Log;

import com.xhsx.service.core.common.Constants;

/**
 * 作者：lizy on 2016/10/13 10:48
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class LogUtil {

    public static void info(String tag,String info){
        if (Constants.debug)
            Log.i(tag == null ? "info Tag" : tag, info == null ? "info message" : info);
    }

    public static void debug(String tag,String info){
        if (Constants.debug)
            Log.d(tag == null ? "debug Tag" : tag, info == null ? "debug message" : info);
    }
    public static void error(String tag,String info){
        if (Constants.debug)
            Log.e(tag == null ? "error Tag" : tag, info == null ? "error message" : info);
    }
    public static void warn(String tag,String info){
        if (Constants.debug)
            Log.e(tag == null ? "error Tag" : tag, info == null ? "warn message" : info);
    }
}
