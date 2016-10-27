package com.xhsx.service.core.xmpp;

import android.content.Context;

import com.xhsx.service.core.IConnectingBridge;

/**
 * 作者：lizy on 2016/10/13 15:27
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class XMPPConnectingBridgeImpl implements IConnectingBridge {
    private com.xhsx.service.core.xmpp.XmppManager xmppManager = null;
    @Override
    public void connect(Context context) {
        if(xmppManager==null)
            xmppManager = new com.xhsx.service.core.xmpp.XmppManager(context);
        xmppManager.connect();
    }

    @Override
    public void disConnect(Context context) {
        if(xmppManager!=null)
            xmppManager.disconnect();
    }
}
