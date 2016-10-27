package com.xhsx.service.core;

import android.content.Context;

/**
 * 作者：lizy on 2016/10/14 16:25
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class PollingServiceConfig {
    private   Context context=null;
    private   int seconds = 0;
    private   Class<?> cls ;
    private String action;
    private String userName;
    private String passWord;

    public static class Builder {
        private   Context context ;
        private   int seconds = 0;
        private   Class<?> cls ;
        private String action;
        private String userName;
        private String passWord;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder passWord(String passWord) {
            this.passWord = passWord;
            return this;
        }
        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
        public Builder seconds(int seconds) {
            this.seconds = seconds;
            return this;
        }
        public Builder cls( Class<?> cls) {
            this.cls = cls;
            return this;
        }
        public Builder action(String action) {
            this.action = action;
            return this;
        }
        public PollingServiceConfig build() { // 构建，返回一个新对象
            return new PollingServiceConfig(this);
        }
    }

    public PollingServiceConfig(Builder b){
        this.context = b.context;
        this.userName = b.userName;
        this.action = b.action;

        this.passWord = b.passWord;
        this.seconds = b.seconds;
        this.cls = b.cls;
    }
    public Context getContext() {
        return context;
    }

    public int getSeconds() {
        return seconds;
    }

    public Class<?> getCls() {
        return cls;
    }

    public String getAction() {
        return action;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }




}
