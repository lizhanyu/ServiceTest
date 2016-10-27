package com.xhsx.service.core.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xhsx.service.core.PollingService;
import com.xhsx.service.core.PollingServiceConfig;
import com.xhsx.service.core.PollingUtils;
import com.xhsx.service.core.common.util.LogUtil;

/**
 * 作者：lizy on 2016/10/17 15:00
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    //重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtil.info("TAG", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        PollingServiceConfig pollingServiceConfig = new PollingServiceConfig.Builder(context).action(PollingService.ACTION)
                .cls(PollingService.class).passWord("123456").userName("05fd50e477d043a4b56b6674cc373608_3").seconds(2).build();
        PollingUtils.startPollingService(pollingServiceConfig);
        Toast.makeText(context,"服务已经创建",Toast.LENGTH_SHORT).show();
    }

}
