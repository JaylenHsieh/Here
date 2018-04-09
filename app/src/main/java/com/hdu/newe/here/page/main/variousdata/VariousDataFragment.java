package com.hdu.newe.here.page.main.variousdata;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdu.newe.here.R;
import com.hdu.newe.here.adapter.MyPagerAdapter;
import com.hdu.newe.here.bean.AttendanceDataBean;
import com.hdu.newe.here.bean.BuffDataBean;
import com.hdu.newe.here.bean.HistoryDataBean;
import com.hdu.newe.here.page.base.BaseFragment;

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


    @BindView(R.id.viewpager_variousdata)
    ViewPager viewpagerVariousdata;
    Unbinder unbinder;
    @BindView(R.id.toolbar_data)
    Toolbar toolbar;

    private ViewPager viewPager;
    private ArrayList<View> viewList;
    private ArrayList<String> titleList;
    private MyPagerAdapter myPagerAdapter;

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

    @Override
    public void loadAttendanceData(List<AttendanceDataBean> attendanceDataBeanList) {

    }

    @Override
    public void loadHistoryData(List<HistoryDataBean> historyDataBeanList) {

    }

    @Override
    public void loadBuffHistoryData(List<BuffDataBean> buffDataBeanList) {

    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {

        toolbar.setTitle("数据");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    public void initTab() {

        viewList = new ArrayList<>();
        titleList = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        viewList.add(layoutInflater.inflate(R.layout.fragment_history_data, null, false));
        viewList.add(layoutInflater.inflate(R.layout.fragment_attendance_rate, null, false));
        viewList.add(layoutInflater.inflate(R.layout.fragment_buff_data, null, false));
        titleList.add("历史数据");
        titleList.add("出勤数据");
        titleList.add("标识数据");
        myPagerAdapter = new MyPagerAdapter(viewList, titleList);
        viewpagerVariousdata.setAdapter(myPagerAdapter);
        viewpagerVariousdata.setCurrentItem(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
