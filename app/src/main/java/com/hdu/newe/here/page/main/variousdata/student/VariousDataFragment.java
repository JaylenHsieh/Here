package com.hdu.newe.here.page.main.variousdata.student;


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
import com.hdu.newe.here.biz.variousdata.student.bean.LeaveRequestBean;
import com.hdu.newe.here.biz.variousdata.student.bean.VariousDataBean;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.variousdata.student.adapter.MyPagerAdapter;
import com.hdu.newe.here.page.main.variousdata.student.bean.ExpandDataBean;

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

    private int loadingFlag = 0;
    private int emptyFlag = 0;
    private VariousDataBean variousDataBean = null;
    private LeaveRequestBean leaveRequestBean = null;

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
        viewpagerVariousData.setOffscreenPageLimit(2);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadVariousData(VariousDataBean variousDataBean) {

        if (attendanceRateFragment == null) {
            attendanceRateFragment = AttendanceRateFragment.newInstance();
        }
        if (buffDataFragment == null) {
            buffDataFragment = BuffDataFragment.newInstance();
        }
        attendanceRateFragment.loadAttendanceRate(variousDataBean.getSubjectName(), variousDataBean.getAttendanceRate());
        buffDataFragment.loadBuffData(variousDataBean.getBuffType(), variousDataBean.getAllAttendanceRate());
        this.variousDataBean = variousDataBean;
        loadingFlag++;
        if (loadingFlag == 2) {
            if (historyDataFragment == null) {
                historyDataFragment = HistoryDataFragment.newInstance();
            }
            historyDataFragment.loadHistoryData(getExpandData(this.variousDataBean, this.leaveRequestBean));
            this.loadingFlag = 0;
            this.variousDataBean = null;
            this.leaveRequestBean = null;
        }
    }

    @Override
    public void loadHistoryData(String objectId) {
        presenter.getVariousData();
    }

    @Override
    public void loadLeaverequestData(LeaveRequestBean leaveRequestBean) {

        this.leaveRequestBean = leaveRequestBean;
        loadingFlag++;
        if (loadingFlag == 2) {
            if (historyDataFragment == null) {
                historyDataFragment = HistoryDataFragment.newInstance();
            }
            historyDataFragment.loadHistoryData(getExpandData(this.variousDataBean, this.leaveRequestBean));
            this.loadingFlag = 0;
            this.variousDataBean = null;
            this.leaveRequestBean = null;
        }
    }

    /**
     * 将获取到的用户的各种数据拓展为ExpandDataBean
     *
     * @param variousDataBean 用户的VariousDataBean
     * @return 返回被拓展后用于在收缩列表中显示的用户数据ExpandDataBean
     */
    public List<ExpandDataBean> getExpandData(VariousDataBean variousDataBean, LeaveRequestBean leaveRequestBean) {

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

        List<String> list;
        list = leaveRequestBean.getLeaveRequestReason();
        if (list == null || list.isEmpty()) {
            expandDataLeaveRequest.setChildNum(0);
        } else {
            expandDataLeaveRequest.setChildNum(leaveRequestBean.getLeaveRequestReason().size() - 2);
        }

        list = variousDataBean.getWarningContent();
        if (list == null || list.isEmpty()) {
            expandDataWarning.setChildNum(0);
        } else {
            expandDataWarning.setChildNum(variousDataBean.getWarningContent().size() - 2);
        }

        list = variousDataBean.getNewPhone();
        if (list == null || list.isEmpty()) {
            expandDataChange.setChildNum(0);
        } else {
            expandDataChange.setChildNum(variousDataBean.getNewPhone().size() - 2);
        }

        if (leaveRequestBean.getLeaveRequestReason() != null && !leaveRequestBean.getLeaveRequestReason().isEmpty()) {

            list = leaveRequestBean.getLeaveRequestReason();
            list.remove(0);
            list.remove(0);
            expandDataLeaveRequest.setLeaveRequestReason(list);

            list = leaveRequestBean.getLeaveRequestState();
            list.remove(0);
            list.remove(0);
            expandDataLeaveRequest.setLeaveRequestState(list);

            list = leaveRequestBean.getLeaveRequestTime();
            list.remove(0);
            list.remove(0);
            expandDataLeaveRequest.setLeaveRequestTime(list);
//            expandDataLeaveRequest.setLeaveRequestType(leaveRequestBean.getLeaveRequestType());
        }

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
        subjectList.add("通信原理");
        subjectList.add("数字信号处理");
        subjectList.add("电路分析");

        //出勤率列表
        List<Number> rateList = new ArrayList<>();
        rateList.add(80.26);
        rateList.add(95.41);
        rateList.add(100.00);
        rateList.add(70.56);
        rateList.add(69.95);
        rateList.add(85.42);

        //手机更换历史
        List<String> changeOldIMEI = new ArrayList<>();
        List<String> changeNewIMEI = new ArrayList<>();
        List<String> changeOldPhone = new ArrayList<>();
        List<String> changeNewPhone = new ArrayList<>();
        List<String> changeTime = new ArrayList<>();
        changeNewIMEI.add("12345678");
        changeNewIMEI.add("98765432");
        changeOldIMEI.add("87654321");
        changeOldIMEI.add("23456789");
        changeOldPhone.add("HONOR8 Lite");
        changeOldPhone.add("HUAWEI NOVA2");
        changeNewPhone.add("HUAWEI NOVA2");
        changeNewPhone.add("VIVO Find X");
        changeTime.add("2017年10月25日");
        changeTime.add("2018年4月8日");

        //警示历史 标题
        List<String> warningTitle = new ArrayList<>();
        warningTitle.add("警示：您最近有一节课未出勤");
        warningTitle.add("警示：您最近有一节课未出勤");

        //警示历史 内容
        List<String> warningContent = new ArrayList<>();
        warningContent.add("谢君亮同学您好！您最近一次的高等数学课点名未出勤。本次为系统第二次自动警示，累计三次后将经由辅导员发出警示。");
        warningContent.add("谢君亮同学您好！您最近一次的线性代数课点名未出勤。本次为系统第一次自动警示，累计三次后将经由辅导员发出警示。");

        VariousDataBean variousDataBean = new VariousDataBean();
        variousDataBean.setSubjectName(subjectList);
        variousDataBean.setAttendanceRate(rateList);
        variousDataBean.setWarningTitle(warningTitle);
        variousDataBean.setBuffType(VariousDataBean.BUFF_TYPE_IRRESOLUTION);
        variousDataBean.setAllAttendanceRate(90.45);
        variousDataBean.setWarningContent(warningContent);
        variousDataBean.setOldIMEI(changeOldIMEI);
        variousDataBean.setOldPhone(changeOldPhone);
        variousDataBean.setNewPhone(changeNewPhone);
        variousDataBean.setOldIMEI(changeOldIMEI);
        variousDataBean.setNewIMEI(changeNewIMEI);
        variousDataBean.setChangeTime(changeTime);
        variousDataBean.setUserObjectId("da2d833c2d");
        variousDataBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    //请假历史 内容
                    List<String> requestReason = new ArrayList<>();
                    requestReason.add("由于身体不适，想要去检查身体，特此请假，望批准。");
                    requestReason.add("家中突发急事，需要回家一趟，由于路途遥远，可能需要一个星期。希望得到老师批准！");
                    requestReason.add("发烧导致肺炎住院，需要请假直到病愈出院，希望老师能够批准");

                    //请假历史 类型
                    List<String> requestType = new ArrayList<>();
                    requestType.add("请病假");
                    requestType.add("请事假");
                    requestType.add("请病假");

                    //请假历史 状态
                    List<String> requestState = new ArrayList<>();
                    requestState.add("已驳回");
                    requestState.add("审核中");
                    requestState.add("已同意");

                    //请假历史 时间
                    List<String> requestTime = new ArrayList<>();
                    requestTime.add("2018年03月27日");
                    requestTime.add("2018年03月28日");
                    requestTime.add("2018年03月29日");

                    LeaveRequestBean leaveRequestBean = new LeaveRequestBean();
                    leaveRequestBean.setLeaveRequestReason(requestReason);
                    leaveRequestBean.setLeaveRequestType(requestType);
                    leaveRequestBean.setLeaveRequestState(requestState);
                    leaveRequestBean.setLeaveRequestTime(requestTime);
                    leaveRequestBean.setUserObjectId("da2d833c2d");

                    leaveRequestBean.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e != null) {
                                showMessage("数据创建成功！");
                            } else {
                                showMessage("创建失败 错误原因：" + e.getMessage());
                            }
                        }
                    });
                } else {
                    showMessage("创建失败 错误原因：" + e.getMessage());
                }
            }
        });

    }
}