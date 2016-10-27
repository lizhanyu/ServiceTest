package com.xhsx.service.core.xmpp.task;

import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XmppManager;

/**
 * 作者：lizy on 2016/10/13 16:59
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class ReconnectionThread extends Thread {

    private static final String LOGTAG =  ReconnectionThread.class.getName();

    private final XmppManager xmppManager;

    private int waiting;

    public ReconnectionThread(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
        this.waiting = 0;
    }

    public void run() {
        try {
            while (!isInterrupted()) {
                LogUtil.debug(LOGTAG, "Trying to reconnect in " + waiting()
                        + " seconds");
                Thread.sleep((long) waiting() * 1000L);
                xmppManager.connect();
                waiting++;
            }
        } catch (final InterruptedException e) {
            xmppManager.getHandler().post(new Runnable() {
                public void run() {
                    xmppManager.getConnectionListener().reconnectionFailed(e);
                }
            });
        }
    }

    private int waiting() {
        if (waiting > 20) {
            return 600;
        }
        if (waiting > 13) {
            return 300;
        }
        return waiting <= 7 ? 10 : 60;
    }
}
