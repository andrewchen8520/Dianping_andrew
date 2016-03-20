package com.jiedesui.welcomestart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jiedesui.welcomestart.com.jiedesui.consts.CONSTS;
import com.jiedesui.welcomestart.entity.City;
import com.jiedesui.welcomestart.entity.ResponseObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

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

    private Button button;

    //yes you can do it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);

        View view= LayoutInflater.from(this).inflate(R.layout.home_city_search,null);

    }




/*    @ViewInject(R.id.city_list)
    private ListView listDatas;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_city_list);
        ViewUtils.inject(this);

        View view = LayoutInflater.from(this).inflate(R.layout.home_city_search,
                null);
        listDatas.addHeaderView(view);
        //执行异步任务
        new CityDataTask().execute();
    }
    @OnClick({R.id.index_city_back,R.id.index_city_flushcity})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.index_city_back://返回
                finish();
                break;
            case R.id.index_city_flushcity://刷新
                new CityDataTask().execute();
                break;

            default:
                break;
        }
    }
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
    public class CityDataTask extends AsyncTask<Void, Void, List<City>> {
        @Override
        protected List<City> doInBackground(Void... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(CONSTS.City_Data_URI);
            try {
                HttpResponse httpResponse = client.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode()==200) {
                    String jsonString = EntityUtils.toString(httpResponse.getEntity());
                    return parseCityDatasJson(jsonString);
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<City> result) {
            super.onPostExecute(result);
            cityList = result;
            //适配显示
            MyAdapter adapter = new MyAdapter(cityList);
            listDatas.setAdapter(adapter);
        }
    }
    //解析城市数据的json
    public List<City> parseCityDatasJson(String json){
        Gson gson = new Gson();
        ResponseObject<List<City>> responseObject = gson.fromJson(json, new TypeToken<ResponseObject<List<City>>>(){}.getType());
        return responseObject.getDatas();
    }
    private StringBuffer buffer = new StringBuffer();//用来第一次保存首字母的索引
    private List<String> firdtList = new ArrayList<String>();//用来保存索引值对象的城市名称
    //适配器
    public class MyAdapter extends BaseAdapter {
        private List<City> listCityDatas;

        public MyAdapter(List<City> listCityDatas) {
            this.listCityDatas = listCityDatas;
        }

        @Override
        public int getCount() {
            return listCityDatas.size();//返回集合的长度
        }

        @Override
        public Object getItem(int position) {
            return listCityDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView==null) {
                holder = new Holder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_list_item, null);
                ViewUtils.inject(holder, convertView);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            //数据显示的处理
            City city = listCityDatas.get(position);
            String sort = city.getSortKey();
            String name = city.getName();
            //索引不存在
            if (buffer.indexOf(sort)==-1) {
                buffer.append(sort);
                firdtList.add(name);
            }
            if (firdtList.contains(name)) {
                holder.keySort.setText(sort);
                holder.keySort.setVisibility(View.VISIBLE);//包含对应的城市就让索引显示
            }else{
                holder.keySort.setVisibility(View.GONE);
            }
            holder.cityName.setText(name);
            return convertView;
        }

    }
    public class Holder{
        @ViewInject(R.id.city_list_item_sort)
        public TextView keySort;
        @ViewInject(R.id.city_list_item_name)
        public TextView cityName;
    }*/

}
