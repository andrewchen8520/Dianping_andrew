package com.jiedesui.welcomestart;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    @ViewInject(R.id.guide_btn)
    private Button btn;
    @ViewInject(R.id.guide)
    private ViewPager pager;

    //新建List存储guideactivity的View对象
    private List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewUtils.inject(this);
        initViewPager();

        //设置前两页View的按钮隐藏
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            //页卡被选中时 调用方法
            public void onPageSelected(int position) {
                if (position==2){
                    btn.setVisibility(View.VISIBLE);
                }else {
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick(R.id.guide_btn)
    public void click(View view){
        startActivity(new Intent(getBaseContext(),Main2Activity.class));
    }
    //初始化ViewPager的数据源，并设置adapter
    private void initViewPager() {
        list=new ArrayList<View>();
        ImageView iv1=new ImageView(this);
        iv1.setImageResource(R.drawable.guide_01);
        list.add(iv1);
        ImageView iv2=new ImageView(this);
        iv2.setImageResource(R.drawable.guide_02);
        list.add(iv2);
        ImageView iv3=new ImageView(this);
        iv3.setImageResource(R.drawable.guide_03);
        list.add(iv3);

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            //判断页面的View是否与instantiateItem(ViewGroup, int)返回的Object相关联
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            //初始化item实例
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            //item销毁
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }
        });

    }
}
