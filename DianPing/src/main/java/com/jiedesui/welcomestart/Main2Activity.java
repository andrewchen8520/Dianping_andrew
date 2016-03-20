package com.jiedesui.welcomestart;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiedesui.welcomestart.main.fragment.FragmentHome;
import com.jiedesui.welcomestart.main.fragment.FragmentMy;
import com.jiedesui.welcomestart.main.fragment.FragmentSearch;
import com.jiedesui.welcomestart.main.fragment.FragmentTuan;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Main2Activity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.main_bottom_tabs)
    private RadioGroup group;
    @ViewInject(R.id.main_home_btn)
    private RadioButton main_home_btn;

    private android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ViewUtils.inject(this);

        //得到FragmentManager
        fragmentManager=getSupportFragmentManager();
        //设置默认选中
        main_home_btn.setChecked(true);
        //设置监听事件，因为次activity继承实现了onCheckedChangeListenner，因此下面的参数传入this就可以了
        group.setOnCheckedChangeListener(this);

        //设置默认为home页，没点选radiobutton时呈现该页
        changeFragment(new FragmentHome(),false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_home_btn:
                changeFragment(new FragmentHome(),true);
                break;
            case R.id.main_my_btn:
                changeFragment(new FragmentMy(),true);
                break;
            case R.id.main_search_btn:
                changeFragment(new FragmentSearch(),true);
                break;
            case R.id.main_tuan_btn:
                changeFragment(new FragmentTuan(),true);
                break;
            default:
                break;
        }
    }

    /**
     * 切换不同的fragment的操作,选中不同的radiobutton，切换不同的fragment
     * 使用fragmentManager进行操作
     * @param fragment
     * @param isInit ??该标记控制fragment是否时第一次建立，如果不是第一次，采取方法对fragment进行复用
     */
    public void changeFragment(Fragment fragment,boolean isInit){
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content,fragment);
        /*if (!isInit){
            fragmentTransaction.addToBackStack(null);
        }*/
        fragmentTransaction.commit();
    }
}
