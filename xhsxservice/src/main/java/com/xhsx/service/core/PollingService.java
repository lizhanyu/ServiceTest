package com.xhsx.service.core;

/**
 * 作者：lizy on 2016/10/12 17:00
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.xhsx.service.core.broadcast.ConnectivityReceiver;
import com.xhsx.service.core.broadcast.PhoneStateChangeListener;
import com.xhsx.service.core.broadcast.ScreenReceiver;
import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XMPPConnectingBridgeImpl;


public class PollingService extends Service {
    public static final String ACTION = "com.xhsxservice.service";
    private TelephonyManager telephonyManager;
    private ScreenReceiver screenReceiver;
    private IConnectingBridge connectingBridge;
    private PhoneStateListener phoneStateListener;
    private BroadcastReceiver connectivityReceiver;
    private String LOGTAG = PollingService.class.getName();

    public PollingService(){
        connectingBridge = new XMPPConnectingBridgeImpl(); //接入xmpp协议的实现
        connectivityReceiver = new ConnectivityReceiver(connectingBridge,this); //加入手机网络监听
        phoneStateListener = new PhoneStateChangeListener(connectingBridge,this);//加入手机状态监听
        screenReceiver = new ScreenReceiver(connectingBridge,this);
    }
    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.info(LOGTAG, "轮询服务被创建onCreate");
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        registerConnectivityReceiver();
        registerScreenReceiver();
       /*  调用shell,fork子进程的方式，实现守护进程，5.x以下没有问题，5.x以上不行，还在研究，不过，
       *    抵制流氓程序，共同维护绿色的安卓生态环境，不加这个功能
       *    http://coolerfall.com/android/android-app-daemon
       *    Daemon.run(this, PollingService.class, Daemon.INTERVAL_ONE_MINUTE * 2);  //加入守护进程
       *    或者
       *    http://blog.csdn.net/marswin89/article/details/50917098
       * */

    //
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.info(LOGTAG, "onStart");
        connectingBridge.connect(this);//连接服务端
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtil.info(LOGTAG, "轮询服务销毁了");
        unRegisterScreenReceiver();
        unregisterConnectivityReceiver();
        connectingBridge.disConnect(this);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // 当调用者退出(即使没有调用unbindService)或者主动停止服务时会调用
        return super.onUnbind(intent);
    }
    private void registerScreenReceiver() {
        LogUtil.info(LOGTAG, "registerScreenReceiver()...");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);
    }
    private void unRegisterScreenReceiver() {
        LogUtil.info(LOGTAG, "unRegisterScreenReceiver()...");
        unregisterReceiver(screenReceiver);
    }
    private void registerConnectivityReceiver() {
        LogUtil.info(LOGTAG, "registerConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        IntentFilter filter = new IntentFilter();
        // filter.addAction(android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
    }

    private void unregisterConnectivityReceiver() {
        LogUtil.info(LOGTAG, "unregisterConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(connectivityReceiver);
    }
}
