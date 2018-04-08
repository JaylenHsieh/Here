package com.hdu.newe.here.page.main.variousdata;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.biz.variousdata.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.variousdata.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class VariousDataFragment extends BaseFragment<VariousDataContract.Presenter> implements VariousDataContract.View {


    Unbinder unbinder;
    @BindView(R.id.toolbar_data)
    Toolbar toolbar;
    @BindView(R.id.tablayout_various_data)
    TabLayout tablayoutVariousData;
    @BindView(R.id.viewpager_various_data)
    ViewPager viewpagerVariousData;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MyPagerAdapter myPagerAdapter;

    private AttendanceRateFragment attendanceRateFragment;
    private HistoryDataFragment historyDataFragment;
    private BuffDataFragment buffDataFragment;

    public VariousDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_varioud_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTab();
        initToolbar();

        return view;
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {

        toolbar.setTitle("数据");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    public void initTab() {

        attendanceRateFragment = new AttendanceRateFragment();
        historyDataFragment = new HistoryDataFragment();
        buffDataFragment = new BuffDataFragment();

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        fragmentList.add(historyDataFragment);
        fragmentList.add(attendanceRateFragment);
        fragmentList.add(buffDataFragment);
        titleList.add("历史数据");
        titleList.add("出勤数据");
        titleList.add("标识数据");
        tablayoutVariousData.setTabMode(TabLayout.MODE_FIXED);
        tablayoutVariousData.addTab(tablayoutVariousData.newTab().setText(titleList.get(0)));
        tablayoutVariousData.addTab(tablayoutVariousData.newTab().setText(titleList.get(1)));
        tablayoutVariousData.addTab(tablayoutVariousData.newTab().setText(titleList.get(2)));
        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        viewpagerVariousData.setAdapter(myPagerAdapter);
        tablayoutVariousData.setupWithViewPager(viewpagerVariousData);
        viewpagerVariousData.setCurrentItem(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 加载各种数据的V层逻辑
     *
     * @param variousDataBean 数据bean
     */
    @Override
    public void loadVariousData(VariousDataBean variousDataBean) {

    }
}
