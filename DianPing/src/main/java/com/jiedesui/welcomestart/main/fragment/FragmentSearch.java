package com.jiedesui.welcomestart.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiedesui.welcomestart.R;

/**
 * Created by sean on 2016/3/13.
 */
public class FragmentSearch extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_index,container,false);
        return view;
    }
}