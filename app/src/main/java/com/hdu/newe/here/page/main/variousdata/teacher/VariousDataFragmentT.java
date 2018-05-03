package com.hdu.newe.here.page.main.variousdata.teacher;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.variousdata.teacher.bean.UserBeanForList;
import com.hdu.newe.here.page.base.BaseFragment;
import com.hdu.newe.here.page.main.variousdata.student.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 教师端展示多种数据的碎片
 * A simple {@link Fragment} subclass.
 *
 * @author pope
 */
public class VariousDataFragmentT extends BaseFragment<VariousDataContractT.Presenter> implements VariousDataContractT.View {

    @BindView(R.id.toolbar_data_t)
    Toolbar toolbarDataT;
    @BindView(R.id.tablayout_various_data_t)
    TabLayout tablayoutVariousDataT;
    @BindView(R.id.viewpager_various_data_t)
    ViewPager viewpagerVariousDataT;
    Unbinder unbinder;
    @BindView(R.id.tablayout_various_data_class)
    TabLayout tablayoutVariousDataClass;

    private final int TABSTYLE_TEACHER = 1;
    private final int TABSTYLE_INSTRUCTOR = 2;

    private AttendanceRateFragmentT attendanceRateFragmentT;
    private AbsentDataFragmentT absentDataFragmentT;
    private BuffDataFragmentT buffDataFragmentT;
    private LeaveRequestFragmentT leaveRequestFragmentT;
    private WarningDataFragmentT warningDataFragmentT;
    private VariousDataOfSubjectFragmentT variousDataOfSubjectFragmentT;

    private AlertDialog.Builder dialog;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private List<String> classList;
    private List<Integer> checkTimes;
    private MyPagerAdapter myPagerAdapter;

    private VariousDataContractT.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_various_data_fragment_t, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }


    /**
     * 初始化班级选项卡
     *
     * @param classList  班级列表
     * @param classCount 所带自然班的数量
     */
    public void initClassTab(final List<String> classList, final int classCount, final List<Integer> checkTimes) {

        //如果班级列表是空的 那么显示dialog
        if (classList.isEmpty()) {
            showDialog();
            return;
        }

        this.checkTimes = checkTimes;
        this.classList = classList;
        for (int i = 0; i < classList.size(); i++) {
            tablayoutVariousDataClass.addTab(tablayoutVariousDataClass.newTab().setText(classList.get(i)));
        }
        tablayoutVariousDataClass.setTabMode(TabLayout.MODE_SCROLLABLE);

        //初始化TAB 当所带自然班数量大于0时，初始加载辅导员的DataTab 否则加载教师的DataTab
        if (classCount > 0) {
            initDataTab(classList.get(0), TABSTYLE_INSTRUCTOR, -1);
        } else {
            initDataTab(classList.get(0), TABSTYLE_TEACHER, checkTimes.get(0));
        }

        //TAB点击监听
        tablayoutVariousDataClass.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //被点击后判断该TAB是自然班还是教学班 不同情况对应加载不同的DataTab
                if (tablayoutVariousDataClass.getSelectedTabPosition() < classCount) {
                    initDataTab(classList.get(tablayoutVariousDataClass.getSelectedTabPosition())
                            , TABSTYLE_INSTRUCTOR, checkTimes.get(tablayoutVariousDataClass.getSelectedTabPosition()));
                } else {
                    initDataTab(classList.get(tablayoutVariousDataClass.getSelectedTabPosition())
                            , TABSTYLE_TEACHER, checkTimes.get(tablayoutVariousDataClass.getSelectedTabPosition()));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 初始化数据类型选项卡
     *
     * @param classObjectId 课程或班级Id
     * @param tabStyle      tab企图加载的类型
     * @param checkTimes    若是教师的tabStyle 该课的考勤次数
     */
    public void initDataTab(final String classObjectId, final int tabStyle, int checkTimes) {

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();

        switch (tabStyle) {
            case TABSTYLE_INSTRUCTOR:
                if (attendanceRateFragmentT == null) {
                    attendanceRateFragmentT = AttendanceRateFragmentT.newInstance();
                }
                if (absentDataFragmentT == null) {
                    absentDataFragmentT = AbsentDataFragmentT.newInstance();
                }
                if (buffDataFragmentT == null) {
                    buffDataFragmentT = BuffDataFragmentT.newInstance();
                }
                if (leaveRequestFragmentT == null) {
                    leaveRequestFragmentT = LeaveRequestFragmentT.newInstance();
                }
                if (warningDataFragmentT == null) {
                    warningDataFragmentT = WarningDataFragmentT.newInstance();
                }
                fragmentList.add(attendanceRateFragmentT);
                fragmentList.add(absentDataFragmentT);
                fragmentList.add(buffDataFragmentT);
                fragmentList.add(leaveRequestFragmentT);
                fragmentList.add(warningDataFragmentT);
                titleList.add("出勤数据");
                titleList.add("缺勤数据");
                titleList.add("标识数据");
                titleList.add("请假数据");
                titleList.add("警示数据");
                tablayoutVariousDataT.setTabMode(TabLayout.MODE_FIXED);
                break;
            case TABSTYLE_TEACHER:
                if (variousDataOfSubjectFragmentT == null) {
                    variousDataOfSubjectFragmentT = VariousDataOfSubjectFragmentT.newInstance();
                }
                for (int i = 0; i < checkTimes; i++) {
                    titleList.add("第" + i + "次考勤");
                    fragmentList.add(variousDataOfSubjectFragmentT);
                }
                tablayoutVariousDataT.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;
            default:
                showMessage("TabStyle传输错误");
                break;
        }

        for (int i = 0; i < titleList.size(); i++) {
            tablayoutVariousDataT.addTab(tablayoutVariousDataT.newTab().setText(titleList.get(i)));
        }
        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        viewpagerVariousDataT.setAdapter(myPagerAdapter);
        tablayoutVariousDataT.setupWithViewPager(viewpagerVariousDataT);

        //Tab设置监听
        tablayoutVariousDataT.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabStyle) {
                    case TABSTYLE_INSTRUCTOR:
                        getVariousData(classObjectId);
                        break;
                    case TABSTYLE_TEACHER:
                        mPresenter.getCheckData(classObjectId);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 加载Tab中详细数据方法
     *
     * @param classObjectId 被查询班级的objectId
     * @param tabStyle      tab布局风格 自然班/教学班
     */
    private void loadVariousDataT(String classObjectId, int tabStyle) {

    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {

        toolbarDataT.setTitle("数据");
        toolbarDataT.setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 载入班级数据
     *
     * @param classList  传入该老师所带班级和所教班级的列表
     * @param classCount 传入该老师所带班级数量
     * @param checkTimes 传入该老师所教班级点过名的数量
     */
    @Override
    public void loadClassData(List<String> classList, int classCount, List<Integer> checkTimes) {
        initClassTab(classList, classCount, checkTimes);
    }

    /**
     * 载入签到数据
     * @param signInDataBean 传入需要被加载的SignInDataBean
     */
    @Override
    public void loadCheckData(SignInDataBean signInDataBean) {
        if (variousDataOfSubjectFragmentT == null){
            variousDataOfSubjectFragmentT = VariousDataOfSubjectFragmentT.newInstance();
        }
        variousDataOfSubjectFragmentT.loadAttendanceData(signInDataBean);
    }

    /**
     * 加载列表上数据方法
     * @param userBeanForList 学生简易用户数据集合
     * @param pos 加载位置
     */
    @Override
    public void loadListData(List<UserBeanForList> userBeanForList, int pos) {
        if (variousDataOfSubjectFragmentT == null){
            variousDataOfSubjectFragmentT = VariousDataOfSubjectFragmentT.newInstance();
        }
        List<String> nameList = new ArrayList<>();
        List<String> numList = new ArrayList<>();
        for (int i = 0 ; i < userBeanForList.size();i++){
            nameList.add(userBeanForList.get(i).getStudentName());
            numList.add(userBeanForList.get(i).getStudentNum());
        }
        variousDataOfSubjectFragmentT.loadListData(nameList,numList,pos);
    }

    /**
     * 加载学生出勤率方法
     * @param studentNameList 学生姓名列表
     * @param studentAttendanceRateList 学生出勤率列表
     */
    @Override
    public void loadAttendanceData(List<String> studentNameList,List<Number> studentAttendanceRateList) {
        if (attendanceRateFragmentT == null){
            attendanceRateFragmentT = AttendanceRateFragmentT.newInstance();
        }
        attendanceRateFragmentT.loadAttendanceData(studentNameList,studentAttendanceRateList);
    }

    @Override
    public void loadAbsentData() {

    }

    @Override
    public void loadBuffData() {

    }

    @Override
    public void loadLeaveRequestData() {

    }

    @Override
    public void loadWarningData() {

    }

    @Override
    public void showDialog() {
        dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("非常抱歉");
        dialog.setMessage("未检测到您本学期进行辅导员和教学工作，无数据可查看。");
        dialog.setCancelable(false);
        dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO 后续相应操作
            }
        });
        dialog.setNegativeButton("查看帮助", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO 后续相应操作
            }
        });
        dialog.show();
    }

    /**
     * 获取自然班的多种数据
     *
     * @param classId 被查询自然班的班级Id
     */
    private void getVariousData(String classId) {
        mPresenter.getAttendanceData(classId);
        mPresenter.getAbsentData(classId);
        mPresenter.getBuffData(classId);
        mPresenter.getLeaveRequestData(classId);
        mPresenter.getWarningData(classId);
    }
}
