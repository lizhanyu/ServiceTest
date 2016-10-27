package com.xhsx.service.core.xmpp.iq;

import org.jivesoftware.smack.packet.IQ;

/**
 * 作者：lizy on 2016/10/13 17:47
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class NotificationIQ   extends IQ {

    private String id;


    private String title;

    private String message;

    private String uri;

    public NotificationIQ() {
    }

    @Override
    public String getChildElementXML() {
        StringBuilder buf = new StringBuilder();

        return buf.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String url) {
        this.uri = url;
    }

}
