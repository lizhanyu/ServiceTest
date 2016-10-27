package com.xhsx.service.core.xmpp.task;

import android.util.Log;

import com.xhsx.service.core.common.Constants;
import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XmppManager;
import com.xhsx.service.core.xmpp.iq.NotificationIQProvider;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

import java.io.File;

/**
 * 作者：lizy on 2016/10/13 16:31
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class XmppConnectTask implements Runnable {
    private String LOGTAG = XmppConnectTask.class.getName();
    final XmppManager xmppManager;

    public XmppConnectTask(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    public void run() {
        LogUtil.info(LOGTAG, "ConnectTask.run()...");

        if (!xmppManager.isConnected()) {
            ConnectionConfiguration connConfig = new ConnectionConfiguration(
                    Constants.XMPP_HOST, Constants.XMPP_PORT,Constants.SERVER_HOSTNAME);
            connConfig.setReconnectionAllowed(true);
            connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
            File file =new File("/mnt/sdcard/security/");
            file.mkdirs();
            connConfig .setTruststorePath("/mnt/sdcard/security/cacerts.bks");
            connConfig.setTruststorePassword("123456");
            connConfig.setTruststoreType("bks");
            connConfig.setSASLAuthenticationEnabled(true);
            connConfig.setCompressionEnabled(false);

            XMPPConnection connection = new XMPPConnection(connConfig);
            xmppManager.setConnection(connection);

            try {
                // Connect to the server
                connection.connect();
                Log.i(LOGTAG, "XMPP connected successfully");

                // packet provider
                ProviderManager.getInstance().addIQProvider("notification",
                        "androidpn:iq:notification",
                        new NotificationIQProvider());

            } catch (XMPPException e) {
                Log.e(LOGTAG, "XMPP connection failed", e);
            }

            xmppManager.runTask();

        } else {
            Log.i(LOGTAG, "XMPP connected already");
            xmppManager.runTask();
        }
    }
}
