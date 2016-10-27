package com.xhsx.service.core.xmpp.task;

import android.util.Log;

import com.xhsx.service.core.common.Constants;
import com.xhsx.service.core.common.UserService;
import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XmppManager;
import com.xhsx.service.core.xmpp.iq.NotificationIQ;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

/**
 * 作者：lizy on 2016/10/13 16:21
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class XmppLoginTask implements Runnable {
    private String LOGTAG = XmppLoginTask.class.getName();
    final XmppManager xmppManager;

    public XmppLoginTask(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    public void run() {
        LogUtil.info(LOGTAG, "LoginTask.run()...");
        LogUtil.warn(LOGTAG,"登陆验证");
        if (!xmppManager.isAuthenticated()) {
            LogUtil.debug(LOGTAG, "username=" + UserService.getUserName(xmppManager.getContext()));
            LogUtil.debug(LOGTAG, "password=" + UserService.getPassword(xmppManager.getContext()));
            LogUtil.warn(LOGTAG, "要重新登陆");
            try {
                xmppManager.getConnection().login(
                        UserService.getUserName(xmppManager.getContext()),
                        UserService.getPassword(xmppManager.getContext()), Constants.XMPP_RESOURCE_NAME);
                LogUtil.debug(LOGTAG, "Loggedn in successfully");
                Presence presence = new Presence(Presence.Type.available);
                Presence.Mode mode = Presence.Mode.valueOf(Constants.AVAILABLE);
                presence.setMode(mode);
              /*  presence.setStatus(Constants.STATUS_ONLINE);
                presence.setPriority(Constants.PRIORITY);*/
                xmppManager.getConnection().sendPacket(presence);
                // connection listener
                if (xmppManager.getConnectionListener() != null) {
                    xmppManager.getConnection().addConnectionListener(
                            xmppManager.getConnectionListener());
                }

                // packet filter
                PacketFilter packetFilter = new PacketTypeFilter(
                        NotificationIQ.class);
                // packet listener
                PacketListener packetListener = xmppManager
                        .getNotificationPacketListener();
                xmppManager.getConnection().addPacketListener(packetListener, new MessageTypeFilter(
                        Message.Type.normal));

                xmppManager.runTask();

            } catch (XMPPException e) {
                LogUtil.error(LOGTAG, "LoginTask.run()... xmpp error");
                LogUtil.error(LOGTAG, "Failed to login to xmpp server. Caused by: "
                        + e.getMessage());
                String INVALID_CREDENTIALS_ERROR_CODE = "401";
                String errorMessage = e.getMessage();
                if (errorMessage != null
                        && errorMessage
                        .contains(INVALID_CREDENTIALS_ERROR_CODE)) {
                 //   xmppManager.reregisterAccount();
                    return;
                }
                xmppManager.startReconnectionThread();

            } catch (Exception e) {
                Log.e(LOGTAG, "LoginTask.run()... other error");
                Log.e(LOGTAG, "Failed to login to xmpp server. Caused by: "
                        + e.getMessage());
                xmppManager.startReconnectionThread();
            }

        } else {
            Log.i(LOGTAG, "Logged in already");
            LogUtil.warn(LOGTAG, "不要重新登陆");
            xmppManager.runTask();
        }

    }
}