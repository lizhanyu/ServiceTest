package com.xhsx.service.core.common;

import android.content.Context;

import com.xhsx.service.core.common.util.SpUtil;

/**
 * 作者：lizy on 2016/10/13 17:57
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class UserService {
    public static String getUserName(Context context) {
        userName= SpUtil.getString(context,Constants.USER_NAME,"");
        return userName;
    }

    public   void setUserName(String userName) {
        this.userName = userName;
    }

    public static String getPassword(Context context) {
        password =  SpUtil.getString(context,Constants.PASS_WORD,"");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static String userName ;
    private  static String password;
}
