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
import com.hdu.newe.here.page.main.variousdata.bean.ExpandDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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

    private VariousDataContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_varioud_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTab();
        initToolbar();
//        creatHistoryData();
        return view;
    }

    /**
     * 提供VariousDataFragment的实例
     *
     * @return VariousDataFragment实例
     */
    public static VariousDataFragment newInstance() {
        VariousDataFragment fragment = new VariousDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {

        toolbar.setTitle("数据");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    /**
     * 初始化Tab
     */
    public void initTab() {

        attendanceRateFragment = new AttendanceRateFragment();
        historyDataFragment = new HistoryDataFragment();
        buffDataFragment = new BuffDataFragment();

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
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

        if (attendanceRateFragment == null) {
            attendanceRateFragment = AttendanceRateFragment.newInstance();
        }
        if (historyDataFragment == null) {
            historyDataFragment = HistoryDataFragment.newInstance();
        }
        if (buffDataFragment == null) {
            buffDataFragment = BuffDataFragment.newInstance();
        }
        attendanceRateFragment.loadAttendanceRate(variousDataBean.getSubjectName(), variousDataBean.getAttendanceRate());
        historyDataFragment.loadHistoryData(getExpandData(variousDataBean));
    }

    @Override
    public void loadHistoryData(String objectId) {
        presenter.getVariousData("aefc052bd2");
    }

    /**
     * 将获取到的用户的各种数据拓展为ExpandDataBean
     *
     * @param variousDataBean 用户的VariousDataBean
     * @return 返回被拓展后用于在收缩列表中显示的用户数据ExpandDataBean
     */
    public List<ExpandDataBean> getExpandData(VariousDataBean variousDataBean) {

        List<ExpandDataBean> expandDataBeans = new ArrayList<>();

        ExpandDataBean expandDataLeaveRequest = new ExpandDataBean();
        ExpandDataBean expandDataWarning = new ExpandDataBean();
        ExpandDataBean expandDataChange = new ExpandDataBean();

        expandDataLeaveRequest.setExpand(false);
        expandDataWarning.setExpand(false);
        expandDataChange.setExpand(false);

        expandDataLeaveRequest.setParentTitle("请假历史");
        expandDataWarning.setParentTitle("警示历史");
        expandDataChange.setParentTitle("手机更换历史");

        expandDataLeaveRequest.setShowType(0);
        expandDataWarning.setShowType(0);
        expandDataChange.setShowType(0);

        expandDataLeaveRequest.setID("0");
        expandDataWarning.setID("1");
        expandDataChange.setID("2");

        expandDataLeaveRequest.setChildNum(variousDataBean.getLeaveRequestReason().size());
        expandDataWarning.setChildNum(variousDataBean.getWarningContent().size());
        expandDataChange.setChildNum(variousDataBean.getChangeHistoryContent().size());

        expandDataLeaveRequest.setChildBean(variousDataBean);
        expandDataWarning.setChildBean(variousDataBean);
        expandDataChange.setChildBean(variousDataBean);

        expandDataBeans.add(expandDataLeaveRequest);
        expandDataBeans.add(expandDataWarning);
        expandDataBeans.add(expandDataChange);


        return expandDataBeans;
    }

    /**
     * 创建测试数据
     */
    private void creatHistoryData() {

        //出勤率的科目列表
        List<String> subjectList = new ArrayList<>();
        subjectList.add("高等数学");
        subjectList.add("线性代数");
        subjectList.add("大学物理");

        //出勤率列表
        List<Number> rateList = new ArrayList<>();
        rateList.add(80.26);
        rateList.add(95.41);
        rateList.add(100.00);

        //请假历史 内容
        List<String> requestContent = new ArrayList<>();
        requestContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容内容内容内容内容内容1");
        requestContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容内容内容内容内容内容2");
        requestContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容内容内容内容内容内容3");
        requestContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容内容内容内容内容内容4");

        //请假历史 类型
        List<String> requestType = new ArrayList<>();
        requestType.add("请病假");
        requestType.add("请病假");
        requestType.add("请事假");
        requestType.add("请事假");

        //请假历史 状态
        List<String> requestState = new ArrayList<>();
        requestState.add("审核中");
        requestState.add("已同意");
        requestState.add("审核中");
        requestState.add("已驳回");

        //请假历史 时间
        List<String> requestTime = new ArrayList<>();
        requestTime.add("2018年03月27日");
        requestTime.add("2018年03月28日");
        requestTime.add("2018年03月29日");
        requestTime.add("2018年03月30日");

        //手机更换历史 标题
        List<String> changeTitle = new ArrayList<>();
        changeTitle.add("标题标题标题标题标题标题1");
        changeTitle.add("标题标题标题标题标题标题2");

        //手机更换历史内容
        List<String> changeContent = new ArrayList<String>();
        changeContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容1");
        changeContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容2");

        //警示历史 标题
        List<String> warningTitle = new ArrayList<>();
        warningTitle.add("标题标题标题标题标题标题1");
        warningTitle.add("标题标题标题标题标题标题2");
        warningTitle.add("标题标题标题标题标题标题3");

        //警示历史 内容
        List<String> warningContent = new ArrayList<>();
        warningContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容1");
        warningContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容2");
        warningContent.add("内容内容内容内容内容内容内容内容内容内容内容内容内容\n内容内容内容内容内容内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容3");

        VariousDataBean variousDataBean = new VariousDataBean();
        variousDataBean.setSubjectName(subjectList);
        variousDataBean.setAttendanceRate(rateList);
        variousDataBean.setLeaveRequestReason(requestContent);
        variousDataBean.setLeaveRequestState(requestState);
        variousDataBean.setLeaveRequestType(requestType);
        variousDataBean.setLeaveRequestTime(requestTime);
        variousDataBean.setWarningTitle(warningTitle);
        variousDataBean.setWarningContent(warningContent);
        variousDataBean.setChangeHistoryTitle(changeTitle);
        variousDataBean.setChangeHistoryContent(changeContent);
        variousDataBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    showMessage("数据创建成功！");
                } else {
                    showMessage("创建失败 错误原因：" + e.getMessage());
                }
            }
        });
    }
}
