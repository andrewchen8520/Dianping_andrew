package com.jiedesui.welcomestart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by sean on 2016/3/13.
 * 实现用户登录标记的写入和读取，判断是否是首次登陆
 */
public class SharedUtils {
    private static final String FILE_NAME="dianping";
    private static final String MODE_NAME="welcome";

    //程序启动时进行读取登录标记操作
    public static boolean getWelcomeBoolean(Context context){
        //MODE_PRIVATE:SharedPreference数据只能被本程序操作
        //获取SharedPreferences文件中的boolean值
        //默认值为false，即首次启动时，执行本方法，返回的是false
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getBoolean(MODE_NAME,false);
    }

    /**
     * 进行写入操作，首次启动后，传入mode_name的值为true
     * @param context
     * @param isFirst 是否是首次登陆的信息
     */
    public static void putWelcomeBoolean(Context context, boolean isFirst){
        SharedPreferences.Editor editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(MODE_NAME,isFirst);
        editor.commit();
    }

    //写入一个String类型的城市名称数据
    public static void putCityName(Context context,String cityName){
        SharedPreferences.Editor editor=context.getSharedPreferences(FILE_NAME,Context.MODE_APPEND).edit();
        editor.putString("cityName",cityName);
        editor.commit();
    }
    //获取String类型的城市名称数据
    public static String getCityName(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getString("cityName","选择城市");
    }
}
