package com.jiedesui.welcomestart.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiedesui.welcomestart.CityActivity;
import com.jiedesui.welcomestart.R;
import com.jiedesui.welcomestart.utils.MyUtils;
import com.jiedesui.welcomestart.utils.SharedUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * Created by sean on 2016/3/13.
 */
public class FragmentHome extends android.support.v4.app.Fragment implements LocationListener {

    @ViewInject(R.id.index_top_city)
    private TextView topCity;
    private String cityName;//存放当前城市名称
    private LocationManager locationManager;//定位服务需要的位置管理器,该管理器允许用户获取系统的位置服务

    //因跟新地理位置是一个耗时操作，需要定义handler进行接收和处理消息，
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //如果msg.what==1，说明获取到城市位置信息，在这里可以给topCity进行赋值
            if (msg.what==1){
                topCity.setText(cityName);
            }
            return false;
        }
    });

    @Override
    public void onStart() {
        super.onStart();
        //检查当前GPS模块
        checkGPSIsOpen();
        Log.d("TAG", "onStart");
    }

    //自定义方法，检查gps模块
    private void checkGPSIsOpen() {
        //获取当前的LocationManager对象
        locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        //检查当前GPS模块是否打开
        boolean isOpen=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("TAG", "isOpen:" + isOpen);
        if (!isOpen){
            //GPS没打开，进入GPS设置页面
            Intent intent=new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//设定intent的action属性
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //标记为一个新的任务栈,可以尝试改变不同值时系统的反应
            startActivityForResult(intent,0);
        }
        //打开gps后开始定位,这里封装了一个方法
        startLocation();
    }

    //自定义方法使gps开始定位
    private void startLocation() {
        /**
         * 在api23中需要进行授权
         * 参数2:位置改变最短时间
         * 参数3:位置更改的最短距离(米)
         * 参数4:以为这个类实现了LocationListener接口,因此在这里使用this即可
         */
        try {
            Log.d("TAG","startLocation");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,60,100,this);}
        catch (SecurityException e) {
            e.printStackTrace();}

        Log.d("TAG", "startLocation excuted");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //注意第三个参数要设置成false，不然会导致运行异常
        View view=inflater.inflate(R.layout.home_index, container, false);
        //
        ViewUtils.inject(this, view);

        //获取数据并且显示
        topCity.setText(SharedUtils.getCityName(getActivity()));//传入getActivity和getContext均可?
        Log.d("TAG", "onCreateView");
        return view;
    }

    //点击“选择城市”时执行方法
    @OnClick(R.id.index_top_city)
    public void onClick(View v){
        //跳转到城市选择界面
        startActivityForResult(new Intent(getActivity(),CityActivity.class), MyUtils.RequestCityCode);
    }

    //处理选择城市后的返回信息,显示城市
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MyUtils.RequestCityCode && resultCode== Activity.RESULT_OK){
            cityName=data.getStringExtra("cityName");
            Log.d("TAG",cityName);
            topCity.setText(cityName);
        }
    }

    //位置变更执行的方法
    @Override
    public void onLocationChanged(Location location) {
        //更新当前的位置信息,自定义方法
        updateWithNewLocation(location);
    }

    //自定义方法，获取对应位置的经度纬度并且定位城市
    private void updateWithNewLocation(Location  location) {
        Log.d("TAG","updateWithNewLocation");
        Log.d("TAG","location!=null:"+(location!=null));
        //定义经度纬度
        double lat=0,lng=0;
        if (location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            Log.d("TAG","经度是"+lat+" 纬度是"+lng);
            Log.d("TAG","Location is not null");
        }else {
            Log.d("TAG","Location is null");
            cityName="无法获取城市信息";
        }
        //通过经纬度获取地址，由于地址会有多个，这个和经纬度精确度有关，本实例中定义了最大的返回数为2，即在集合对象中有两个值
        //Address是一个Parceble接口的实现类
        List<Address>list =null;
        //Geocoder是一个将经纬度和adress互相编码的处理类
        Geocoder ge= new Geocoder(getActivity());
        try{
            list=ge.getFromLocation(lat,lng,2);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                Address ad=list.get(i);
                cityName=ad.getLocality();//获取城市
            }
        }
        //每次获取到城市位置后，就通过handler发送一条消息
        handler.sendEmptyMessage(1);
    }

    //定位状态发生变化时执行的方法
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    //定位服务可用时执行的方法
    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    //在onDestroy中解除定位
    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存当前城市,使用SharedPreference进行保存,并且每次页面进行时,显示当前城市信息
        SharedUtils.putCityName(getActivity(),cityName);
        //停止定位
        stopLocation();
    }
    //自定义方法停止定位
    private void stopLocation() {
        try{
            locationManager.removeUpdates(this);
        }catch (SecurityException e) {
            e.printStackTrace();}

    }
}
