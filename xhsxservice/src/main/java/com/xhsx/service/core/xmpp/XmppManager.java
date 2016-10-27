package com.xhsx.service.core.xmpp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.xhsx.service.core.common.util.LogUtil;
import com.xhsx.service.core.xmpp.listener.NotificationPacketListener;
import com.xhsx.service.core.xmpp.listener.PersistentConnectionListener;
import com.xhsx.service.core.xmpp.task.ReconnectionThread;
import com.xhsx.service.core.xmpp.task.XmppConnectTask;
import com.xhsx.service.core.xmpp.task.XmppLoginTask;
import com.xhsx.service.core.xmpp.task.XmppRegisterTask;
import com.xhsx.service.core.xmpp.ConnectThreadPool;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 作者：lizy on 2016/10/13 16:20
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class XmppManager {
    private List<Runnable> taskList;
    private String LOGTAG = XmppManager.class.getName();
    private boolean running = false;
    private XMPPConnection connection;
    private ConnectionListener connectionListener;
    private ConnectThreadPool connectThreadPool;
    private PacketListener notificationPacketListener;
    private Thread reconnection;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PacketListener getNotificationPacketListener() {
        return notificationPacketListener;
    }
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setNotificationPacketListener(PacketListener notificationPacketListener) {
        this.notificationPacketListener = notificationPacketListener;
    }

    private Future<?> futureTask;
    public XmppManager(Context context){
        connectThreadPool = new ConnectThreadPool();
        taskList = new ArrayList<Runnable>();
        reconnection = new ReconnectionThread( this);
        handler = new Handler();
        connectionListener = new PersistentConnectionListener(this);
        notificationPacketListener = new NotificationPacketListener(this);
        this.context = context;
    }
    public Future<?> getFutureTask() {
        return futureTask;
    }
    private void submitConnectTask() {
        Log.d(LOGTAG, "submitConnectTask()...");
        addTask(new XmppConnectTask(this));
    }

    private void submitRegisterTask() {
        Log.d(LOGTAG, "submitRegisterTask()...");
        submitConnectTask();
        addTask(new XmppRegisterTask(this));
    }

    private void submitLoginTask() {
        Log.d(LOGTAG, "submitLoginTask()...");
        submitRegisterTask();
        addTask(new XmppLoginTask(this));
    }
    public void runTask() {
        Log.d(LOGTAG, "runTask()...");
        synchronized (taskList) {
            running = false;
            futureTask = null;
            if (!taskList.isEmpty()) {
                Runnable runnable = (Runnable) taskList.get(0);
                taskList.remove(0);
                running = true;
                futureTask = connectThreadPool.submit(runnable);
                if (futureTask == null) {
                 //   taskTracker.decrease();
                }
            }
        }
       // taskTracker.decrease();
        Log.d(LOGTAG, "runTask()...done");
    }
    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }
 //这块先不实现
    public boolean isRegistered() {
        return true;
    }

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    public boolean isAuthenticated() {
        return connection != null && connection.isConnected()
                && connection.isAuthenticated();
    }
    private void addTask(Runnable runnable) {
        Log.d(LOGTAG, "addTask(runnable)...");
       // taskTracker.increase();
        synchronized (taskList) {
            if (taskList.isEmpty() && !running) {
                running = true;
                futureTask = connectThreadPool.submit(runnable);
                if (futureTask == null) {
                  //  taskTracker.decrease();
                }
            } else {
                taskList.add(runnable);
            }
        }
        Log.d(LOGTAG, "addTask(runnable)... done");
    }
    public void connect() {
        Log.d(LOGTAG, "connect()...");
        submitLoginTask();
    }

    public void disconnect() {
        Log.d(LOGTAG, "disconnect()...");
        terminatePersistentConnection();
        connectThreadPool.shutDown();
    }
    public void terminatePersistentConnection() {
        Log.d(LOGTAG, "terminatePersistentConnection()...");
        Runnable runnable = new Runnable() {

            final XmppManager xmppManager = XmppManager.this;

            public void run() {
                if (xmppManager.isConnected()) {
                    LogUtil.debug(LOGTAG, "terminatePersistentConnection()... run()");
                    xmppManager.getConnection().removePacketListener(
                            xmppManager.getNotificationPacketListener());
                    xmppManager.getConnection().disconnect();
                }
                xmppManager.runTask();
            }

        };
        addTask(runnable);
    }
    public XMPPConnection getConnection() {
        return connection;
    }
    public void startReconnectionThread() {
        synchronized (reconnection) {
            if (!reconnection.isAlive()) {
                reconnection.setName("Xmpp Reconnection Thread");
                reconnection.start();
            }
        }
    }
    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
    }



}
