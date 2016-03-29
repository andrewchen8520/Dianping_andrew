package com.jiedesui.welcomestart;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

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
        new LoadCityListTask().execute();
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
                new LoadCityListTask().execute();
                break;

            case R.id.btn_test:
                Toast.makeText(this,"TEST",Toast.LENGTH_SHORT).show();
                new LoadCityListTask().execute();
                break;

            default:
                break;
        }
    }

    //点击ListView的城市列表后,返回选中城市名称
    @OnItemClick({R.id.city_list})
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {

        Intent intent = new Intent();
        TextView textView = (TextView) view.findViewById(R.id.city_list_item_name);
        intent.putExtra("cityName", textView.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


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

        //获取到城市类表信息后更新UI
        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            cityList=cities;
            //进行显示
            Log.d("TAG",cities.toString());
            MyAdapter myAdapter=new MyAdapter(getBaseContext(),R.layout.home_city_list_item,cities);
            listDatas.setAdapter(myAdapter);
        }
    }



    //解析城市数据的json
    private List<City> parseCityDatasJson(String jsonString) {
        Gson gson =new Gson();
        /**
         *参数1：String类型的json数据
         *参数2：需要转换成的目标类
         */
        ResponseObject<List<City>> responseObject=gson.fromJson(jsonString, new TypeToken<ResponseObject<List<City>>>() {
        }.getType());
        Log.d("TAG",new TypeToken<ResponseObject<List<City>>>() {
        }.getType()+"");
        return responseObject.getDatas();
    }

    //适配器,listView显示列表用
    private StringBuffer buffer=new StringBuffer();//用来保存依次出现的首字母
    private List<String> firstList=new ArrayList<>();//用来保存索引字母下面对应城市名称的列表
    public class MyAdapter extends ArrayAdapter{
        private List<City> listCityDatas;
        public MyAdapter(Context context, int resource, List<City> citylist) {
            super(context, resource, citylist);
            listCityDatas=citylist;
        }

        //重写View方法
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            Holder holder;
           if (convertView==null){
               view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_list_item,null);

               //使用Holder缓存两个TextView对象，是的findViewById方法不用频繁调用
               holder=new Holder();
               holder.keysort=(TextView)view.findViewById(R.id.city_list_item_sort);
               holder.cityName=(TextView)view.findViewById(R.id.city_list_item_name);

               //使用view的setTag方法保存holder信息
               view.setTag(holder);

           }else {
              view= convertView;
               holder=(Holder)view.getTag();
           }

            //进行数据处理
            City city=listCityDatas.get(position);
            String sortCode=city.getSortKey();
            String cityName=city.getName();

            //进行
            if (buffer.indexOf(sortCode)==-1){
                buffer.append(sortCode);
                firstList.add(cityName);

            }

            if (firstList.contains(cityName)){
                holder.keysort.setText(sortCode);
                holder.keysort.setVisibility(View.VISIBLE);
            } else {
                holder.keysort.setVisibility(View.GONE);
            }

            holder.keysort.setText(sortCode);
            holder.cityName.setText(cityName);

            return view;
        }
    }

    //View实例的缓存区
    class Holder{
        TextView keysort;
        TextView cityName;
    }

}
