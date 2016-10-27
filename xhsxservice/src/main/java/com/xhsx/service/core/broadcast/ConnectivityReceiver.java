package com.xhsx.service.core.broadcast;

/**
 * 作者：lizy on 2016/10/13 15:30
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.xhsx.service.core.IConnectingBridge;
import com.xhsx.service.core.common.util.LogUtil;

/**
 * A broadcast receiver to handle the changes in network connectiion states.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    private String LOGTAG = ConnectivityReceiver.class.getName();
    private IConnectingBridge connectingBridge;
    private Context context;
    public ConnectivityReceiver(IConnectingBridge connectingBridge,Context context) {
        this.connectingBridge = connectingBridge;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.info(LOGTAG, "ConnectivityReceiver.onReceive()...");
        String action = intent.getAction();
        LogUtil.debug(LOGTAG, "action=" + action);

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            LogUtil.debug(LOGTAG, "Network Type  = " + networkInfo.getTypeName());
            LogUtil.debug(LOGTAG, "Network State = " + networkInfo.getState());
            if (networkInfo.isConnected()) {
                Log.i(LOGTAG, "Network connected");
                connectingBridge.connect(context);
            }
        } else {
            Log.e(LOGTAG, "Network unavailable");
            connectingBridge.disConnect(context);
        }
    }

}