package com.xhsx.service.core.xmpp.task;

import android.util.Log;

import com.xhsx.service.core.xmpp.XmppManager;

/**
 * 作者：lizy on 2016/10/13 16:22
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class XmppRegisterTask implements Runnable {
    private String LOGTAG = XmppRegisterTask.class.getName();
    final XmppManager xmppManager;

    public XmppRegisterTask(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    public void run() {
      Log.i(LOGTAG, "RegisterTask.run()...");

        if (!xmppManager.isRegistered()) {
          /*    final String newUsername = newRandomUUID();
            final String newPassword = newRandomUUID();

            Registration registration = new Registration();

            PacketFilter packetFilter = new AndFilter(new PacketIDFilter(
                    registration.getPacketID()), new PacketTypeFilter(
                    IQ.class));

            PacketListener packetListener = new PacketListener() {

                public void processPacket(Packet packet) {
                    Log.d("RegisterTask.PacketListener",
                            "processPacket().....");
                    Log.d("RegisterTask.PacketListener", "packet="
                            + packet.toXML());

                    if (packet instanceof IQ) {
                        IQ response = (IQ) packet;
                        if (response.getType() == IQ.Type.ERROR) {
                            if (!response.getError().toString().contains(
                                    "409")) {
                                Log.e(LOGTAG,
                                        "Unknown error while registering XMPP account! "
                                                + response.getError()
                                                .getCondition());
                            }
                        } else if (response.getType() == IQ.Type.RESULT) {
                            xmppManager.setUsername(newUsername);
                            xmppManager.setPassword(newPassword);
                            Log.d(LOGTAG, "username=" + newUsername);
                            Log.d(LOGTAG, "password=" + newPassword);

                            Editor editor = sharedPrefs.edit();
                            editor.putString(Constants.XMPP_USERNAME,
                                    newUsername);
                            editor.putString(Constants.XMPP_PASSWORD,
                                    newPassword);
                            editor.commit();
                            Log
                                    .i(LOGTAG,
                                            "Account registered successfully");
                            xmppManager.runTask();
                        }
                    }
                }
            };

            connection.addPacketListener(packetListener, packetFilter);

            registration.setType(IQ.Type.SET);
            // registration.setTo(xmppHost);
            // Map<String, String> attributes = new HashMap<String, String>();
            // attributes.put("username", rUsername);
            // attributes.put("password", rPassword);
            // registration.setAttributes(attributes);
            registration.addAttribute("username", newUsername);
            registration.addAttribute("password", newPassword);
            connection.sendPacket(registration);
*/
        } else {
            Log.i(LOGTAG, "Account registered already");
            xmppManager.runTask();
        }
    }
}