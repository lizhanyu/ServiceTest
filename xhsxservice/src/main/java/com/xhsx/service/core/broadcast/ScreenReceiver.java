package com.xhsx.service.core.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xhsx.service.core.IConnectingBridge;
import com.xhsx.service.core.common.util.CommonUtil;
import com.xhsx.service.core.common.util.LogUtil;

/**
 * 作者：lizy on 2016/10/17 09:44
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class ScreenReceiver extends BroadcastReceiver {
    private String LOGTAG = ScreenReceiver.class.getName();
    private IConnectingBridge connectingBridge;
    private Context context;
    public ScreenReceiver(IConnectingBridge connectingBridge,Context context){
        this.connectingBridge = connectingBridge;
        this.context = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.debug(LOGTAG, "监听到锁屏变化");
        if(CommonUtil.isNetConnect(context))
            connectingBridge.connect(context);
    }
}
