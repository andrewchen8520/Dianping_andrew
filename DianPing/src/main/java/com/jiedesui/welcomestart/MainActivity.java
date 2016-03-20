package com.jiedesui.welcomestart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiedesui.welcomestart.utils.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 仿大众点评的欢迎页面
 * 在欢迎页面停留3秒后实现延时跳转
 * 可以使用handler实现
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        *//**
         * 延时3秒启动下一个activity，使用Handler类，使用Callback内部接口为了调用其handleMessage方法而不用对Handler进行实例化，
         * Handler.sendEmptyMessage发送信息，通过handleMessage来接收信息
         *//*
        new Handler(new Handler.Callback(){
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message msg) {
                //实现页面跳转
                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);     //延时3秒发送一条空消息*/

        //定义计时器
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (SharedUtils.getWelcomeBoolean(getBaseContext())){
                    //不是首次启动，不显示guide页面
                    startActivity(new Intent(getBaseContext(),Main2Activity.class));
                }else {
                    //首次启动，跳转到guide页面
                    startActivity(new Intent(getBaseContext(),GuideActivity.class));
                    //首次启动后，设置启动标记
                    SharedUtils.putWelcomeBoolean(getBaseContext(),true);
                }
                //跳转之后关闭页面
            }
        },2000 );
    };

}
