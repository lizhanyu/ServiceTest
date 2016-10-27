package com.xhsx.service.core.xmpp.iq;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * 作者：lizy on 2016/10/13 17:43
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class NotificationIQProvider implements IQProvider {

    public NotificationIQProvider() {
    }

    @Override
    public IQ parseIQ(XmlPullParser parser) throws Exception {

        NotificationIQ notification = new NotificationIQ();
        /*如果是通过IQ传递，那么在这里解析操作*/

        return notification;
    }

}

