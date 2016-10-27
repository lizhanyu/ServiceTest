package com.xhsx.service.core.xmpp.listener;

import android.content.Intent;

import com.xhsx.service.core.common.Constants;
import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.XmppManager;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * 作者：lizy on 2016/10/14 14:13
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class NotificationPacketListener implements PacketListener {

    private static final String LOGTAG =  NotificationPacketListener.class.getName();

    private final XmppManager xmppManager;

    public NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
        LogUtil.debug(LOGTAG, "NotificationPacketListener. INIT()...");
    }

    @Override
    public void processPacket(Packet packet) {
        LogUtil.debug(LOGTAG, "NotificationPacketListener.processPacket()...");
        LogUtil.debug(LOGTAG, "packet.toXML()=" + packet.toXML());
        if(packet instanceof  Message) {
            String broadcastIntent = Constants.BROADCASTINTENT_APP;//自己自定义
            Message message = (Message) packet;
            Intent intent = new Intent(broadcastIntent);
            intent.putExtra(Constants.BODY, message.getBody());
            intent.putExtra(Constants.SUBJECT, message.getSubject());
            intent.putExtra(Constants.FROM, message.getFrom());
            intent.putExtra(Constants.TO, message.getTo());
            xmppManager.getContext().sendBroadcast(intent);
        }

    }

}
