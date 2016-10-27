package com.xhsx.service.core.broadcast;

/**
 * 作者：lizy on 2016/10/13 15:39
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.xhsx.service.core.IConnectingBridge;
import com.xhsx.service.core.common.util.LogUtil;

/**
 * A listener class for monitoring changes in phone connection states.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class PhoneStateChangeListener extends PhoneStateListener {

    private     String LOGTAG = PhoneStateChangeListener.class.getName();

    private IConnectingBridge connectingBridge;
    private Context context;
    public PhoneStateChangeListener(IConnectingBridge connectingBridge,Context context) {
        this.connectingBridge = connectingBridge;
        this.context = context;
    }

    @Override
    public void onDataConnectionStateChanged(int state) {
        super.onDataConnectionStateChanged(state);
        LogUtil.debug(LOGTAG, "onDataConnectionStateChanged()...");
        LogUtil.debug(LOGTAG, "Data Connection State = " + getState(state));

        if (state == TelephonyManager.DATA_CONNECTED) {
            connectingBridge.connect(context);
        }
    }

    private String getState(int state) {
        switch (state) {
            case 0: // '\0'
                return "DATA_DISCONNECTED";
            case 1: // '\001'
                return "DATA_CONNECTING";
            case 2: // '\002'
                return "DATA_CONNECTED";
            case 3: // '\003'
                return "DATA_SUSPENDED";
        }
        return "DATA_<UNKNOWN>";
    }

}
