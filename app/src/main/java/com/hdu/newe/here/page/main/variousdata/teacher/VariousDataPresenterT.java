package com.hdu.newe.here.page.main.variousdata.teacher;

import com.hdu.newe.here.biz.ModelFactory;
import com.hdu.newe.here.biz.signin.bean.SignInDataBean;
import com.hdu.newe.here.biz.variousdata.teacher.VariousDataInterfaceT;
import com.hdu.newe.here.biz.variousdata.teacher.bean.UserBeanForList;
import com.hdu.newe.here.page.base.BasePresenterImpl;

import java.util.List;

/**
 * @author pope
 * @date 2018/5/3
 */

public class VariousDataPresenterT extends BasePresenterImpl implements VariousDataContractT.Presenter {

    private VariousDataContractT.View view;
    private VariousDataInterfaceT variousDataInterfaceT = ModelFactory.getVariousDataInterfaceT();

    public VariousDataPresenterT(VariousDataContractT.View view) {
        this.view = view;
        view.bindPresenter(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getClassData();
    }

    @Override
    public void getClassData() {

        variousDataInterfaceT.getClassData(new VariousDataInterfaceT.OnClassDataCallback() {

            @Override
            public void onGetSuccess(List<String> classList, int classCount, List<Integer> checkTimes) {
                //教师相关班级信息
                //classList存放了自然班与教学班的班级信息 自然班在前
                //classCount存放了自然班的班级数量
                //checkTimes存放了每个教学班的点名次数
                view.loadClassData(classList, classCount,checkTimes);
            }

            @Override
            public void onStartGetData() {

            }

            @Override
            public void onGetSuccess() {

            }

            @Override
            public void onGetFailed(String errorMsg) {

            }
        });

    }

    @Override
    public void getAttendanceData(String classId) {

        variousDataInterfaceT.getAttendanceRateData(classId, new VariousDataInterfaceT.OnAttendanceDataCallbackT() {
            @Override
            public void onGetSuccess(List<String> studentList, List<Number> attendanceRateList) {

            }

            @Override
            public void onStartGetData() {

            }

            @Override
            public void onGetSuccess() {

            }

            @Override
            public void onGetFailed(String errorMsg) {

            }
        });

    }

    @Override
    public void getAbsentData(String objectId) {

    }

    @Override
    public void getBuffData(String objectId) {

    }

    @Override
    public void getLeaveRequestData(String objectId) {

    }

    @Override
    public void getWarningData(String objectId) {

    }

    @Override
    public void getCheckData(String subjectId) {
        variousDataInterfaceT.getCheckData(subjectId, new VariousDataInterfaceT.OnCheckDataCallback() {
            @Override
            public void onGetSuccess(SignInDataBean signInDataBean
                    , List<UserBeanForList> leaveRequestStudentList, List<UserBeanForList> absentStudentList) {
                view.loadCheckData(signInDataBean);
                view.loadListData(leaveRequestStudentList,SignInDataBean.LIST_TYPE_LEAVEREQUEST);
                view.loadListData(absentStudentList,SignInDataBean.LIST_TYPE_ABSENT);
            }

            @Override
            public void onStartGetData() {

            }

            @Override
            public void onGetSuccess() {

            }

            @Override
            public void onGetFailed(String errorMsg) {

            }
        });
    }
}
