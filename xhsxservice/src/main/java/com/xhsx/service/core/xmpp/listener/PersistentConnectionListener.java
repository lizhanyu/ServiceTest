package com.xhsx.service.core.xmpp.listener;

import android.util.Log;

import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XmppManager;

import org.jivesoftware.smack.ConnectionListener;

/**
 * 作者：lizy on 2016/10/14 14:10
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class PersistentConnectionListener implements ConnectionListener {

    private static final String LOGTAG =  PersistentConnectionListener.class.getName();

    private final XmppManager xmppManager;

    public PersistentConnectionListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void connectionClosed() {
        Log.d(LOGTAG, "connectionClosed()...");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        LogUtil.debug(LOGTAG, "connectionClosedOnError()...");
        if (xmppManager.getConnection() != null
                && xmppManager.getConnection().isConnected()) {
            xmppManager.getConnection().disconnect();
        }
        xmppManager.startReconnectionThread();
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.d(LOGTAG, "reconnectingIn()...");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.d(LOGTAG, "reconnectionFailed()...");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.d(LOGTAG, "reconnectionSuccessful()...");
    }

}
