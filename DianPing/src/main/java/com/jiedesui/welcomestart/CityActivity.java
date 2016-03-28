package com.jiedesui.welcomestart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiedesui.welcomestart.com.jiedesui.consts.CONSTS;
import com.jiedesui.welcomestart.entity.City;
import com.jiedesui.welcomestart.entity.ResponseObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 点击选择城市界面后，显示的城市列表
 */
public class CityActivity extends AppCompatActivity {

    @ViewInject(R.id.city_list)
    private ListView listDatas;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);
        ViewUtils.inject(this);

        //为城市列表加入头部信息“正在定位”
        View view = LayoutInflater.from(this).inflate(R.layout.home_city_search,
                null);
        listDatas.addHeaderView(view);
        //执行异步任务进行网络访问

    }


    //点击按钮实现功能“返回”“测试”
    @OnClick({R.id.index_city_back,R.id.index_city_flushcity,R.id.btn_test,R.id.btn_test})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.index_city_back://返回
                finish();
                break;
            case R.id.index_city_flushcity://刷新
                Toast.makeText(this,"fresh",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_test:
                Toast.makeText(this,"TEST",Toast.LENGTH_SHORT).show();
                new LoadCityListTask().execute();
                break;

            default:
                break;
        }
    }

    //点击ListView的城市列表后
    @OnItemClick({R.id.city_list})
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {

        Intent intent = new Intent();
        TextView textView = (TextView) view.findViewById(R.id.city_list_item_name);
        intent.putExtra("cityName", textView.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


    //使用异步任务获取城市的json串
  /* public class CityDataTask extends AsyncTask<Void,Void,List<City>>{

        @Override
        protected List<City> doInBackground(Void... params) {
            //建立网络连接，获取json串
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(CONSTS.City_Data_URI);

            try {
                HttpResponse httpResponse=client.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode()==200){
                    String jsonString=EntityUtils.toString(httpResponse.getEntity());
                    Log.d("TAG",jsonString);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }*/

    private class LoadCityListTask extends AsyncTask<Void,Void,List<City>>{

        @Override
        protected List<City> doInBackground(Void... params) {
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(CONSTS.City_Data_URI);

            try {
                HttpResponse httpResponse=client.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode()==200){
                    String jsonString=EntityUtils.toString(httpResponse.getEntity());
                    Log.d("TAG",jsonString);
                    return parseCityDatasJson(jsonString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }


    //解析城市数据的json
    private List<City> parseCityDatasJson(String jsonString) {

        return null;
    }

    //适配器

}
