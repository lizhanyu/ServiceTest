package com.xhsx.service.core;

/**
 * 作者：lizy on 2016/10/12 17:02
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.xhsx.service.core.common.Constants;
import com.xhsx.service.core.common.util.SpUtil;

/**
 * 轮询工具类
 * @author lizy
 *
 */
public class PollingUtils {
    private PollingService pollingService;
    //开启轮询服务
    public static void startPollingService(PollingServiceConfig pollingServiceConfig) {
        checkParamter(pollingServiceConfig);
        SpUtil.putString(pollingServiceConfig.getContext(), Constants.USER_NAME, pollingServiceConfig.getUserName());
        SpUtil.putString(pollingServiceConfig.getContext(), Constants.PASS_WORD, pollingServiceConfig.getPassWord());

        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) pollingServiceConfig.getContext()
                .getSystemService(Context.ALARM_SERVICE);

        //包装需要执行Service的Intent
        Intent intent = new Intent(pollingServiceConfig.getContext(), pollingServiceConfig.getCls());
        intent.setAction(pollingServiceConfig.getAction());
        PendingIntent pendingIntent = PendingIntent.getService(pollingServiceConfig.getContext(),
                0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //触发服务的起始时间
        long triggerAtTime = SystemClock.elapsedRealtime()+100;
        int second = pollingServiceConfig.getSeconds()==0 ? 2:pollingServiceConfig.getSeconds();

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
                second * 1000, pendingIntent);

    }


    //停止轮询服务
    public static void stopPollingService(Context context, Class<?> cls,String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //取消正在执行的服务
        manager.cancel(pendingIntent);
    }
    public static void checkParamter(PollingServiceConfig pollingServiceConfig){
        if(pollingServiceConfig ==null)
            throw new IllegalArgumentException("参数错误");
        if(pollingServiceConfig.getContext()==null)
            throw new IllegalArgumentException("参数错误");
        if(pollingServiceConfig.getUserName()==null)
            throw new IllegalArgumentException("参数错误");
        if(pollingServiceConfig.getPassWord()==null)
            throw new IllegalArgumentException("参数错误");
        if(pollingServiceConfig.getAction()==null)
            throw new IllegalArgumentException("参数错误");


    }
}
