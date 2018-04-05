package com.hdu.newe.here.page.main.variousdata;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.page.main.variousdata.adapter.VariousDataPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * 数据模块 各种数据展示的主Fragment
 * @author pope
 */
public class VariousDataFragment extends Fragment {

    @BindView(R.id.pagertitle_variousdata)
    PagerTabStrip pagertitleVariousdata;
    @BindView(R.id.viewpager_variousdata)
    ViewPager viewpagerVariousdata;
    Unbinder unbinder;

    private ArrayList<View> viewList;
    private ArrayList<String> titleList;
    private VariousDataPagerAdapter variousDataPagerAdapter;

    public VariousDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_various_data, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewList = new ArrayList<>();
        titleList = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        viewList.add(layoutInflater.inflate(R.layout.fragment_attendace_data,null,false));
        viewList.add(layoutInflater.inflate(R.layout.fragment_history_data,null,false));
        viewList.add(layoutInflater.inflate(R.layout.fragment_buff_data,null,false));
        titleList.add("出勤数据");
        titleList.add("历史数据");
        titleList.add("标识数据");
        variousDataPagerAdapter = new VariousDataPagerAdapter(viewList,titleList);
        viewpagerVariousdata.setAdapter(variousDataPagerAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}