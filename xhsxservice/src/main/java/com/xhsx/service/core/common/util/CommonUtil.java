package com.xhsx.service.core.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：lizy on 2016/10/17 10:37
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class CommonUtil {
    /**
     * 检验网络连接
     * @author lizy
     * @return
     */
    public static boolean isNetConnect(Context context) {
        ConnectivityManager con = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = con.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            // 当前网络不可用
            return false;
        }
        return true;

    }
}
