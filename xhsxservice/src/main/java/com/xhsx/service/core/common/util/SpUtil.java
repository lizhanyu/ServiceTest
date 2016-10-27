package com.xhsx.service.core.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：lizy on 2016/10/14 17:53
 * 邮箱：lizhanyu1234@163.com
 * 描述：
 */
public class SpUtil {
    private static SharedPreferences sp;
    private static String xml_name="serviceConfig";
    /**
     * 写入String变量至sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public static void putString(Context ctx,String key,String value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    /**
     * 读取String标示从sp中
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param defValue  没有此节点默认值
     * @return      默认值或者此节点读取到的结果
     */
    public static String getString(Context ctx,String key,String defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences(xml_name, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
}
