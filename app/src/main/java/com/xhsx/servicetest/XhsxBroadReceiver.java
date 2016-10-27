package com.xhsx.servicetest;

/**
 * 作者：lizy on 2016/10/13 10:10
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.WindowManager;
public class XhsxBroadReceiver extends BroadcastReceiver{
    private static final String ACTION = "xinhuashixun_app_1";
    @Override
    public void onReceive(Context context, Intent intent) {
        //广播接受
        if (intent.getAction().equals(ACTION)){
            String name=intent.getStringExtra("body");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示")
                    .setMessage("收到BroadcastSend应用程序的广播"+name)
                    .setPositiveButton("确定", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("取消", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = (AlertDialog) builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }
    }

}
